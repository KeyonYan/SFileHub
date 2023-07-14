package com.keyon.sfilehub.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import com.keyon.sfilehub.dao.FileStorageDao;
import com.keyon.sfilehub.dto.FileChunkDto;
import com.keyon.sfilehub.entity.FileChunk;
import com.keyon.sfilehub.entity.FileStorage;
import com.keyon.sfilehub.entity.User;
import com.keyon.sfilehub.exception.SystemException;
import com.keyon.sfilehub.util.BulkFileUtil;
import com.keyon.sfilehub.vo.CheckResultVo;
import com.keyon.sfilehub.vo.FileStorageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@Slf4j
public class FileStorageService {
    @Autowired
    private FileStorageDao fileStorageDao;
    @Autowired
    private FileChunkService fileChunkService;
    @Value("${file.chunk-size}")
    private Long defaultChunkSize;
    @Value("${file.path}")
    private String baseFileSavePath;

    public boolean uploadFile(FileChunkDto dto, User user) {
        CheckResultVo vo = fileChunkService.check(dto);
        if (vo.getUploaded()) return true;
        String fileSavePath = baseFileSavePath + File.separator + user.getUsername();
        File dir = new File(fileSavePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String suffix = FileUtil.getSuffix(dto.getFileName());
        fileSavePath += String.format("%s%s.%s", File.separator, dto.getIdentifier(), suffix);
        boolean uploadFlag;
        if (dto.getTotalChunks() == 1) {
            uploadFlag = uploadSingleFile(fileSavePath, dto);
        } else {
            uploadFlag = uploadSharding(fileSavePath, dto);
        }
        FileChunk fileChunk = FileChunk.builder()
                .fileName(dto.getFileName())
                .chunkNumber(dto.getChunkNumber())
                .chunkSize(dto.getChunkSize())
                .currentChunkSize(dto.getCurrentChunkSize())
                .totalSize(dto.getTotalSize())
                .totalChunks(dto.getTotalChunks())
                .identifier(dto.getIdentifier())
                .createBy(user)
                .build();
        fileChunkService.save(fileChunk);
        if (dto.getTotalChunks() != 1) {
            if (vo.getUploadedChunks() == null) return uploadFlag;
            int currentUploadedChunks = vo.getUploadedChunks().size() + (uploadFlag ? 1 : 0);
            if (currentUploadedChunks < dto.getTotalChunks()) return uploadFlag;
        }
        // all chunk is uploaded, save fileStorage
        FileStorage fileStorage = FileStorage.builder()
                .fileName(dto.getFileName())
                .fileLink(fileSavePath)
                .suffix(suffix)
                .size(dto.getTotalSize())
                .identifier(dto.getIdentifier())
                .createBy(user)
                .build();
        fileStorageDao.save(fileStorage);
        return uploadFlag;
    }

    public boolean uploadSingleFile(String fullFileName, FileChunkDto dto) {
        File localPath = new File(fullFileName);
        try {
            dto.getChunkFile().transferTo(localPath);
        } catch (IOException e) {
            throw new SystemException("文件写入失败");
        }
        return true;
    }

    public boolean uploadSharding(String saveFilePath, FileChunkDto dto) {
        try (RandomAccessFile raf = new RandomAccessFile(saveFilePath, "rw")) {
            long chunkSize = dto.getChunkSize() == 0L ? defaultChunkSize : dto.getChunkSize().longValue();
            long offset = chunkSize * (dto.getChunkNumber() - 1);
            raf.seek(offset);
            raf.write(dto.getChunkFile().getBytes());
        } catch (IOException e) {
            throw new SystemException(String.format("分片文件写入失败[Number: %d]", dto.getChunkNumber()));
        }
        return true;
    }

    public boolean hasFile(String identifier) {
        FileStorage fileStorage = fileStorageDao.findByIdentifier(identifier);
        if (fileStorage == null) return false;
        return true;
    }

    public void downloadByIdentifier(String identifier, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        FileStorage fileStorage = fileStorageDao.findByIdentifier(identifier);
        if (BeanUtil.isNotEmpty(fileStorage)) {
            BulkFileUtil.downloadFile(fileStorage, request, response);
        } else {
            throw new RuntimeException("文件不存在");
        }
    }

    public List<FileStorageVo> queryFileList(User user) {
        List<FileStorage> fileStorageList = fileStorageDao.findByCreateBy(user);
        List<FileStorageVo> fileStorageVoList = BeanUtil.copyToList(fileStorageList, FileStorageVo.class);
        fileStorageVoList.forEach(vo -> {
            vo.setCreateBy(user.getUsername());
        });
        return fileStorageVoList;
    }
}
