package my.flick.rd.springproject.controller;

import lombok.RequiredArgsConstructor;
import my.flick.rd.springproject.api.UserApi;
import my.flick.rd.springproject.controller.assembler.UserAssembler;
import my.flick.rd.springproject.controller.model.UserModel;
import my.flick.rd.springproject.dto.UserDto;
import my.flick.rd.springproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserAssembler userAssembler;
    private final UserService userService;

    @Override
    public UserModel getUserById(long id) {
        return userAssembler.toModel(userService.getUserById(id));
    }

    @Override
    public List<UserModel> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(userAssembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public UserModel createUser(UserDto userDto) {
        return userAssembler.toModel(userService.addUser(userDto));
    }

    @Override
    public UserModel updateUser(long id, UserDto userDto) {
        return userAssembler.toModel(userService.updateUser(id, userDto));
    }

    @Override
    public ResponseEntity<Void> deleteUser(long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> blockUser(long id) {
        userService.blockUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> unblockUser(long id) {
        userService.unblockUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
