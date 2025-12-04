package com.prpo.chat.presence.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prpo.chat.presence.dto.PresenceDto;
import com.prpo.chat.presence.entity.Presence;
import com.prpo.chat.presence.service.PresenceService;

@RestController
@RequestMapping("/presence")
public class PresenceController {

    private final PresenceService presenceService;

    public PresenceController(PresenceService presenceService) {
        this.presenceService = presenceService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<PresenceDto> getPresence(@PathVariable String userId) {
        return presenceService.getPresence(userId)
            .map(this::toDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}/online")
    public PresenceDto setOnline(@PathVariable String userId) {
        Presence presence = presenceService.setOnline(userId);
        return toDto(presence);
    }

    @PutMapping("/{userId}/offline")
    public PresenceDto setOffline(@PathVariable String userId) {
        Presence presence = presenceService.setOffline(userId);
        return toDto(presence);
    }

    @GetMapping("/bulk")
    public List<PresenceDto> getBulkPresence(@RequestParam List<String> userIds) {
        return presenceService.getBulkPresence(userIds).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @GetMapping("/all")
    public List<PresenceDto> getAllPresence() {
        return presenceService.getAllPresence().stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    private PresenceDto toDto(Presence presence) {
        return new PresenceDto(
            presence.getUserId(),
            presence.getStatus(),
            presence.getLastSeen()
        );
    }
}
