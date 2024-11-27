package me.synology.asordk.service;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import me.synology.asordk.document.Messages;
import me.synology.asordk.repository.MessagesRepository;

@Service
@Slf4j
public class MessagesService {
    private final MessagesRepository messagesRepository;
    private final MongoTemplate mongoTemplate;

    public MessagesService(MessagesRepository messagesRepository, MongoTemplate mongoTemplate) {
        this.messagesRepository = messagesRepository;
        this.mongoTemplate = mongoTemplate;
    }
    
    public List<Messages> getAllMessages() {
        return messagesRepository.findAll();
    }

    public Messages updateMessagesTitle(String id, String newTitle) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().set("title", newTitle);
        return mongoTemplate.findAndModify(query, update, Messages.class);
    }
}
