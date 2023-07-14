package com.keyon.sfilehub.dao;

import com.keyon.sfilehub.entity.FileStorage;
import com.keyon.sfilehub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileStorageDao extends JpaRepository<FileStorage, Long> {
    FileStorage findByIdentifier(String identifier);

    List<FileStorage> findByCreateBy(User user);
}
