package com.draw.domain.event.application;

import com.draw.domain.event.dao.EventRepository;
import com.draw.domain.event.domain.Event;
import com.draw.domain.event.dto.EventCreateRequest;
import com.draw.domain.event.dto.EventResponse;
import com.draw.domain.member.dao.MemberRepository;
import com.draw.domain.member.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Event create(Long memberId, EventCreateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        Event event = request.toEntity(member);
        eventRepository.save(event);
        return event;
    }

    public Event getEventByCode(int eventCode) {
        return eventRepository.findEventByCodeWithMemberFetch(eventCode)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이벤트입니다."));
    }

    public EventResponse getEventResponse(int eventCode) {
        return EventResponse.from(getEventByCode(eventCode));
    }
}