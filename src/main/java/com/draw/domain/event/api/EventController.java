package com.draw.domain.event.api;

import com.draw.domain.event.application.EventService;
import com.draw.domain.event.domain.Event;
import com.draw.domain.event.dto.EventCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<Void> createEvent(@RequestBody @Valid EventCreateRequest request) {
        Event event = eventService.create(1L, request); //todo 로그인 회원 정보 가져오기
        return ResponseEntity.created(URI.create(String.valueOf(event.getId()))).build();
    }
}
