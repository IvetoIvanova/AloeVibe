package com.example.aloevibe.web;

import com.example.aloevibe.model.dto.UserRegistrationDTO;
import com.example.aloevibe.service.RecaptchaService;
import com.example.aloevibe.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    @Value("${recaptcha.siteKey}")
    private String recaptchaSiteKey;

    private final UserService userService;
    private final RecaptchaService recaptchaService;

    public RegistrationController(UserService userService, RecaptchaService recaptchaService) {
        this.userService = userService;
        this.recaptchaService = recaptchaService;
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        if (!model.containsAttribute("registerData")) {
            model.addAttribute("registerData", new UserRegistrationDTO());
        }
        
        model.addAttribute("recaptchaSiteKey", recaptchaSiteKey);
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute("registerData") UserRegistrationDTO userDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @RequestParam("g-recaptcha-response") String recaptchaToken) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);
            redirectAttributes.addFlashAttribute("registerData", userDTO);
            return "redirect:/register";
        }

        boolean isRecaptchaValid = recaptchaService.verifyRecaptchaToken(recaptchaToken);

        if (!isRecaptchaValid) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid reCAPTCHA verification.");
            redirectAttributes.addFlashAttribute("registerData", userDTO);
            return "redirect:/register";
        }

        try {
            userService.registerUser(userDTO);
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Възникна грешка при регистрацията. Моля, опитайте отново по-късно.");
            return "redirect:/register";
        }
    }
}
