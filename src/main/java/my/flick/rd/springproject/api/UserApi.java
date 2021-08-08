package my.flick.rd.springproject.api;

import my.flick.rd.springproject.controller.model.UserModel;
import my.flick.rd.springproject.dto.UserDto;
import my.flick.rd.springproject.dto.validationgroups.OnCreate;
import my.flick.rd.springproject.dto.validationgroups.OnUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Todo: add constant or property of api path
@RequestMapping("/api/v1/users")
public interface UserApi {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserModel getUserById(@PathVariable("id") long id);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<UserModel> getAllUsers();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserModel createUser(@RequestBody @Validated(OnCreate.class) UserDto userDto);

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserModel updateUser(@PathVariable long id,@RequestBody @Validated(OnUpdate.class) UserDto userDto);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> deleteUser(@PathVariable long id);
}
