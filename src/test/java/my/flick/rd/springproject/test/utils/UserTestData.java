package my.flick.rd.springproject.test.utils;

import my.flick.rd.springproject.dto.UserDto;
import my.flick.rd.springproject.model.User;
import my.flick.rd.springproject.model.enums.Role;

public class UserTestData {
    public static final long USER_ID = 3L;
    public static final String USER_EMAIL = "user@mail.com";
    public static final String USER_PASSWORD = "password";
    public static final Role USER_ROLE = Role.CUSTOMER;
    public static final User USER = User.builder()
            .id(USER_ID)
            .email(USER_EMAIL)
            .password(USER_PASSWORD)
            .role(USER_ROLE)
            .build();
    public static final UserDto USER_DTO = UserDto.builder()
            .id(USER_ID)
            .email(USER_EMAIL)
            .password(USER_PASSWORD)
            .role(USER_ROLE)
            .build();
}
