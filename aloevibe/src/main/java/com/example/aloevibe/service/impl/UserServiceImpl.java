package com.example.aloevibe.service.impl;

import com.example.aloevibe.model.dto.UserRegistrationDTO;
import com.example.aloevibe.model.entity.User;
import com.example.aloevibe.model.entity.UserRole;
import com.example.aloevibe.model.enums.UserRoleEnum;
import com.example.aloevibe.model.mapper.UserMapper;
import com.example.aloevibe.repository.RoleRepository;
import com.example.aloevibe.repository.UserRepository;
import com.example.aloevibe.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(UserRegistrationDTO userRegistrationDTO) {
        if (userRepository.findByEmail(userRegistrationDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Имейлът вече съществува.");
        }

        User user = userMapper.toEntity(userRegistrationDTO);
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));

        UserRole userRole = roleRepository.findByRole(UserRoleEnum.USER);
        if (userRole == null) {
            throw new IllegalStateException("Ролята USER не е инициализирана.");
        }

        user.setRoles(List.of(userRole));

        userRepository.save(user);
    }

}
