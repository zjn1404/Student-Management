package com.nqt.student_management.security;

import com.nqt.student_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Autowired
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authProvider;
    }

    @Bean
    @Autowired
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                configurer->configurer
                        .requestMatchers(HttpMethod.GET,"/").permitAll()
                        .requestMatchers(HttpMethod.GET,"/students/**").hasAnyRole("TEACHER", "ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.GET,"/students/search").hasAnyRole("TEACHER", "ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.POST,"/students/update").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.POST,"/students/delete").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/students/save").hasRole("ADMIN")
                        .requestMatchers("/register/**").permitAll()
                        .anyRequest().authenticated()
        ).formLogin(
                form->form.loginPage("/login")
                          .loginProcessingUrl("/authenticateTheUser")
                          .permitAll()
        ).logout(
                LogoutConfigurer::permitAll
        ).exceptionHandling(
                configurer->configurer.accessDeniedPage("/showPage403")
        );

        return http.build();
    }

}
