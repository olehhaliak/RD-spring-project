package my.flick.rd.springproject.service.impl;

import my.flick.rd.springproject.dto.UserDto;
import my.flick.rd.springproject.exception.UserNotFoundException;
import my.flick.rd.springproject.model.enums.Role;
import my.flick.rd.springproject.repository.UserRepository;
import my.flick.rd.springproject.util.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static my.flick.rd.springproject.test.utils.UserTestData.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final String UPDATED_EMAIL = "newEmail@mail.com";
    private static final Role UPDATED_ROLE = Role.ADMIN;
    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void getUserByIdTest() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(testUser()));
        when(userMapper.mapToDto(testUser())).thenReturn(getUserDto());
        assertThat(userService.getUserById(USER_ID), is(equalTo(getUserDto())));
    }

    @Test
    void getUserById_NotExistsTest() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(USER_ID));
    }

    @Test
    void getAllUsersTest() {
        when(userRepository.findAll()).thenReturn(List.of(testUser()));
        when(userMapper.mapToDto(testUser())).thenReturn(getUserDto());
        assertThat(userService.getAllUsers(), contains(getUserDto()));
    }

    @Test
    void addUserTest() {
        when(userMapper.mapToModel(getUserDto())).thenReturn(testUser());
        when(userMapper.mapToDto(testUser())).thenReturn(getUserDto());
        when(userRepository.save(testUser())).thenReturn(testUser());
        assertThat(userService.addUser(getUserDto()),is(equalTo(getUserDto())));
    }

    @Test
    void updateUserTest() {
        UserDto updatedUserDto = getUserDto();
        updatedUserDto.setEmail(UPDATED_EMAIL);
        updatedUserDto.setRole(UPDATED_ROLE);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(testUser()));
        when(userRepository.save(any())).thenReturn(testUser());
        when((userMapper.mapToDto(testUser()))).thenReturn(updatedUserDto);
        assertThat(userService.updateUser(USER_ID,updatedUserDto),is(equalTo(updatedUserDto)));
        verify(userMapper).overwriteNotNullProperties(any(),any());
    }

    @Test
    void updateUser_NotExistsTest() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,()->userService.updateUser(USER_ID,getUserDto()));
    }

    @Test
    void deleteUserTest() {
        when(userRepository.existsById(USER_ID)).thenReturn(true);
        userService.deleteUser(USER_ID);
        verify(userRepository).deleteById(USER_ID);
    }
    @Test
    void deleteUser_NotExistsTest() {
        when(userRepository.existsById(USER_ID)).thenReturn(false);
        assertThrows(UserNotFoundException.class,()->userService.deleteUser(USER_ID));
    }
}