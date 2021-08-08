package my.flick.rd.springproject.controller.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import my.flick.rd.springproject.dto.UserDto;
import org.springframework.hateoas.RepresentationModel;

public class UserModel extends RepresentationModel<UserModel> {
    @JsonUnwrapped
    UserDto userDto;

    public UserModel(UserDto userDto) {
        this.userDto = userDto;
    }
}
