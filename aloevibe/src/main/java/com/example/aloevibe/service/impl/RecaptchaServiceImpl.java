package com.example.aloevibe.service.impl;

import com.example.aloevibe.service.RecaptchaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
public class RecaptchaServiceImpl implements RecaptchaService {

    @Value("${recaptcha.secretKey}")
    private String secretKey;
    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    private final RestTemplate restTemplate;

    public RecaptchaServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean verifyRecaptchaToken(String captchaResponse) {
        if (captchaResponse == null || captchaResponse.isEmpty()) {
            return false;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.put("secret", Collections.singletonList(secretKey));
        body.put("response", Collections.singletonList(captchaResponse));

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(RECAPTCHA_VERIFY_URL, entity, Map.class);
            return response.getBody() != null && (Boolean) response.getBody().get("success");
        } catch (Exception e) {
            return false;
        }
    }
}
