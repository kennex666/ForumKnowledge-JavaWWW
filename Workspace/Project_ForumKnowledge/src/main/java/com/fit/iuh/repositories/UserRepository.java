package com.fit.iuh.repositories;

import com.fit.iuh.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
