package com.fit.iuh.utilities;

import com.fit.iuh.entites.User;
import com.fit.iuh.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
public class SpringContext {

    public static String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof org.springframework.security.core.userdetails.User) {
                org.springframework.security.core.userdetails.User userSecurity = (org.springframework.security.core.userdetails.User) principal;
                return userSecurity.getUsername(); // trả về email (username)
            } else {
                return "";
            }
        }
        return ""; // Hoặc xử lý trạng thái không đăng nhập
    }

}
