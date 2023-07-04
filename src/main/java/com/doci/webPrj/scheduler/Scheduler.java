package com.doci.webPrj.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Scheduler {

    @Scheduled(cron = "0 0 0 * * *")
    void runScheduledTask() {

    }
}
