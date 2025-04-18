package com.example.aloevibe.init;

import com.example.aloevibe.model.entity.User;
import com.example.aloevibe.model.entity.UserRole;
import com.example.aloevibe.model.enums.UserRoleEnum;
import com.example.aloevibe.repository.RoleRepository;
import com.example.aloevibe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final DataSource dataSource;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("classpath:data.sql")
    private Resource dataSqlScript;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.email}")
    private String adminEmail;

    public DataInitializer(DataSource dataSource, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        initializeRoles();
        initializeAdmin();
        initializeDatabase();
    }

    public void initializeDatabase() {
        String checkQuery = "SELECT COUNT(*) FROM categories";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(checkQuery);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException("Неуспешна проверка на базата данни", e);
        }


        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(dataSqlScript);
        populator.execute(dataSource);
    }

    private void initializeRoles() {
        if (roleRepository.findAll().isEmpty()) {
            UserRole adminRole = new UserRole(UserRoleEnum.ADMIN);
            UserRole userRole = new UserRole(UserRoleEnum.USER);
            roleRepository.save(adminRole);
            roleRepository.save(userRole);
        }
    }

    private void initializeAdmin() {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
//            admin.setPhone("123456789");

            UserRole adminRole = roleRepository.findByRole(UserRoleEnum.ADMIN);
            UserRole userRole = roleRepository.findByRole(UserRoleEnum.USER);

//            if (adminRole == null) {
//                throw new IllegalStateException("Admin role is not initialized.");
//            }

            admin.setRoles(List.of(adminRole, userRole));
            userRepository.save(admin);
        }
    }
}
