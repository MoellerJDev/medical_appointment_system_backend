package com.medical.medical_appointment_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import com.medical.medical_appointment_system.filter.CookieCsrfFilter;


//THIS IS LITERALLY ALL THE MIDDLEWARE FOR THE SECURITY OF THE APP
@Configuration
public class SecurityConfig {

    @Bean
    // configure access to API, this blocks out the endpoints that are accessible to public vs admin
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // permit all requests to the following paths
        http.authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/index.html", "/static/**",
                                "/*.ico", "/*.json", "/*.png", "/api/user", "/login/oauth2/code/okta", "api/**", "/api/logout", "/api/list", "/api/create", "/api/specializations/**", "api/specialization/**", "/api/doctors/**").permitAll()
                        .anyRequest().authenticated()
                );
        // create custom cookie
        http.csrf((csrf) -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        // https://stackoverflow.com/a/74521360/65681
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                        .ignoringRequestMatchers("/appointments/new")//permits public acess for POST, PUT, DELETE
                );
        http.addFilterAfter(new CookieCsrfFilter(), BasicAuthenticationFilter.class);
        http.oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("http://localhost:3000/admin/admin-dashboard", true) // Redirect to frontend after success
                );

        return http.build();
    }

}