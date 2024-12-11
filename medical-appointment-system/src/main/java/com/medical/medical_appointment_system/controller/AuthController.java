package com.medical.medical_appointment_system.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final ClientRegistration clientRegistration;

    @Autowired
    public AuthController(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistration = clientRegistrationRepository.findByRegistrationId("okta");
        logger.info("AuthController initialized with client registration: {}", clientRegistration.getClientName());
    }

    /**
     * Redirects users to the frontend.
     *
     * @param response HttpServletResponse used to set the redirect location.
     * @return ResponseEntity with status 302 (FOUND).
     */
    @GetMapping("/redirect")
    public ResponseEntity<Void> redirectFrontend(HttpServletResponse response) {
        String frontendUrl = "http://localhost:3000";
        logger.info("Redirecting to frontend at {}", frontendUrl);
        response.setHeader("Location", frontendUrl);
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }

    /**
     * Returns the authentication status and user role.
     *
     * @param authentication Spring Security Authentication object.
     * @return ResponseEntity containing authentication status and role.
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getAuthStatus(Authentication authentication) {
        logger.debug("Received request for authentication status");
        Map<String, Object> response = new HashMap<>();

        if (authentication != null && authentication.isAuthenticated()) {
            String role = determineUserRole(authentication);
            logger.info("User authenticated. Username: {}, Role: {}", authentication.getName(), role);
            response.put("authenticated", true);
            response.put("role", role);
        } else {
            logger.warn("User unauthenticated. Defaulting role to PATIENT.");
            response.put("authenticated", false);
            response.put("role", "PATIENT");
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Determines the user's role based on their granted authorities.
     *
     * @param authentication Spring Security Authentication object.
     * @return String representing the user's role ("ADMIN" or "PATIENT").
     */
    private String determineUserRole(Authentication authentication) {
        logger.debug("Determining user role for authentication object: {}", authentication);
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("ROLE_"))
                .findFirst()
                .orElse("PATIENT");
        logger.info("Determined user role: {}", role);
        return role;
    }
}