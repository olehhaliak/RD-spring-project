package my.flick.rd.springproject.util.dtomapper.impl;

import my.flick.rd.springproject.dto.UserDto;
import my.flick.rd.springproject.model.Category;
import my.flick.rd.springproject.model.User;
import org.junit.jupiter.api.Test;

import static my.flick.rd.springproject.test.utils.UserTestData.USER;
import static my.flick.rd.springproject.test.utils.UserTestData.USER_DTO;
import static org.junit.jupiter.api.Assertions.*;
class UserDtoMapperImplTest {
    UserDtoMapperImpl userDtoMapper = new UserDtoMapperImpl();
    @Test
    void mapToDtoTest() {
        UserDto actualDto = userDtoMapper.mapToDto(USER);
        assertAll(
                ()->assertEquals(USER_DTO.getId(),actualDto.getId()),
                ()->assertEquals(USER_DTO.getEmail(),actualDto.getEmail()),
                ()->assertEquals(USER_DTO.getRole(),actualDto.getRole()),
                ()->assertNull(actualDto.getPassword())
        );
    }

    @Test
    void mapToModelTest() {
        User actualUser = userDtoMapper.mapToModel(USER_DTO);
       assertAll(
               ()->assertEquals(USER.getId(),actualUser.getId()),
               ()->assertEquals(USER.getEmail(),actualUser.getEmail()),
               ()->assertEquals(USER.getRole(),actualUser.getRole()),
               ()->assertEquals(USER.getPassword(),actualUser.getPassword())
       );
    }
}