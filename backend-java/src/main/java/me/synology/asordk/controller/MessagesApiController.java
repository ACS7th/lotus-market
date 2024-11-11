package me.synology.asordk.controller;

import com.mongodb.MongoException;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import me.synology.asordk.document.Messages;
import me.synology.asordk.service.MessagesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/messages")
@Slf4j
public class MessagesApiController {
    private final MessagesService messagesService;

    public MessagesApiController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @GetMapping("/all")
    public List<Messages> getAllMessages() {
        log.info("Get all Messages...");
        return messagesService.getAllMessages();
    }

    @PatchMapping("/{messageId}/title")
    public ResponseEntity<?>
    updateMessageTitle(@PathVariable String messageId,
                       @RequestBody Map<String, String> req) {

        String newTitle = req.get("title");

        if (newTitle == null || newTitle.isEmpty()) {
            log.error("The 'title' key in the request body is empty or null");
            return ResponseEntity.badRequest().body("Title cannot be empty");
        }

        log.info("Updating message title: messageId - {}, newTitle - {}",
                 messageId, newTitle);

        try {
            messagesService.updateMessagesTitle(messageId, newTitle);
        } catch (MongoException e) {
            log.error("MongoDB error on updating message title");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Database error during update");
        }

        return ResponseEntity.ok().build();
    }
}
