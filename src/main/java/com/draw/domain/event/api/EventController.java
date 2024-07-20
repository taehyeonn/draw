package com.draw.domain.event.api;

import com.draw.domain.event.application.EventService;
import com.draw.domain.event.domain.Event;
import com.draw.domain.event.domain.Participant;
import com.draw.domain.event.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<Void> createEvent(@RequestBody @Valid EventCreateRequest request) {
        Long memberId = 1L; // 임시 로그인 회원 정보
        Event event = eventService.create(memberId, request);
        return ResponseEntity.created(URI.create(String.valueOf(event.getCode()))).build();
    }

    @GetMapping("/{eventCode}")
    public ResponseEntity<EventResponse> getEventResponse(@PathVariable("eventCode") int eventCode) {
        EventResponse response = eventService.getEventResponse(eventCode);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{eventCode}")
    public ResponseEntity<ParticipantResponse> applyEvent(@RequestBody @Valid EventApplicationRequest request,
                                                          @PathVariable("eventCode") int eventCode) {
        ParticipantResponse response = eventService.apply(request, eventCode);
        return ResponseEntity.created(URI.create(String.valueOf(response.code()))).body(response);
    }

    @PostMapping("/{eventCode}/draw")
    public ResponseEntity<WinnersResponse> drawEvent(@PathVariable("eventCode") int eventCode) {
        Long memberId = 1L; // 임시 로그인 회원 정보
        List<Participant> winners = eventService.draw(memberId, eventCode);
        return ResponseEntity.ok().body(WinnersResponse.of(winners));
    }
}