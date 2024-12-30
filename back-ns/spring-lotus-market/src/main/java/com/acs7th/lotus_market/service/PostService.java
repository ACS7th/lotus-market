package com.acs7th.lotus_market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.acs7th.lotus_market.model.Post;
import com.acs7th.lotus_market.model.PostIndex;
import com.acs7th.lotus_market.repository.PostIndexRepository;
import com.acs7th.lotus_market.repository.PostRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@Slf4j
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostIndexRepository postIndexRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
    }

    public void createPost(Post post) {
        if (post.getTitle() == null || post.getContent() == null || post.getPurchaseDate() == null 
            || post.getItem() == null) {
            throw new IllegalArgumentException("title, content, date, item은 필수 항목입니다.");
        }
    
        try {
            // 1. Post 저장
            Post savedPost = postRepository.save(post);
            log.info("Post saved: {}", savedPost);
    
            // 2. Elasticsearch용 PostIndex 생성
            PostIndex postIndex = PostIndex.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .item(savedPost.getItem())
                .imageUrl(savedPost.getImageUrl())
                .purchaseDate(savedPost.getPurchaseDate())
                .timestamp(savedPost.getTimestamp())
                .build();
    
            // 3. Elasticsearch에 저장
            try {
                postIndexRepository.save(postIndex);
                log.info("PostIndex saved to Elasticsearch: {}", postIndex);
            } catch (Exception e) {
                log.error("Failed to save PostIndex to Elasticsearch: {}", e.getMessage(), e);
                throw new RuntimeException("Elasticsearch 저장 중 문제가 발생했습니다.", e);
            }
    
        } catch (Exception e) {
            log.error("Error occurred while creating Post: {}", e.getMessage(), e);
            throw new RuntimeException("Post 생성 중 문제가 발생했습니다.", e);
        }
    }
    
    public List<PostIndex> searchPostsByItemFromElasticsearch(String item) {
        return postIndexRepository.findByItemContainingIgnoreCase(item);
    }

}
