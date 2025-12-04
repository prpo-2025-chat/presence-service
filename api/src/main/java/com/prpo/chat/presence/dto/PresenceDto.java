package com.prpo.chat.presence.dto;

import java.util.Date;

import com.prpo.chat.presence.entity.PresenceStatus;

public class PresenceDto {

    private String userId;
    private PresenceStatus status;
    private Date lastSeen;

    public PresenceDto() {}

    public PresenceDto(String userId, PresenceStatus status, Date lastSeen) {
        this.userId = userId;
        this.status = status;
        this.lastSeen = lastSeen;
    }

    public String getUserId() { return userId; }
    public PresenceStatus getStatus() { return status; }
    public Date getLastSeen() { return lastSeen; }

    public void setUserId(String userId) { this.userId = userId; }
    public void setStatus(PresenceStatus status) { this.status = status; }
    public void setLastSeen(Date lastSeen) { this.lastSeen = lastSeen; }
}
