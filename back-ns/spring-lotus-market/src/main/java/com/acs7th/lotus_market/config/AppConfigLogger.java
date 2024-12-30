package com.acs7th.lotus_market.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class AppConfigLogger {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @PostConstruct
    public void logConfiguration() {
        log.info("### Application Configuration ###");
        log.info("MongoDB URI: {}", mongoUri);
        log.info("#################################");
    }
}

