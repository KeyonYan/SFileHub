package com.keyon.sfilehub.controller;

import com.keyon.sfilehub.dto.FileChunkDto;
import com.keyon.sfilehub.entity.User;
import com.keyon.sfilehub.exception.BusinessException;
import com.keyon.sfilehub.service.FileChunkService;
import com.keyon.sfilehub.service.FileStorageService;
import com.keyon.sfilehub.service.UserService;
import com.keyon.sfilehub.util.ResultUtil;
import com.keyon.sfilehub.vo.CheckResultVo;
import com.keyon.sfilehub.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.Principal;

@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private FileChunkService fileChunkService;
    @Autowired
    private UserService userService;

    @GetMapping("/uploadCheck")
    public Result<CheckResultVo> checkUpload(@Validated FileChunkDto dto) {
        return ResultUtil.success(fileChunkService.check(dto));
    }

    @PostMapping("/upload")
    public Result<Boolean> upload(@Validated FileChunkDto fileChunkDto, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        boolean status = fileStorageService.uploadFile(fileChunkDto, user);
        if (status) {
            return ResultUtil.success();
        } else {
            throw new BusinessException("文件上传失败");
        }
    }

    @GetMapping("/download/{identifier}")
    public void download(@PathVariable("identifier") String identifier, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        fileStorageService.downloadByIdentifier(identifier, request, response);
    }


}