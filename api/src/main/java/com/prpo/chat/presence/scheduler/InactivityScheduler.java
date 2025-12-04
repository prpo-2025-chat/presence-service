package com.prpo.chat.presence.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.prpo.chat.presence.service.PresenceService;

@Component
public class InactivityScheduler {

    private static final long INACTIVITY_MINUTES = 1;

    private final PresenceService presenceService;

    public InactivityScheduler(PresenceService presenceService) {
        this.presenceService = presenceService;
    }

    @Scheduled(fixedRate = 60000)  // 60 seconds
    public void cleanupInactiveUsers() {
        presenceService.cleanupInactiveUsers(INACTIVITY_MINUTES);
    }
}
