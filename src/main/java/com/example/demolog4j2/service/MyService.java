package com.example.demolog4j2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Service
public class MyService {
    private static final Logger logger = LoggerFactory.getLogger(MyService.class);

    @PostConstruct
    public void init() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                logger.info("Shutdown hook is running...");

                for(int i = 1; i <= 10; i ++) {
                    logger.info("shutdown progress: {} / 10", i);
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                // Perform any cleanup or finalization tasks here
                System.out.println("Cleanup tasks completed.");
            }
        });
    }

}
