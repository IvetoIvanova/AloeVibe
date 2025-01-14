package com.example.aloevibe.repository;

import com.example.aloevibe.model.entity.UserRole;
import com.example.aloevibe.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, UUID> {
    UserRole findByRole(UserRoleEnum userRoleEnum);

}
