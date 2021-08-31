package ru.geekbrains.library.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import ru.geekbrains.library.dictionary.RoleEnum;
import ru.geekbrains.library.dto.UserListDto;
import ru.geekbrains.library.dto.UserRegisterDto;
import ru.geekbrains.library.exceptions.UserNotFoundException;
import ru.geekbrains.library.model.Role;
import ru.geekbrains.library.model.User;
import ru.geekbrains.library.repositories.UserRepository;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<UserListDto> findUserListDtoByEmail(String email) {
        return userRepository.findByEmail(email).map(user -> modelMapper.map(user, UserListDto.class));
    }

    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public User createNewUser(UserRegisterDto newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        User user = modelMapper.map(newUser, User.class);
        user.setRoles(new ArrayList<>());
        Role role = roleService.getRoleByName(RoleEnum.ROLE_USER);
        user.getRoles().add(role);
        userRepository.save(user);
        return user;
    }

    public List<User> findAllByRoles(List<RoleEnum> rolesNames) {
        List<Role> roles = roleService.findAllByName(rolesNames);
        return userRepository.findByRolesIn(roles);
    }

    @Transactional
    public void updateRole(Long userId, Long roleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Юзер не найден"));
        Role role = roleService.findById(roleId);
        user.getRoles().clear();
        user.getRoles().add(role);
    }

}
