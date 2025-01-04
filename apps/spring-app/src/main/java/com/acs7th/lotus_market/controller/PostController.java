package com.acs7th.lotus_market.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.acs7th.lotus_market.model.Post;
import com.acs7th.lotus_market.service.PostService;
import com.acs7th.lotus_market.service.gcp.StorageService;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/post")
@Slf4j
public class PostController {

    private final PostService postService;        
    private final StorageService storageService;  
    private final MeterRegistry meterRegistry;    

    @Autowired
    public PostController(PostService postService, StorageService storageService, MeterRegistry meterRegistry) {
        this.postService = postService;
        this.storageService = storageService;
        this.meterRegistry = meterRegistry;
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        log.info("get all posts!!!!");

        // GET 요청 수 기록
        meterRegistry.counter("api_post_requests", "method", "GET").increment();

        try {
            return ResponseEntity.ok(postService.getAllPosts());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("포스트를 불러오는 중 오류가 발생했습니다.");
        }
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<?> createPost(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("purchaseDate") String dateStr,
            @RequestParam("item") String item,
            @RequestParam(value = "images", required = false) MultipartFile imageFile) {
        log.info("post new post...");

        // POST 요청 수 기록
        meterRegistry.counter("api_post_requests", "method", "POST").increment();

        try {
            Date purchaseDate;
            try {
                purchaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            } catch (ParseException e) {
                purchaseDate = new Date();
            }

            Post newPost = Post.builder()
                    .title(title)
                    .content(content)
                    .purchaseDate(purchaseDate)
                    .item(item)
                    .imageUrl(storageService.uploadToCloudStorage(imageFile)) // image upload
                    .timestamp(new Date())
                    .build();

            postService.createPost(newPost);

            return new ResponseEntity<>("등록 성공", HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            log.error("모든 매개변수를 넣어야 합니다");
            return ResponseEntity.badRequest()
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (IOException e) {
            log.error("파일 업로드 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"파일 업로드 실패.\", \"details\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            log.error("응답 실패");
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"등록 실패\", \"details\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPosts(@RequestParam("item") String item) {
        log.info("search posts by item: {}", item);

        // /search 요청 수 기록
        meterRegistry.counter("api_post_search_requests").increment();

        try {
            return ResponseEntity.ok(postService.getPostsContainingItem(item));
        } catch (Exception e) {
            log.error("검색 실패");
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"검색 실패\", \"details\": \"" + e.getMessage() + "\"}");
        }
    }
}
