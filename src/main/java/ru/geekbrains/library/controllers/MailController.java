package ru.geekbrains.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.library.model.Message;
import ru.geekbrains.library.model.User;
import ru.geekbrains.library.services.MailService;
import ru.geekbrains.library.services.UserService;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mail")
public class MailController {
    private final MailService mailService;
    private final UserService userService;

    @GetMapping("/sub")
    private void newsletterSub(Principal principal) {
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь с именем %s не найден", principal.getName())));
        mailService.newsletterSub(user);
    }

    @GetMapping("/unsub")
    private void newsletterUnsub(Principal principal) {
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь с именем %s не найден", principal.getName())));
        mailService.newsletterUnsub(user);
    }

    @PostMapping("/feedback")
    private void sendFeedback(@RequestBody Message message) {
        List<User> getters = userService.findAllByRoles(Arrays.asList("ROLE_MANAGER", "ROLE_ADMIN"));
        mailService.sendMessageFromFeedbackForm(getters, message);
    }
}
