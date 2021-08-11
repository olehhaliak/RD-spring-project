package my.flick.rd.springproject.util.mapper.impl;

import my.flick.rd.springproject.dto.UserDto;
import my.flick.rd.springproject.model.User;
import my.flick.rd.springproject.model.enums.Role;
import org.junit.jupiter.api.Test;

import static my.flick.rd.springproject.test.utils.UserTestData.*;
import static org.junit.jupiter.api.Assertions.*;

class UserMapperImplTest {
    UserMapperImpl userDtoMapper = new UserMapperImpl();

    @Test
    void mapToDtoTest() {
        UserDto actualDto = userDtoMapper.mapToDto(testUser());
        assertAll(
                () -> assertEquals(USER_ID, actualDto.getId()),
                () -> assertEquals(USER_EMAIL, actualDto.getEmail()),
                () -> assertEquals(USER_ROLE, actualDto.getRole()),
                () -> assertNull(actualDto.getPassword()),
                () -> assertFalse(actualDto.isBlocked())
        );
    }

    @Test
    void mapToModelTest() {
        User actualUser = userDtoMapper.mapToModel(testUserDto());
        assertAll(
                () -> assertEquals(USER_ID, actualUser.getId()),
                () -> assertEquals(USER_EMAIL, actualUser.getEmail()),
                () -> assertEquals(USER_ROLE, actualUser.getRole()),
                () -> assertEquals(USER_PASSWORD, actualUser.getPassword()),
                () -> assertFalse(actualUser.isBlocked())
        );
    }

    @Test
    public void overwriteNotNullProperties_AllPresentTest() {
        String newEmail = "user2@mail.com";
        Role newRole = Role.ADMIN;
        User dest = testUser();
        User src = testUser();
        src.setEmail(newEmail);
        src.setRole(newRole);
        userDtoMapper.overwriteNotNullProperties(src, dest);
        assertAll(
                () -> assertEquals(newEmail, dest.getEmail()),
                () -> assertEquals(newRole, dest.getRole())
        );
    }

    @Test
    public void overwriteNotNullProperties_AllNullTest() {
        User dest = testUser();
        User src = testUser();
        src.setEmail(null);
        src.setRole(null);
        userDtoMapper.overwriteNotNullProperties(src, dest);
        assertAll(
                () -> assertEquals(USER_EMAIL, dest.getEmail()),
                () -> assertEquals(USER_ROLE, dest.getRole())
        );
    }
}