package ru.geekbrains.library.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.geekbrains.library.dictionary.RoleEnum;
import ru.geekbrains.library.dto.RoleDto;
import ru.geekbrains.library.exceptions.RoleNotFoundException;
import ru.geekbrains.library.model.Role;
import ru.geekbrains.library.repositories.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final ModelMapper mapper;
    private final RoleRepository roleRepository;

//    public Role getRoleForNewUser() {
//        return roleRepositrory.getNewUserRole();
//    }

    public Role getRoleByName(RoleEnum role) {
        return roleRepository.findByName(role);
    }

    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(()->new RoleNotFoundException("Роль не найдена"));
    }

    public List<Role> findAllByName(List<RoleEnum> names) {
        return roleRepository.findAllByNameIn(names);
    }

    public List<RoleDto> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(s -> mapper.map(s, RoleDto.class))
                .collect(Collectors.toList());
    }

}
