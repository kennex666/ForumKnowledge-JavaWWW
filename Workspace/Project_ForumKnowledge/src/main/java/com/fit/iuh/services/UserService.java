package com.fit.iuh.services;

import com.fit.iuh.entites.User;
import com.fit.iuh.enums.UserAccountState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface UserService {
    public List<User> getAllUsers(Pageable page);

    public List<User> getAllUsers();

    public List<User> findUserByName(String keyword);

    public User findUserById(int id);

    public User findUserByEmail(String email);

    public User findUserByEmailAndPassword(String email, String passwordHash);

    public void save(User user);

    public String registerUser(User user, int role);
    
    public User findById(int id);

    public Boolean changeState(int id, UserAccountState state);

    public Page<User> findUsersWithCondition(int skip, int limit, boolean isDesc);


    List<User> getUsersBetweenDates(Date startDate, Date endDate);

    public void edit(User user);
}
