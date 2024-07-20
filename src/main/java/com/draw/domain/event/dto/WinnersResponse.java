package com.draw.domain.event.dto;

import com.draw.domain.event.domain.Participant;

import java.util.List;

public record WinnersResponse(int maxWinners, List<String> winners) {

    public static WinnersResponse of(List<Participant> participants) {
        return new WinnersResponse(participants.size(), participants.stream().map(Participant::getEmail).toList());
    }
}