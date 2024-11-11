package me.synology.asordk.document;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "messages")
@Getter
@Setter
public class Messages {
    
    private String id;
    private String name;
    private String body;
    private LocalDateTime date;
    private String title;
    private String imageUrl;
    private int version;
    
}
