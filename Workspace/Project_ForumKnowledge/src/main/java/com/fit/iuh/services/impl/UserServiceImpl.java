package com.fit.iuh.services.impl;

import com.fit.iuh.entites.Following;
import com.fit.iuh.entites.User;
import com.fit.iuh.enums.UserAccountState;
import com.fit.iuh.repositories.UserRepository;
import com.fit.iuh.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Boolean changeState(int id, UserAccountState state) {
        User user = userRepository.findById(id);
        if (user != null) {
            user.setAccountState(state);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public Page<User> findUsersWithCondition(int skip, int limit, boolean isDesc) {
        Pageable pageable = PageRequest.of(
                skip - 1,
                limit,
                isDesc ? Sort.by("userId").descending() : Sort.by("userId").ascending()
        );
        return userRepository.findAllUsers(pageable);
    }


    public List<User> getUsersBetweenDates(Date startDate, Date endDate) {
        return userRepository.findByCreatedAtBetween(startDate, endDate);
    }

    @Transactional
    @Override
    public void edit(User user) {
        userRepository.editUser(user.getName(), user.getEmail(), user.getTitle(), user.getProfilePicture(), user.getCoverPicture(), user.getDescription(), user.getBio(), user.getUserId());
    }

    @Override
    public List<Following> isFollowing(int currentUserId, int userId){
        return userRepository.isFollowing(currentUserId, userId);
    }

    @Override
    @Transactional
    public void follow(int currentUserId, int userId) {
        userRepository.follow(currentUserId, userId);
        return;
    }

    @Override
    @Transactional
    public void unfollow(int currentUserId, int userId) {
        userRepository.unfollow(currentUserId, userId);
        return;
    }
}
