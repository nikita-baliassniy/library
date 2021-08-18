package ru.geekbrains.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.library.services.StorageService;

@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
public class StorageController {
    private final StorageService storageService;

    @GetMapping("/cover/{id}")
    public byte[] sendCover(@PathVariable Long id) {
        return storageService.getCover(id);
    }
}
