package com.acs7th.lotus_market.service.gcp;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class StorageService {

    @Value("${gcp.bucket.name}")
    private String bucketName; 

    public String uploadToCloudStorage(MultipartFile file) throws IOException {
        String imageUrl = null;

        if (file != null && !file.isEmpty()) {
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String uniqueFilename = UUID.randomUUID() + "_" + originalFilename;

            Storage storage = StorageOptions.getDefaultInstance().getService();

            BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, uniqueFilename).build(),
                file.getInputStream()
            );

            imageUrl = String.format("https://storage.googleapis.com/%s/%s", bucketName, uniqueFilename);
        }

        return imageUrl;
    }
}
