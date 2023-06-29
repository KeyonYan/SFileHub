package com.keyon.sfilehub.dao;

import com.keyon.sfilehub.entity.FileChunk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileChunkDao extends JpaRepository<FileChunk, Long> {
    List<FileChunk> findByIdentifier(String identifier);
}
