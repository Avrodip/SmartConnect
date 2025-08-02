package com.scm.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.scm.services.impl.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {
    // @Bean
    // public UserDetailsService userDetailsService() {
    // UserDetails user =
    // User.withDefaultPasswordEncoder().username("admin").password("admin123").roles("ADMIN").build();
    // var InMemoryUserDetailsManager = new InMemoryUserDetailsManager(user);
    // return InMemoryUserDetailsManager;
    // }
    @Autowired
    private SecurityCustomUserDetailService userDetailService;
    @Autowired
    private OAuthAuthenticationSuccessHandler handler;
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // user details serice object
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        // password encoder object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/login", "/register", "/home", "/services").permitAll()
                .requestMatchers("/user/**").authenticated()
                .anyRequest().permitAll()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login")  // Ensure this page exists
                .loginProcessingUrl("/authenticate")  // Spring Security will handle this POST request
                .successForwardUrl("/user/profile")
                // .failureForwardUrl("/login?error=true")
                .usernameParameter("email")
                .passwordParameter("password")
            );
            httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(logoutForm->{
            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        //oauth configurations
        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });
        return httpSecurity.build();
    }
    

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
