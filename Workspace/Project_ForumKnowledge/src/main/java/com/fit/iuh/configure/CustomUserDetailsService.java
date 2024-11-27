package com.fit.iuh.configure;

import com.fit.iuh.services.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder; // Inject PasswordEncoder

    public CustomUserDetailsService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println(
                "Email: " + username
        );
        com.fit.iuh.entites.User user = userService.findUserByEmail(username);

        if (user == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }

        // Chuyển role thành authority
        String role = user.getRole() == 1 ? "ROLE_ADMIN" : "ROLE_USER";

        // So sánh mật khẩu (đã mã hóa trong database với mật khẩu nhập vào)
        // Thực tế Spring Security tự động làm việc này khi đăng nhập
        System.out.println("User found");
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(), // Mật khẩu mã hóa
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }
}
