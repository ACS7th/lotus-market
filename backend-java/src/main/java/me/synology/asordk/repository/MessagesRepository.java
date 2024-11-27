package me.synology.asordk.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import me.synology.asordk.document.Messages;

@Repository
public interface MessagesRepository extends MongoRepository<Messages, String>{
}