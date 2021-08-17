package ru.geekbrains.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.library.model.User;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private List<RoleDto> roles;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getUserInfo().getName();
    }
}


