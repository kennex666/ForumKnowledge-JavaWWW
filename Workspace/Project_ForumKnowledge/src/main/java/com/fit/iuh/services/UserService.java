package com.fit.iuh.services;

import com.fit.iuh.entites.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers(Pageable page);

    public List<User> getAllUsers();

    public List<User> findUserByName(String keyword);

    public User findUserById(int id);
}
