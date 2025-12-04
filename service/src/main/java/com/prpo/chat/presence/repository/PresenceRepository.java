package com.prpo.chat.presence.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.prpo.chat.presence.entity.Presence;
import com.prpo.chat.presence.entity.PresenceStatus;

public interface PresenceRepository extends MongoRepository<Presence, String> {
    
    Optional<Presence> findByUserId(String userId);
    
    List<Presence> findByUserIdIn(List<String> userIds);
    
    List<Presence> findByStatusAndLastSeenBefore(PresenceStatus status, Date cutoff);
}
