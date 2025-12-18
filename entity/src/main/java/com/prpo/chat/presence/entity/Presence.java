package com.prpo.chat.presence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("presence")
public class Presence implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private static final long DEFAULT_TTL_SECONDS = 60; // 1 min(test)

    @Id
    private String userId;

    @Indexed
    private PresenceStatus status;

    private Date lastSeen;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private Long ttl;

    public Presence() {}

    public Presence(String userId) {
        this.userId = userId;
        this.status = PresenceStatus.OFFLINE;
        this.lastSeen = new Date();
        this.ttl = DEFAULT_TTL_SECONDS;
    }

    public String getUserId() { return userId; }
    public PresenceStatus getStatus() { return status; }
    public Date getLastSeen() { return lastSeen; }
    public Long getTtl() { return ttl; }

    public void setUserId(String userId) { this.userId = userId; }
    public void setStatus(PresenceStatus status) { this.status = status; }
    public void setLastSeen(Date lastSeen) { this.lastSeen = lastSeen; }
    public void setTtl(Long ttl) { this.ttl = ttl; }
    
    public void refreshTtl() {
        this.ttl = DEFAULT_TTL_SECONDS;
        this.lastSeen = new Date();
    }
}
