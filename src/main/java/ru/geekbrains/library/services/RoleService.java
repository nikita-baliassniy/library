package ru.geekbrains.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.library.model.Role;
import ru.geekbrains.library.repositories.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

//    public Role getRoleForNewUser() {
//        return roleRepositrory.getNewUserRole();
//    }

    public Role getRoleByName(String role) {
        return roleRepository.findByName(role);
    }

    public List<Role> findAllByName(List<String> names) {
        return roleRepository.findAllByNameIn(names);
    }

}
