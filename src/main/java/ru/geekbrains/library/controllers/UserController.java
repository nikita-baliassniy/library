package ru.geekbrains.library.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.library.dictionary.RoleEnum;
import ru.geekbrains.library.dto.UserListDto;
import ru.geekbrains.library.exceptions.UserNotFoundException;
import ru.geekbrains.library.model.User;
import ru.geekbrains.library.services.UserService;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final ModelMapper mapper;
    private final UserService userService;

    @GetMapping("/{email}")
    public UserListDto getUserByEmail(@PathVariable String email) {
        return userService.findUserListDtoByEmail(email).orElseThrow(() -> new UserNotFoundException("Порльзователь с Email: " + email + " не найден."));
    }

    @GetMapping("/isAdmin")
    public Boolean isAdmin(Principal principal){
        return userService.checkAdmin(principal.getName());
    }

    @GetMapping("/self")
    public UserListDto getSelfInfo(Principal principal) {
        return userService.findUserListDtoByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException("Порльзователь с Email: " + principal.getName() + " не найден."));
    }

    @GetMapping("/workers")
    public List<UserListDto> findAllWorkers() {
        return userService.findAllByRoles(
                Arrays.asList(RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_MANAGER))
                .stream()
                .map(s -> mapper.map(s, UserListDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/users")
    public List<UserListDto> findAllUsers(){
        return userService.findAllByRoles(Collections.singletonList(RoleEnum.ROLE_USER))
                .stream()
                .map(s -> mapper.map(s, UserListDto.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/changeRole")
    public void changeRole(@RequestParam("userId")Long userId,
                           @RequestParam("roleId") Long roleId){
        userService.updateRole(userId,roleId);
    }
}
