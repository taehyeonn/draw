package com.draw.domain.event.dto;

import com.draw.domain.event.domain.Event;
import com.draw.domain.event.domain.EventType;
import com.draw.domain.member.domain.Member;

import java.time.LocalDateTime;
import java.util.List;

public record EventCreateRequest(String title, String content, EventType eventType, int maxParticipants,
                                 int maxWinners, LocalDateTime startDateTime, LocalDateTime endDateTime, List<String> gifts) {
    public Event toEntity(Member member) {
        return Event.of(title, content, eventType, maxParticipants, maxWinners, startDateTime, endDateTime, member);
    }
}