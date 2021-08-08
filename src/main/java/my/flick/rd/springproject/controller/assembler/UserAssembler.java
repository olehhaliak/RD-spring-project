package my.flick.rd.springproject.controller.assembler;

import my.flick.rd.springproject.controller.UserController;
import my.flick.rd.springproject.controller.model.UserModel;
import my.flick.rd.springproject.dto.UserDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler extends RepresentationModelAssemblerSupport<UserDto, UserModel> {
    public UserAssembler() {
        super(UserController.class, UserModel.class);
    }

    @Override
    public UserModel toModel(UserDto entity) {
        return new UserModel(entity);
    }
}
