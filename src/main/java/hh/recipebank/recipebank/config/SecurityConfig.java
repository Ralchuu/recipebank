package hh.recipebank.recipebank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    // No explicit userDetailsService injected here. Use default configuration or add it later if needed.

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/").permitAll()
                .requestMatchers("/recipelist").permitAll()
                .requestMatchers("/recipe/*").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/addrecipe").hasAnyRole("ADMIN","USER")
                .requestMatchers("/editrecipe/**", "/deleterecipe/**").hasRole("ADMIN")
                .requestMatchers("/reviews/delete/**").hasRole("ADMIN")
                .requestMatchers("/users/**").hasRole("ADMIN")
                .anyRequest().permitAll()
        )
        .formLogin(form -> form
                .defaultSuccessUrl("/", true)
                .permitAll()
        )
        .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
        )
        .csrf(csrf -> csrf.disable());

    return http.build();
    }
}
