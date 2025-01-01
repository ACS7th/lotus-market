package com.acs7th.lotus_market.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.acs7th.lotus_market.model.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByItemContainingOrderByTimestampDesc(String item);
}
