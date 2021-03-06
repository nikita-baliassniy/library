package ru.geekbrains.library.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.geekbrains.library.dto.BookListDto;
import ru.geekbrains.library.dto.UserDto;
import ru.geekbrains.library.dto.UserListDto;
import ru.geekbrains.library.dto.UserRegisterDto;
import ru.geekbrains.library.model.Book;
import ru.geekbrains.library.model.User;

import java.util.function.Function;

@Configuration
public class ModelConfig {

    private void configureBook(ModelMapper modelMapper) {
        modelMapper.typeMap(Book.class, BookListDto.class)
                .addMappings(mapper -> mapper.map(Book::getCommentsCount, BookListDto::setCommentsCount));
    }

    private void configureUser(ModelMapper modelMapper) {
        modelMapper.typeMap(User.class, UserDto.class)
                .addMappings(mapper -> mapper.map(User::getUsername, UserDto::setName));
        modelMapper.typeMap(User.class, UserListDto.class)
                .addMappings(mapper -> mapper.map(User::getNewsletter, UserListDto::setSubscribeNews));
        modelMapper.typeMap(UserRegisterDto.class, User.class)
                .addMappings(mapper -> mapper.map(UserRegisterDto::getName, User::setUsername));
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        configureBook(modelMapper);
        configureUser(modelMapper);
        return modelMapper;
    }
}
