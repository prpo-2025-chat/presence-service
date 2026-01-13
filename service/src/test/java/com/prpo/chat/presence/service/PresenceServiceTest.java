package com.prpo.chat.presence.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prpo.chat.presence.entity.Presence;
import com.prpo.chat.presence.entity.PresenceStatus;
import com.prpo.chat.presence.repository.PresenceRepository;

@ExtendWith(MockitoExtension.class)
class PresenceServiceTest {

    @Mock
    private PresenceRepository presenceRepository;

    @InjectMocks
    private PresenceService presenceService;

    @Test
    void setOnline_createsNewPresenceIfNotExists() {
        String userId = "user-1";
        when(presenceRepository.findById(userId)).thenReturn(Optional.empty());
        when(presenceRepository.save(any(Presence.class))).thenAnswer(inv -> inv.getArgument(0));

        Presence result = presenceService.setOnline(userId);

        assertEquals(PresenceStatus.ONLINE, result.getStatus());
        assertNotNull(result.getLastSeen());

        ArgumentCaptor<Presence> captor = ArgumentCaptor.forClass(Presence.class);
        verify(presenceRepository).save(captor.capture());
        assertEquals(PresenceStatus.ONLINE, captor.getValue().getStatus());
    }

    @Test
    void setOnline_updatesExistingPresence() {
        String userId = "user-1";
        Presence existing = new Presence(userId);
        existing.setStatus(PresenceStatus.OFFLINE);

        when(presenceRepository.findById(userId)).thenReturn(Optional.of(existing));
        when(presenceRepository.save(any(Presence.class))).thenAnswer(inv -> inv.getArgument(0));

        Presence result = presenceService.setOnline(userId);

        assertEquals(PresenceStatus.ONLINE, result.getStatus());
        verify(presenceRepository).save(existing);
    }

    @Test
    void setOffline_updatesStatusToOffline() {
        String userId = "user-1";
        Presence existing = new Presence(userId);
        existing.setStatus(PresenceStatus.ONLINE);

        when(presenceRepository.findById(userId)).thenReturn(Optional.of(existing));
        when(presenceRepository.save(any(Presence.class))).thenAnswer(inv -> inv.getArgument(0));

        Presence result = presenceService.setOffline(userId);

        assertEquals(PresenceStatus.OFFLINE, result.getStatus());
        verify(presenceRepository).save(existing);
    }

    @Test
    void getPresence_returnsPresenceIfExists() {
        String userId = "user-1";
        Presence presence = new Presence(userId);
        presence.setStatus(PresenceStatus.ONLINE);

        when(presenceRepository.findById(userId)).thenReturn(Optional.of(presence));

        Optional<Presence> result = presenceService.getPresence(userId);

        assertTrue(result.isPresent());
        assertEquals(PresenceStatus.ONLINE, result.get().getStatus());
    }

    @Test
    void getPresence_returnsEmptyIfNotExists() {
        String userId = "user-1";
        when(presenceRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<Presence> result = presenceService.getPresence(userId);

        assertTrue(result.isEmpty());
    }

    @Test
    void getBulkPresence_returnsOnlyExistingUsers() {
        Presence p1 = new Presence("user-1");
        p1.setStatus(PresenceStatus.ONLINE);

        when(presenceRepository.findById("user-1")).thenReturn(Optional.of(p1));
        when(presenceRepository.findById("user-2")).thenReturn(Optional.empty());

        List<Presence> result = presenceService.getBulkPresence(List.of("user-1", "user-2"));

        assertEquals(1, result.size());
        assertEquals("user-1", result.get(0).getUserId());
    }

    @Test
    void getOnlineUsers_delegatesToRepository() {
        Presence p1 = new Presence("user-1");
        p1.setStatus(PresenceStatus.ONLINE);

        when(presenceRepository.findByStatus(PresenceStatus.ONLINE)).thenReturn(List.of(p1));

        List<Presence> result = presenceService.getOnlineUsers();

        assertEquals(1, result.size());
        verify(presenceRepository).findByStatus(PresenceStatus.ONLINE);
    }
}
