package ru.geekbrains.library.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.geekbrains.library.dto.BookListDto;
import ru.geekbrains.library.dto.UserDto;
import ru.geekbrains.library.model.Book;
import ru.geekbrains.library.model.User;

@Configuration
public class ModelConfig {

    private void configureBook(ModelMapper modelMapper) {
        modelMapper.typeMap(Book.class, BookListDto.class)
                .addMappings(mapper -> mapper.map(Book::getCommentsCount, BookListDto::setCommentsCount));
    }

    private void configureUser(ModelMapper modelMapper) {
        modelMapper.typeMap(User.class, UserDto.class)
                .addMappings(mapper -> mapper.map(User::getUsername, UserDto::setName));
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        configureBook(modelMapper);
        configureUser(modelMapper);
        return modelMapper;
    }
}
