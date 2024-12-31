package com.acs7th.lotus_market.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.acs7th.lotus_market.model.PostIndex;

import java.util.List;

public interface PostIndexRepository extends ElasticsearchRepository<PostIndex, String> {
    List<PostIndex> findByTitleContaining(String title);
    List<PostIndex> findByItemContainingIgnoreCase(String item);
}