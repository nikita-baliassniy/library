package ru.geekbrains.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.library.dto.AuthorDto;
import ru.geekbrains.library.dto.AuthorListDto;
import ru.geekbrains.library.exceptions.AuthorBadDataException;
import ru.geekbrains.library.exceptions.AuthorNotFoundException;
import ru.geekbrains.library.model.filter.ModelSorter;
import ru.geekbrains.library.repositories.specifications.AuthorSpecification;
import ru.geekbrains.library.services.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public List<AuthorListDto> getAllAuthors(@RequestParam MultiValueMap<String, String> params) {
        return authorService.findAll(AuthorSpecification.build(params), new ModelSorter(params));
    }

    @GetMapping("/{id}")
    public AuthorDto getAuthorById(@PathVariable Long id) {
        return authorService.findAuthorDtoById(id).orElseThrow(() -> new AuthorNotFoundException("Автор с ID: " + id + " не найден"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto updateAuthor(@RequestBody AuthorDto authorDto) {
        return authorService.insertOrUpdateAuthor(authorDto).orElseThrow(() -> new AuthorBadDataException("Ошибка в заполнении данных"));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto insertAuthor(@RequestBody AuthorDto authorDto) {
        return authorService.insertOrUpdateAuthor(authorDto).orElseThrow(() -> new AuthorBadDataException("Ошибка в заполнении данных"));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deleteAuthorById(@PathVariable Long id) {
        if (authorService.deleteById(id) <= 0) {
            throw new AuthorNotFoundException();
        }
    }

}
