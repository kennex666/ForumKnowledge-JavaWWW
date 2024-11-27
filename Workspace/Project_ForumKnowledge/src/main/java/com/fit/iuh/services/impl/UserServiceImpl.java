package com.fit.iuh.services.impl;

import com.fit.iuh.entites.User;
import com.fit.iuh.repositories.UserRepository;
import com.fit.iuh.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User findById(int id) {
        return userRepository.findById(id).get();
    }
}
