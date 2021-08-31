package ru.geekbrains.library.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.library.model.Newsletter;

import java.util.List;

@Data
@NoArgsConstructor
public class UserListDto {

    private Long id;
    private String email;
    @JsonProperty("name")
    private String userInfoName;
    private List<RoleDto> roles;
    private boolean subscribeNews;

    public void setSubscribeNews(Newsletter newsletter) {
        this.subscribeNews = newsletter != null;
    }
}
