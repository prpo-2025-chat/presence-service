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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/presence")
public class PresenceController {

    private final PresenceService presenceService;

    public PresenceController(PresenceService presenceService) {
        this.presenceService = presenceService;
    }

    @Operation(summary = "Get user presence", description = "Returns the current presence status and last seen time for a user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Presence found"),
            @ApiResponse(responseCode = "404", description = "User presence not found")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<PresenceDto> getPresence(
            @Parameter(description = "User ID", required = true) @PathVariable @NotBlank String userId) {
        return presenceService.getPresence(userId)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Set user online", description = "Sets the user's presence status to ONLINE and refreshes the TTL")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User set to online")
    })
    @PutMapping("/{userId}/online")
    public PresenceDto setOnline(
            @Parameter(description = "User ID", required = true) @PathVariable @NotBlank String userId) {
        Presence presence = presenceService.setOnline(userId);
        return toDto(presence);
    }

    @Operation(summary = "Set user offline", description = "Sets the user's presence status to OFFLINE")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User set to offline")
    })
    @PutMapping("/{userId}/offline")
    public PresenceDto setOffline(
            @Parameter(description = "User ID", required = true) @PathVariable @NotBlank String userId) {
        Presence presence = presenceService.setOffline(userId);
        return toDto(presence);
    }

    @Operation(summary = "Get bulk presence", description = "Returns presence status for multiple users at once")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Presence list retrieved successfully")
    })
    @GetMapping("/bulk")
    public List<PresenceDto> getBulkPresence(
            @Parameter(description = "List of user IDs", required = true) @RequestParam @NotEmpty List<String> userIds) {
        return presenceService.getBulkPresence(userIds).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get all presence data", description = "Returns presence status for all tracked users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Presence list retrieved successfully")
    })
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
                presence.getLastSeen());
    }
}
