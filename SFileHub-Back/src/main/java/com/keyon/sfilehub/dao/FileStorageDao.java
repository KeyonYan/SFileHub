package com.keyon.sfilehub.dao;

import com.keyon.sfilehub.entity.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileStorageDao extends JpaRepository<FileStorage, Long> {
    FileStorage findByIdentifier(String identifier);
}
