package my.flick.rd.springproject.controller.assembler;

import my.flick.rd.springproject.controller.UserController;
import my.flick.rd.springproject.controller.model.UserModel;
import my.flick.rd.springproject.dto.UserDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler extends RepresentationModelAssemblerSupport<UserDto, UserModel> {
    public UserAssembler() {
        super(UserController.class, UserModel.class);
    }

    @Override
    public UserModel toModel(UserDto entity) {
        UserModel model = new UserModel(entity);
        model.add(linkTo(methodOn(UserController.class).getUserById(entity.getId())).withSelfRel());
        model.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("get all users"));
        model.add(linkTo(methodOn(UserController.class).createUser(null)).withRel("add new user"));
        model.add(linkTo(methodOn(UserController.class).updateUser(entity.getId(),null)).withRel("update user"));
        model.add(linkTo(methodOn(UserController.class).deleteUser(entity.getId())).withRel("delete user"));
        return model;
    }
}
