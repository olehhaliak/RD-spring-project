package my.flick.rd.springproject.service.impl;

import my.flick.rd.springproject.dto.UserDto;
import my.flick.rd.springproject.exception.*;
import my.flick.rd.springproject.model.User;
import my.flick.rd.springproject.model.enums.Role;
import my.flick.rd.springproject.repository.UserRepository;
import my.flick.rd.springproject.test.utils.UserTestData;
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
        when(userMapper.mapToDto(testUser())).thenReturn(testUserDto());
        assertThat(userService.getUserById(USER_ID), is(equalTo(testUserDto())));
    }

    @Test
    void getUserById_NotExistsTest() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(USER_ID));
    }

    @Test
    void getAllUsersTest() {
        when(userRepository.findAll()).thenReturn(List.of(testUser()));
        when(userMapper.mapToDto(testUser())).thenReturn(testUserDto());
        assertThat(userService.getAllUsers(), contains(testUserDto()));
    }

    @Test
    void addUserTest() {
        when(userMapper.mapToModel(testUserDto())).thenReturn(testUser());
        when(userMapper.mapToDto(testUser())).thenReturn(testUserDto());
        when(userRepository.save(testUser())).thenReturn(testUser());
        assertThat(userService.addUser(testUserDto()), is(equalTo(testUserDto())));
    }

    @Test
    void addUser_DifferentPasswordsTest() {
        UserDto userDto = testUserDto();
        userDto.setPasswordRepeat("different password");
        assertThrows(InputValidationException.class, () -> userService.addUser(userDto));
    }

    @Test
    void updateUserTest() {
        UserDto updatedUserDto = testUserDto();
        updatedUserDto.setEmail(UPDATED_EMAIL);
        updatedUserDto.setRole(UPDATED_ROLE);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(testUser()));
        when(userRepository.save(any())).thenReturn(testUser());
        when((userMapper.mapToDto(testUser()))).thenReturn(updatedUserDto);
        assertThat(userService.updateUser(USER_ID, updatedUserDto), is(equalTo(updatedUserDto)));
        verify(userMapper).overwriteNotNullProperties(any(), any());
    }

    @Test
    void updateUser_NotExistsTest() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(USER_ID, testUserDto()));
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
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(USER_ID));
    }

    @Test
    void blockUserTest() {
        User testUser = testUser();
        testUser.setRole(Role.CUSTOMER);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(testUser));
        userService.blockUser(USER_ID);
        testUser.setBlocked(true);
        verify(userRepository).save(testUser);
    }

    @Test
    void blockUser_NotExistsTest() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.blockUser(USER_ID));
    }

    @Test
    void blockUser_AdminUserTest() {
        User testUser = testUser();
        testUser.setRole(Role.ADMIN);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(testUser));
        assertThrows(UserIsNotCustomerException.class, () -> userService.blockUser(USER_ID));
    }


    @Test
    void unblockUserTest() {
        User testUser = testUser();
        testUser.setRole(Role.CUSTOMER);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(testUser));
        userService.unblockUser(USER_ID);
        testUser.setBlocked(false);
        verify(userRepository).save(testUser);
    }

    @Test
    void unblockUser_NotExistsTest() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.unblockUser(USER_ID));
    }

    @Test
    void unblockUser_AdminUserTest() {
        User testUser = testUser();
        testUser.setRole(Role.ADMIN);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(testUser));
        assertThrows(UserIsNotCustomerException.class, () -> userService.unblockUser(USER_ID));
    }

    @Test
    void getAuthenticatedUserTest() {
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(testUser()));
        assertThat(userService.getAuthenticatedUser(USER_EMAIL,USER_PASSWORD),is(equalTo(testUser())));
    }


    @Test
    void getAuthenticatedUser_NotExistsTest() {
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getAuthenticatedUser(USER_EMAIL,USER_PASSWORD));
    }

    @Test
    void getAuthenticatedUser_WrongPasswordTest() {
        User testUser = testUser();
        testUser.setPassword("Wrong password");
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(testUser));
        assertThrows(InvalidCredentialsException.class, () -> userService.getAuthenticatedUser(USER_EMAIL,USER_PASSWORD));
    }
    @Test
    void getAuthenticatedUser_UserIsBlockedTest() {
        User testUser = testUser();
        testUser.setBlocked(true);
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(testUser));
        assertThrows(UserIsBlockedException.class, () -> userService.getAuthenticatedUser(USER_EMAIL,USER_PASSWORD));
    }
}