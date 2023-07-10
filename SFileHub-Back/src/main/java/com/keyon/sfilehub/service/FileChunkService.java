package com.keyon.sfilehub.service;

import com.keyon.sfilehub.dao.FileChunkDao;
import com.keyon.sfilehub.dto.FileChunkDto;
import com.keyon.sfilehub.entity.FileChunk;
import com.keyon.sfilehub.vo.CheckResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileChunkService {
    @Autowired
    private FileChunkDao fileChunkDao;

    public CheckResultVo check(FileChunkDto dto) {
        CheckResultVo vo = new CheckResultVo();
        List<FileChunk> existChunks = fileChunkDao.findByIdentifier(dto.getIdentifier());
        if (existChunks.size() == 0) {
            vo.setUploaded(false);
            return vo;
        }
        ArrayList<Integer> uploadedFiles = new ArrayList<>();
        for (FileChunk chunk : existChunks) {
            if (chunk.getChunkNumber() == dto.getChunkNumber())
                vo.setUploaded(true);
            uploadedFiles.add(chunk.getChunkNumber());
        }
        vo.setUploadedChunks(uploadedFiles);
        return vo;
    }

    public void save(FileChunk fileChunk) {
        fileChunkDao.save(fileChunk);
    }
}
