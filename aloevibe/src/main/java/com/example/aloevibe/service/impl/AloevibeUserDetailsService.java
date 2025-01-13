package com.example.aloevibe.service.impl;

import com.example.aloevibe.model.entity.User;
import com.example.aloevibe.model.entity.UserRole;
import com.example.aloevibe.model.enums.UserRoleEnum;
import com.example.aloevibe.repository.UserRepository;
import com.example.aloevibe.user.AloevibeUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AloevibeUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public AloevibeUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .map(AloevibeUserDetailsService::map)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Потребител с имейл " + email + " не е намерен!"));
    }

    private static UserDetails map(User user) {
        return new AloevibeUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(UserRole::getRole).map(
                        AloevibeUserDetailsService::map).toList(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    private static GrantedAuthority map(UserRoleEnum role) {
        return new SimpleGrantedAuthority(
                "ROLE_" + role
        );
    }
}
