package ru.geekbrains.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.library.beans.JwtTokenUtil;
import ru.geekbrains.library.dto.JwtRequest;
import ru.geekbrains.library.dto.JwtResponse;
import ru.geekbrains.library.dto.UserRegisterDto;
import ru.geekbrains.library.exceptions.LibraryError;
import ru.geekbrains.library.services.UserService;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new LibraryError(HttpStatus.UNAUTHORIZED.value(), "Не верные логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PutMapping
    public ResponseEntity<?> registerNeUser(@RequestBody UserRegisterDto newUser) {
        try {
            userService.createNewUser(newUser);
            return ResponseEntity.ok(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new LibraryError(HttpStatus.BAD_REQUEST.value(), "Username or Email already exists"), HttpStatus.BAD_REQUEST);
        }

    }
}
