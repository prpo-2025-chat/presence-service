package com.prpo.chat.presence.service;

import java.util.ArrayList;
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
        return presenceRepository.findById(userId);
    }

    public Presence setOnline(String userId) {
        Presence presence = presenceRepository.findById(userId)
            .orElse(new Presence(userId));
        
        presence.setStatus(PresenceStatus.ONLINE);
        presence.refreshTtl();
        return presenceRepository.save(presence);
    }

    public Presence setOffline(String userId) {
        Presence presence = presenceRepository.findById(userId)
            .orElse(new Presence(userId));
        
        presence.setStatus(PresenceStatus.OFFLINE);
        presence.refreshTtl();
        return presenceRepository.save(presence);
    }

    public List<Presence> getBulkPresence(List<String> userIds) {
        List<Presence> results = new ArrayList<>();
        for (String userId : userIds) {
            presenceRepository.findById(userId).ifPresent(results::add);
        }
        return results;
    }

    public List<Presence> getAllPresence() {
        List<Presence> results = new ArrayList<>();
        presenceRepository.findAll().forEach(p -> {
            if (p != null) results.add(p);
        });
        return results;
    }

    public List<Presence> getOnlineUsers() {
        return presenceRepository.findByStatus(PresenceStatus.ONLINE);
    }
}
