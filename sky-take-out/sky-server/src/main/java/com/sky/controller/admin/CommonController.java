package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Slf4j
public class CommonController {

    private static String UPLOAD_DIR = "../uploads/";

    @PostMapping("/upload")
    public Result<String> uploadImg(MultipartFile file) {
        log.info("file upload", file);
        try {
            // Ensure the upload directory exists
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            // Generate a unique file name
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);

            // Save the file to the disk
            Files.write(filePath, file.getBytes());

            // Return the file URL
            String fileUrl = "http://localhost:8080/uploads/" + fileName;
            return Result.success(fileUrl);
        } catch (Exception e) {
            log.error("Image upload error", e);
        }
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }
}
