package com.keyon.sfilehub.util;

import cn.hutool.core.codec.Base64;
import com.keyon.sfilehub.entity.FileStorage;
import com.keyon.sfilehub.exception.BusinessException;
import com.keyon.sfilehub.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
public class BulkFileUtil {
    // FIXME: 下载没反应
    public static void downloadFile(FileStorage fileStorage, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        File file = new File(fileStorage.getFileLink());
        log.info("download file path: {}", file.getAbsolutePath());
        try {
            if (!file.exists()) {
                throw new BusinessException("文件不存在");
            }
            BufferedInputStream brInputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] buf = new byte[1024];
            int len = 0;
            response.reset();
            // 在线打开方式
            if (false) {
                URL u = new URL("file:///" + fileStorage.getFileLink());
                response.setContentType(u.openConnection().getContentType());
                response.setHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(fileStorage.getFileName(), "UTF-8"));
            } else { // 纯下载方式
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileStorage.getFileName(), "UTF-8"));
                response.setContentType("application/octet-stream; charset=utf-8");
            }
            OutputStream out = response.getOutputStream();
            while ((len = brInputStream.read(buf)) > 0){
                out.write(buf, 0, len);
            }
            brInputStream.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    public static String filenameEncoding(String filename, HttpServletRequest request) throws UnsupportedEncodingException {
        String agent = request.getHeader("User-Agent");
        if (agent.contains("MSIE")) {
            filename = URLEncoder.encode(filename, "utf-8");
        } else if (agent.contains("Firefox")) {
            Base64 base64Encoder = new Base64();
            filename = "=?utf-8?B?" + base64Encoder.encode(filename.getBytes("utf-8")) + "?=";
        } else {
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }
}
