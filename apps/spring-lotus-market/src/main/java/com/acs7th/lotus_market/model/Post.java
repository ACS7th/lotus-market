package com.acs7th.lotus_market.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "post")
public class Post {
    @Id
    private String id;
    private String title;
    private String content;
    private String item;
    private String imageUrl;
    private Date purchaseDate;
    private Date timestamp;
}
