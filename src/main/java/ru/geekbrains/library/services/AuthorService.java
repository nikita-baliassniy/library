package ru.geekbrains.library.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.geekbrains.library.dto.AuthorDto;
import ru.geekbrains.library.dto.AuthorListDto;
import ru.geekbrains.library.model.Author;
import ru.geekbrains.library.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public List<AuthorListDto> findAll() {
        return authorRepository
                .findAll()
                .stream()
                .map(author -> modelMapper.map(author, AuthorListDto.class))
                .collect(Collectors.toList());
    }

    public Optional<AuthorDto> findAuthorById(Long id) {
        return authorRepository.findById(id).map(author -> modelMapper.map(author, AuthorDto.class));
    }

    public Optional<AuthorDto> insertOrUpdateAuthor(AuthorDto authorDto) {
        try {

            Author author = modelMapper.map(authorDto, Author.class);
            if (author.getId() == null) {
                if (author.getBooks() != null) {
                    author.getBooks().forEach(book -> {
                        book.getAuthors().add(author);
                    });
                }
            }

            return Optional.of(modelMapper.map(authorRepository.save(author), AuthorDto.class));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    public Integer deleteById(Long id) {

        return authorRepository.deleteAuthorById(id);
    }
}
