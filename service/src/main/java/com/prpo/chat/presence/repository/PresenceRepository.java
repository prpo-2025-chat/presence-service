package com.prpo.chat.presence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.prpo.chat.presence.entity.Presence;
import com.prpo.chat.presence.entity.PresenceStatus;

public interface PresenceRepository extends CrudRepository<Presence, String> {
    
    List<Presence> findByStatus(PresenceStatus status);
}
