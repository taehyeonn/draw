package com.draw.domain.event.api;

import com.draw.domain.event.application.EventService;
import com.draw.domain.event.domain.Event;
import com.draw.domain.event.dto.EventApplicationRequest;
import com.draw.domain.event.dto.EventCreateRequest;
import com.draw.domain.event.dto.EventResponse;
import com.draw.domain.event.dto.ParticipantResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<Void> createEvent(@RequestBody @Valid EventCreateRequest request) {
        Event event = eventService.create(1L, request); //todo 로그인 회원 정보 가져오기
        return ResponseEntity.created(URI.create(String.valueOf(event.getCode()))).build();
    }

    @GetMapping("/{eventCode}")
    public ResponseEntity<EventResponse> getEventResponse(@PathVariable("eventCode") int eventCode) {
        EventResponse response = eventService.getEventResponse(eventCode);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{eventCode}")
    public ResponseEntity<ParticipantResponse> applyForEvent(@RequestBody @Valid EventApplicationRequest request,
                                                             @PathVariable("eventCode") int eventCode) {
        ParticipantResponse response = eventService.apply(request, eventCode);
        return ResponseEntity.created(URI.create(String.valueOf(response.code()))).body(response);
    }
}