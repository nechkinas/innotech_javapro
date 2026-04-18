package ru.innotech.spring_db.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.innotech.spring_db.dto.UserDto;
import ru.innotech.spring_db.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        return userService.getDtoById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(
            @RequestBody
            UserDto userDto
    ) {
        return userService.createDto(userDto.name());
    }

    @DeleteMapping("/{id}/delete")
    public void deleteById(@PathVariable Long id) {
        userService.remove(id);
    }

}
