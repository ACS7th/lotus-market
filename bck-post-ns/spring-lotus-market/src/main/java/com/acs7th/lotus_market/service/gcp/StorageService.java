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
    private String bucketName; // GCS 버킷 이름

    public String uploadToCloudStorage(MultipartFile file) throws IOException {
        String imageUrl = null;

        if (file != null && !file.isEmpty()) {
            // 고유 파일 이름 생성
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String uniqueFilename = UUID.randomUUID() + "_" + originalFilename;

            // GCS 클라이언트 생성
            Storage storage = StorageOptions.getDefaultInstance().getService();

            // GCS에 업로드
            BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, uniqueFilename).build(),
                file.getInputStream()
            );

            // 업로드된 파일 URL 반환
            imageUrl = String.format("https://storage.googleapis.com/%s/%s", bucketName, uniqueFilename);
        }

        return imageUrl;
    }
}
