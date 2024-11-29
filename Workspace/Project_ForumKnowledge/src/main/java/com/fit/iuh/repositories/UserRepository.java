package com.fit.iuh.repositories;

import com.fit.iuh.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.name LIKE %:keyword%")
    public List<User> findByNameContaining(String keyword);

    @Query("SELECT u FROM User u WHERE u.email LIKE %:email%")
    public User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.userId = :userId")
    public User findById(int userId);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.passwordHash = :passwordHash")
    public User findByEmailAndPassword(String email, String passwordHash);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User existsByEmail(String email);
}
