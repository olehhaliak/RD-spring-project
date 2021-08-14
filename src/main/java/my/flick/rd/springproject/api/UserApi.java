package my.flick.rd.springproject.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import my.flick.rd.springproject.annotation.RequireAdminPrivileges;
import my.flick.rd.springproject.controller.model.UserModel;
import my.flick.rd.springproject.dto.UserDto;
import my.flick.rd.springproject.dto.validationgroups.OnCreate;
import my.flick.rd.springproject.dto.validationgroups.OnUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "User management api")
@RequestMapping("/api/v1/users")
public interface UserApi {

    @ApiOperation("get user by id")
    @ApiImplicitParam(name = "id", type = "path", required = true, paramType = "long")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserModel getUserById(@PathVariable("id") long id);

    @ApiOperation("get all users")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<UserModel> getAllUsers();


    @ApiOperation("add new user")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserModel createUser(@RequestBody @Validated(OnCreate.class) UserDto userDto);

    @RequireAdminPrivileges
    @ApiOperation("update user")
    @ApiImplicitParam(name = "id", type = "path", required = true, paramType = "long")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserModel updateUser(@PathVariable long id,@RequestBody @Validated(OnUpdate.class) UserDto userDto);

    @RequireAdminPrivileges
    @ApiOperation("delete user")
    @ApiImplicitParam(name = "id", type = "path", required = true, paramType = "long")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> deleteUser(@PathVariable long id);


    @ApiOperation("block user")
    @PatchMapping("/block/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Void> blockUser(@PathVariable long id);

    @ApiOperation("block user")
    @PatchMapping("/unblock/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Void> unblockUser(@PathVariable long id);
}
