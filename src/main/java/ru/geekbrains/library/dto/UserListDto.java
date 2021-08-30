package ru.geekbrains.library.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.library.model.Newsletter;

@Data
@NoArgsConstructor
public class UserListDto {

    private Long id;
    private String email;
    @JsonProperty("name")
    private String userInfoName;
    private boolean subscribeNews;

    public void setSubscribeNews(Newsletter newsletter) {
        this.subscribeNews = newsletter != null;
    }
}
