package com.keyon.sfilehub.dao;

import com.keyon.sfilehub.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {

    Role findByTitle(String title);
}
