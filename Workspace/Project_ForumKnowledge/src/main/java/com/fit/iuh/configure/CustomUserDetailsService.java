package com.fit.iuh.configure;

import com.fit.iuh.entites.User;
import com.fit.iuh.repositories.PostRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

//    De tam
    private final PostRepository userRepository;

    public CustomUserDetailsService(PostRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email);
        User user = new User();
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Chuyển role thành authority
        String role = user.getRole() == 1 ? "ROLE_ADMIN" : "ROLE_USER";

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
//                user.getPassword(),
                user.getName(),
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }
}