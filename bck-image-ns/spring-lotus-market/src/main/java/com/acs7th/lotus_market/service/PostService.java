package com.acs7th.lotus_market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.acs7th.lotus_market.model.Post;
import com.acs7th.lotus_market.repository.PostRepository;

import java.util.Date;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public void createPost(Post post) {
        if (post.getTitle() == null || post.getContent() == null || post.getDate() == null 
            || post.getItem() == null) {
            throw new IllegalArgumentException("title, content, date, item은 필수 항목입니다.");
        }

        if (post.getDate() == null) {
            post.setDate(new Date());
        }

        postRepository.save(post);
    }
}
