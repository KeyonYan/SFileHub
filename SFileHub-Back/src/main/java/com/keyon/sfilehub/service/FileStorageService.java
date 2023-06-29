package com.keyon.sfilehub.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import com.keyon.sfilehub.dao.FileChunkDao;
import com.keyon.sfilehub.dao.FileStorageDao;
import com.keyon.sfilehub.dto.FileChunkDto;
import com.keyon.sfilehub.entity.FileChunk;
import com.keyon.sfilehub.entity.FileStorage;
import com.keyon.sfilehub.entity.User;
import com.keyon.sfilehub.exception.ParameterException;
import com.keyon.sfilehub.exception.SystemException;
import com.keyon.sfilehub.util.BulkFileUtil;
import com.keyon.sfilehub.vo.CheckResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
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
        if (dto.getChunkFile() == null) {
            throw new ParameterException("文件不能为空");
        }
        if (dto.getIdentifier() == null) {
            throw new ParameterException("文件标识不能为空");
        }
        if (dto.getChunkNumber() == null || dto.getChunkNumber() < 1) {
            throw new ParameterException("文件块序号不能为空或必须从1开始");
        }
        if (dto.getChunkSize() == null || dto.getChunkSize() <= 0) {
            throw new ParameterException("文件块大小不能为空或必须大于0");
        }
        if (dto.getTotalChunks() == null || dto.getTotalChunks() <= 0) {
            throw new ParameterException("文件块总数不能为空或必须大于0");
        }

        CheckResultVo vo = fileChunkService.check(dto);
        if (vo.getUploaded()) {
            return true;
        }
        String fileSavePath = baseFileSavePath + File.separator + user.getUsername();
        File dir = new File(fileSavePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        fileSavePath += File.separator + dto.getFilename();
        boolean uploadFlag;
        if (dto.getTotalChunks() == 1) {
            uploadFlag = uploadSingleFile(fileSavePath, dto);
        } else {
            uploadFlag = uploadSharding(fileSavePath, dto);
        }
        FileChunk fileChunk = FileChunk.builder()
                .fileName(dto.getFilename())
                .chunkNumber(dto.getChunkNumber())
                .chunkSize(dto.getChunkSize())
                .currentChunkSize(dto.getCurrentChunkSize())
                .totalSize(dto.getTotalSize())
                .totalChunks(dto.getTotalChunks())
                .identifier(dto.getIdentifier())
                .createBy(user)
                .build();
        fileChunkService.save(fileChunk);
        if (dto.getChunkNumber().equals(dto.getTotalChunks())) {
            FileStorage fileStorage = FileStorage.builder()
                    .fileName(dto.getFilename())
                    .suffix(FileUtil.getSuffix(dto.getFilename()))
                    .size(dto.getTotalSize())
                    .identifier(dto.getIdentifier())
                    .createBy(user)
                    .build();
            fileStorageDao.save(fileStorage);
        }
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

    public void downloadByIdentifier(String identifier, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        FileStorage fileStorage = fileStorageDao.findByIdentifier(identifier);
        if (BeanUtil.isNotEmpty(fileStorage)) {
            File file = new File(baseFileSavePath + File.separator + fileStorage.getFileName()); // TODO
            BulkFileUtil.downloadFile(file, request, response);
        } else {
            throw new RuntimeException("文件不存在");
        }
    }
}
