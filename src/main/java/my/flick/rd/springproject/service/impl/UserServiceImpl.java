package my.flick.rd.springproject.service.impl;

import lombok.RequiredArgsConstructor;
import my.flick.rd.springproject.dto.UserDto;
import my.flick.rd.springproject.exception.InputValidationException;
import my.flick.rd.springproject.exception.InvalidCredentialsException;
import my.flick.rd.springproject.exception.UserNotFoundException;
import my.flick.rd.springproject.model.User;
import my.flick.rd.springproject.repository.UserRepository;
import my.flick.rd.springproject.service.UserService;
import my.flick.rd.springproject.util.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserDto getUserById(long id) {
        return userRepository.findById(id)
                .map(userMapper::mapToDto)
                .orElseThrow(() -> new UserNotFoundException("User with id specified does not exists"));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        if (!userDto.getPassword().equals(userDto.getPasswordRepeat())) {
            throw new InputValidationException("password and repeatPassword fields must be equal");//Todo:add test
        }
        User user = userMapper.mapToModel(userDto);
        user = userRepository.save(user);
        return userMapper.mapToDto(user);
    }

    @Override
    public UserDto updateUser(long id, UserDto userDto) {
        User oldUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id specified does not exists"));//TODO: move err message to constant or properties
        User user = userMapper.mapToModel(userDto);
        userMapper.overwriteNotNullProperties(user, oldUser);
        return userMapper.mapToDto(userRepository.save(oldUser));
    }

    @Override
    public void deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with id specified does not exists");
        }
        userRepository.deleteById(id);
    }

    @Override
    public User getAuthenticatedUser(String email, String password) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("User with email '" + email + "' not exists"));
        if (!user.getPassword().equals(password)) {
            throw new InvalidCredentialsException("Wrong password");
        }
        return user;
    }
}
