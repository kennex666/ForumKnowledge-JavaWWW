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
        return List.of();
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public List<User> findUserByName(String keyword) {
        return List.of();
    }

    @Override
    public User findUserById(int id) {
        return null;
    }
}
