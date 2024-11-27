package com.fit.iuh.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Tắt CSRF (nếu không cần)
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/register").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN") // Chỉ ADMIN truy cập
                                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN") // USER hoặc ADMIN truy cập
                                .anyRequest().authenticated()
                        // Các yêu cầu khác cần đăng nhập
                )
                .formLogin(form -> form
                        .loginPage("/login") // URL trang đăng nhập
                        .defaultSuccessUrl("/", true) // Đăng nhập thành công sẽ chuyển hướng đến /home
                        .failureUrl("/login?error") // Đăng nhập thất bại sẽ chuyển hướng đến /login?error
                        .permitAll() // Ai cũng có thể truy cập trang đăng nhập
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Tạo session khi cần
                );

        return http.build();
    }

    // Auto service CustomUserDetails
//    @Bean
//    public UserDetailsService userDetailsService() {

    /// /        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    /// /        manager.createUser(
    /// /                User.withUsername("admin")
    /// /                        .password("{noop}password") // {noop} để bỏ qua mã hóa
    /// /                        .roles("ADMIN")
    /// /                        .build()
    /// /        );
    /// /        manager.createUser(
    /// /                User.withUsername("user")
    /// /                        .password("{noop}password")
    /// /                        .roles("USER")
    /// /                        .build()
    /// /        );
    /// /        return manager;
//
//        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
//        manager.setUsersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?");
//        manager.setAuthoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username = ?");
//        return manager;
//    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Sử dụng BCrypt để mã hóa mật khẩu
    }

}
