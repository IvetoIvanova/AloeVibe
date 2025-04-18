package com.example.aloevibe.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

public interface PassResetService {

    void createPasswordResetToken(String email);

    boolean isValidToken(String token);

    void updatePassword(String token, String newPassword, PasswordEncoder passwordEncoder);

}
