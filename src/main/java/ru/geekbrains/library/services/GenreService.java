package ru.geekbrains.library.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.geekbrains.library.dto.AuthorDto;
import ru.geekbrains.library.dto.GenreDto;
import ru.geekbrains.library.dto.GenreListDto;
import ru.geekbrains.library.model.Author;
import ru.geekbrains.library.model.Genre;
import ru.geekbrains.library.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreService {

    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    public List<GenreListDto> findAll() {
        return genreRepository
                .findAll()
                .stream()
                .map(genre -> modelMapper.map(genre, GenreListDto.class))
                .collect(Collectors.toList());
    }

    public Optional<GenreDto> getGenreById(Long id) {
        return genreRepository.findById(id).map(genre -> modelMapper.map(genre, GenreDto.class));
    }

    public Optional<GenreDto> insertOrUpdateGenre(GenreDto genreDto) {
        try {

            Genre genre = modelMapper.map(genreDto, Genre.class);
            if (genre.getId() == null) {
                if (genre.getBooks() != null) {
                    genre.getBooks().forEach(book -> {
                        book.getGenres().add(genre);
                    });
                }
            }

            return Optional.of(modelMapper.map(genreRepository.save(genre), GenreDto.class));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    public Integer deleteById(Long id) {

        return genreRepository.deleteGenreById(id);
    }
}
