package com.draw.domain.event.dto;

import com.draw.domain.event.domain.Event;
import com.draw.domain.event.domain.EventStatus;
import com.draw.domain.event.domain.EventType;

import java.time.LocalDateTime;

public record EventResponse(String title, String content, EventType eventType, EventStatus eventStatus, int maxParticipants,
                            int maxWinners, LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime createdAt,
                            String hostNickname) {

    public static EventResponse from(Event event) {
        return new EventResponse(event.getTitle(), event.getContent(), event.getEventType(), event.getEventStatus(),
                event.getMaxParticipants(), event.getMaxWinners(), event.getStartDateTime(),
                event.getEndDateTime(), event.getCreatedAt(), event.getMember().getNickname());
    }
}