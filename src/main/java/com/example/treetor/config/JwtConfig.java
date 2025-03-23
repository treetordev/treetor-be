package com.example.treetor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class JwtConfig {




  /*  @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.builder().
                username("MANISH")
                .password(passwordEncoder().encode("MANISH")).roles("ADMIN").
                build();
        return new InMemoryUserDetailsManager(userDetails);
    }
*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }

}