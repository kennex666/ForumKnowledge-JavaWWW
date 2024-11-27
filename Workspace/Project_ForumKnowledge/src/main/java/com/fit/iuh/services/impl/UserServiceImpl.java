package com.fit.iuh.services.impl;

import com.fit.iuh.entites.User;
import com.fit.iuh.repositories.UserRepository;
import com.fit.iuh.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

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
}
