package com.example.demo.PR4;

import com.example.demo.Model.User;
import com.example.demo.Services.AuthorizationService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pr4/users")
@Api(tags = "User")
public class UserRestController {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private UserRestService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Добавление нового пользователя")
    @ApiResponse(code = 200, message = "everything is ok")
    public void addNewUser(@ApiParam(type = "User",
                        value = "user",
                        example = "{\n" +
                            "    \"username\": \"example11\",\n" +
                            "    \"email\": \"example11@yandex.ru\",\n" +
                            "    \"password\": \"qwerty\",\n" +
                            "    \"phoneNumber\": \"89164415588\"\n" +
                            "}",
                        required = true)
                               @RequestBody User user) {
        userService.addNewUser(user);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Получение информации о всех пользователях")
    @ApiResponse(code = 200, message = "everything is ok")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Получение информации о пользователе по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "everything is ok"),
            @ApiResponse(code = 404, message = "User with this id doesn't exist")
    })
    public User getUser(@ApiParam(type = "Long",
                    value = "id",
                    example = "5",
                    required = true)
                            @PathVariable Long id) {
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Обновление информации о пользователе по id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User with this id updated"),
            @ApiResponse(code = 404, message = "User with this id doesn't exist")
    })
    public User updateUser(@ApiParam(type = "Long",
                    value = "id",
                    example = "5",
                    required = true)
                               @PathVariable Long id,
                           @ApiParam(type = "User",
                                   value = "user",
                                   example = "{\n" +
                                           "    \"username\": \"example11\",\n" +
                                           "    \"email\": \"example11@yandex.ru\",\n" +
                                           "    \"password\": \"qwerty\",\n" +
                                           "    \"phoneNumber\": \"89164415588\"\n" +
                                           "}",
                                   required = true)
                           @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Удаление пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "User with this id deleted"),
            @ApiResponse(code = 404, message = "User with this id doesn't exist")
    })
    public void deleteUser(@ApiParam(type = "Long",
                    value = "id",
                    example = "5",
                    required = true)
                               @PathVariable Long id) {
        userService.deleteUser(id);
    }
}
