package my.flick.rd.springproject.service;

import my.flick.rd.springproject.dto.UserDto;
import my.flick.rd.springproject.model.User;

import java.util.List;

public interface UserService {
    UserDto getUserById(long id);

    List<UserDto> getAllUsers();

    UserDto addUser(UserDto userDto);

    /**
     * updates non-null fields (except id and password)
     *
     * @param userDto
     * @return
     */
    UserDto updateUser(long id, UserDto userDto);

    void deleteUser(long id);

    /**
     * @param email
     * @param password
     * @return authenticated user if email and password is valid, otherwise
     * @throws InvalidCredentialsException
     */
    User getAuthenticatedUser(String email,String password);
}
