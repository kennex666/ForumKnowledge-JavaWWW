package com.fit.iuh.services.impl;

import com.fit.iuh.entites.User;
import com.fit.iuh.enums.UserAccountState;
import com.fit.iuh.repositories.UserRepository;
import com.fit.iuh.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers(Pageable page) {
        return userRepository.findAll(page).getContent();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findUserByName(String keyword) {
        return userRepository.findByNameContaining(keyword);
    }

    @Override
    public User findUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByEmailAndPassword(String email, String passwordHash) {
        return userRepository.findByEmailAndPassword(email, passwordHash);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public String registerUser(User user, int role) {
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));

        User userInRecord =  userRepository.existsByEmail(user.getEmail());

        if (userInRecord != null) {
            return "Người dùng đã sử dụng Email này rồi!";
        }

        User savedUser = new User();

        savedUser.setName(user.getName());
        savedUser.setRole(role);
        savedUser.setAccountState(user.getAccountState());
        savedUser.setEmail(user.getEmail());
        savedUser.setPasswordHash(user.getPasswordHash());
        try {
            userRepository.save(savedUser);
            return "Đăng ký thành công!";
        } catch (Exception e) {
            return "Đã có lỗi xảy ra!";
        }
    }
  
    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void changeState(int id, UserAccountState state) {
        User user = userRepository.findById(id);
        user.setAccountState(state);
        userRepository.save(user);
    }

    @Override
    public List<User> getUsersBetweenDates(Date startDate, Date endDate) {
        return userRepository.findByCreatedAtBetween(startDate, endDate);
    }
}
