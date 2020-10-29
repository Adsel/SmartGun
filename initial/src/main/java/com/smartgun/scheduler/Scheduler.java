package com.smartgun.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.smartgun.service.GreetingService;
import com.smartgun.shared.Data;

@Component
public class Scheduler {
    private final GreetingService greetingService;

    Scheduler(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @Scheduled(fixedRateString = "3000", initialDelayString = "0")
    public void schedulingTask() {
        if (Data.isUser) {
            greetingService.sendMessages();
        }
    }
}