package com.prpo.chat.presence.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("presence")
public class Presence {

    @Id
    private String id;

    private String userId;

    private PresenceStatus status;

    @LastModifiedDate
    private Date lastSeen;

    public Presence() {}

    public Presence(String userId) {
        this.userId = userId;
        this.status = PresenceStatus.OFFLINE;
    }

    public String getId() { return id; }
    public String getUserId() { return userId; }
    public PresenceStatus getStatus() { return status; }
    public Date getLastSeen() { return lastSeen; }

    public void setId(String id) { this.id = id; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setStatus(PresenceStatus status) { this.status = status; }
    public void setLastSeen(Date lastSeen) { this.lastSeen = lastSeen; }
}
