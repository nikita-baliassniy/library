package ru.geekbrains.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.library.exceptions.LibraryError;
import ru.geekbrains.library.model.Role;
import ru.geekbrains.library.repositories.RoleRepositrory;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepositrory roleRepositrory;

    public Role getRoleForNewUser() {
        return roleRepositrory.getNewUserRole();
    }

}
