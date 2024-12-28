package com.acs7th.lotus_market.service.gcp;

import java.io.IOException;
import java.nio.file.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

    @Value("${file.upload.dir:uploads}")
    private String uploadDir;

    public String uploadToCloudStorage(MultipartFile file) throws IOException {
        String imageUrl = null;

        if (file != null && !file.isEmpty()) {
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

            Files.createDirectories(uploadPath);

            Path filePath = uploadPath.resolve(originalFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            imageUrl = "/uploads/" + originalFilename;
        }

        return imageUrl;
    }
}