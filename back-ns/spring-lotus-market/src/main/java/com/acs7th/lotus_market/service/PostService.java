package com.acs7th.lotus_market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.acs7th.lotus_market.model.Post;
import com.acs7th.lotus_market.repository.PostRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@Slf4j
public class PostService {

    @Autowired
    private PostRepository postRepository;

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
        } catch (Exception e) {
            log.error("Error occurred while creating Post: {}", e.getMessage(), e);
            throw new RuntimeException("Post 생성 중 문제가 발생했습니다.", e);
        }
    }

    public List<Post> getPostsContainingItem(String item) {
        return postRepository.findByItemContainingOrderByTimestampDesc(item);
    }

}