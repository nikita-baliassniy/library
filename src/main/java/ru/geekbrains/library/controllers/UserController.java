package ru.geekbrains.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.library.dto.UserListDto;
import ru.geekbrains.library.exceptions.UserNotFoundException;
import ru.geekbrains.library.services.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{email}")
    public UserListDto getUserByEmail(@PathVariable String email) {
        return userService.findUserListDtoByEmail(email).orElseThrow(() -> new UserNotFoundException("Порльзователь с Email: " + email + " не найден."));
    }

    @GetMapping("/self")
    public UserListDto getSelfInfo(Principal principal) {
        return userService.findUserListDtoByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException("Порльзователь с Email: " + principal.getName() + " не найден."));
    }
}
