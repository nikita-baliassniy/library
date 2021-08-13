package ru.geekbrains.library.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserListDto {

    private Long id;
    private String email;
    @JsonProperty("name")
    private String userInfoName;

}
