package ru.geekbrains.library.configs;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.geekbrains.library.dto.BookListDto;
import ru.geekbrains.library.model.Book;

@Configuration
public class ModelConfig {

    private void configureBook(ModelMapper modelMapper) {
        modelMapper.typeMap(Book.class, BookListDto.class)
                .addMappings(mapper -> mapper.map(Book::getCommentsCount, BookListDto::setCommentsCount) );
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        configureBook(modelMapper);
        return modelMapper;
    }
}
