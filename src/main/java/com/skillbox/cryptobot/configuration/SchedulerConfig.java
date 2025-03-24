package com.skillbox.cryptobot.configuration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "api.scheduler")
@EnableScheduling
public class SchedulerConfig {

    private int interval;
    private int notification;




}
