package com.example.aloevibe.web;

import com.example.aloevibe.model.dto.UserDTO;
import com.example.aloevibe.model.dto.UserRegistrationDTO;
import com.example.aloevibe.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("registerData")
    public UserDTO registerDTO() {
        return new UserDTO();
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid UserRegistrationDTO userDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);
            redirectAttributes.addFlashAttribute("registerData", userDTO);
            return "redirect:/register";
        }

        try {
            userService.registerUser(userDTO);
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Възникна грешка при регистрацията. Моля, опитайте отново по-късно.");
            return "register";
        }
    }
}
