package com.example.aloevibe.service;

import com.example.aloevibe.model.dto.UserDTO;
import com.example.aloevibe.model.dto.UserRegistrationDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    void registerUser(UserRegistrationDTO userRegistrationDTO);
}
