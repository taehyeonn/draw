package com.draw.domain.event.dto;

import com.draw.domain.event.domain.Participant;

import java.time.LocalDateTime;

public record ParticipantResponse(String email, int code, LocalDateTime createdAt) {

    public static ParticipantResponse of(Participant participant) {
        return new ParticipantResponse(participant.getEmail(), participant.getEvent().getCode(), participant.getCreatedAt());
    }
}