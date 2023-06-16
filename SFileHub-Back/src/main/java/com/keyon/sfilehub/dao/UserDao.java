package com.keyon.sfilehub.dao;

import com.keyon.sfilehub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
