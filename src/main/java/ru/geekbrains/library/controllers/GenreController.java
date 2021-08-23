package ru.geekbrains.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.library.dto.GenreDto;
import ru.geekbrains.library.dto.GenreListDto;
import ru.geekbrains.library.exceptions.GenreBadDataException;
import ru.geekbrains.library.exceptions.GenreNotFoundException;
import ru.geekbrains.library.services.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public List<GenreListDto> getAllGenres() {
        return genreService.findAll();
    }

    @GetMapping("/{id}")
    public GenreDto getGenreById(@PathVariable Long id) {
        return genreService.getGenreDtoById(id).orElseThrow(() -> new GenreNotFoundException("Жанр с ID: " + id + " не найден"));
    }

    @PostMapping
    public GenreDto updateGenre(@RequestBody GenreDto genreDto) {
        return genreService.insertOrUpdateGenre(genreDto).orElseThrow(GenreBadDataException::new);
    }

    @PutMapping
    public GenreDto insertGenre(@RequestBody GenreDto genreDto) {
        return genreService.insertOrUpdateGenre(genreDto).orElseThrow(GenreBadDataException::new);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deleteAuthorById(@PathVariable Long id) {
        if (genreService.deleteById(id) <= 0) {
            throw new GenreNotFoundException();
        }
    }
}
