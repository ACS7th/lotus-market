package com.acs7th.lotus_market.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class MongoInitLogger {

    @Value("${spring.data.mongodb.uri:no_input_mongouri}")
    private String mongoUri;

    @PostConstruct
    public void initLogger() {
        int count = 0;
        boolean connectionSuccessful = false;

        while (count < 10 && !connectionSuccessful) {
            log.info("### Database Configuration ###");
            log.info("MongoDB URI: {}", mongoUri);
            log.info("Execution count: {}", count + 1);
            log.info("#################################");

            try (MongoClient mongoClient = MongoClients.create(MongoClientSettings.builder()
                    .applyConnectionString(new com.mongodb.ConnectionString(mongoUri))
                    .build())) {

                MongoDatabase database = mongoClient.getDatabase("lotus-db");
                database.listCollectionNames().first(); 
                
                log.info("MongoDB 연결 성공");
                connectionSuccessful = true;
            } catch (Exception e) {
                log.error("MongoDB 연결 실패: {}", e.getMessage());
            }

            if (!connectionSuccessful) {
                count++;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    log.error("Sleep interrupted", e);
                    Thread.currentThread().interrupt(); // 인터럽트 상태 복구
                    break;
                }
            }
        }

        if (!connectionSuccessful) {
            log.error("### MongoDB 연결에 실패했습니다. 10번의 시도 후 종료합니다. ###");
        }
    }
}
