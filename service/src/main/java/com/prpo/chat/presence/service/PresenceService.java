package com.prpo.chat.presence.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.prpo.chat.presence.entity.Presence;
import com.prpo.chat.presence.entity.PresenceStatus;
import com.prpo.chat.presence.repository.PresenceRepository;

@Service
public class PresenceService {

    private final PresenceRepository presenceRepository;

    public PresenceService(PresenceRepository presenceRepository) {
        this.presenceRepository = presenceRepository;
    }

    public Optional<Presence> getPresence(String userId) {
        return presenceRepository.findByUserId(userId);
    }

    public Presence setOnline(String userId) {
        Presence presence = presenceRepository.findByUserId(userId)
            .orElse(new Presence(userId));
        
        presence.setStatus(PresenceStatus.ONLINE);
        return presenceRepository.save(presence);
    }

    public Presence setOffline(String userId) {
        Presence presence = presenceRepository.findByUserId(userId)
            .orElse(new Presence(userId));
        
        presence.setStatus(PresenceStatus.OFFLINE);
        return presenceRepository.save(presence);
    }

    public List<Presence> getBulkPresence(List<String> userIds) {
        return presenceRepository.findByUserIdIn(userIds);
    }

    public List<Presence> getAllPresence() {
        return presenceRepository.findAll();
    }

    public void cleanupInactiveUsers(long inactivityMinutes) {
        Date cutoff = new Date(System.currentTimeMillis() - (inactivityMinutes * 60 * 1000));
        
        List<Presence> inactiveUsers = presenceRepository
            .findByStatusAndLastSeenBefore(PresenceStatus.ONLINE, cutoff);
        
        for (Presence presence : inactiveUsers) {
            presence.setStatus(PresenceStatus.OFFLINE);
            presenceRepository.save(presence);
        }
    }
}
