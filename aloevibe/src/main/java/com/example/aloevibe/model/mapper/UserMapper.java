package com.example.aloevibe.model.mapper;

import com.example.aloevibe.model.dto.UserRegistrationDTO;
import com.example.aloevibe.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRegistrationDTO toDTO(User user);

    User toEntity(UserRegistrationDTO userRegistrationDTO);
}
