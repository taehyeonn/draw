package com.draw.domain.event.dto;

import com.draw.domain.event.domain.Event;
import com.draw.domain.event.domain.Participant;
import jakarta.validation.constraints.NotNull;

public record EventApplicationRequest(@NotNull String email) {

    public Participant toEntity(Event event) {
        return Participant.of(email, event);
    }
}