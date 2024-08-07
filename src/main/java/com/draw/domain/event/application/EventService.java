package com.draw.domain.event.application;

import com.draw.domain.event.dao.EventRepository;
import com.draw.domain.event.dao.GiftRepository;
import com.draw.domain.event.dao.ParticipantRepository;
import com.draw.domain.event.domain.*;
import com.draw.domain.event.dto.EventApplicationRequest;
import com.draw.domain.event.dto.EventCreateRequest;
import com.draw.domain.event.dto.EventResponse;
import com.draw.domain.event.dto.ParticipantResponse;
import com.draw.domain.member.dao.MemberRepository;
import com.draw.domain.member.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;
    private final ParticipantRepository participantRepository;
    private final GiftRepository giftRepository;

    @Transactional
    public Event create(Long memberId, EventCreateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        Event event = request.toEntity(member);
        event.updateEventStatus(EventStatus.OPEN);
        Event savedEvent = eventRepository.save(event);
        request.gifts().stream()
                .map(string -> Gift.of(GiftType.TEXT, string, event))
                .map(giftRepository::save)
                .toList();
        return savedEvent;
    }

    public Event getEventByCode(int eventCode) {
        return eventRepository.findEventByCodeWithMemberFetch(eventCode)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이벤트입니다."));
    }

    public EventResponse getEventResponse(int eventCode) {
        return EventResponse.from(getEventByCode(eventCode));
    }

    @Transactional
    public ParticipantResponse apply(EventApplicationRequest request, int eventCode) {
        Event event = getEventByCode(eventCode);
        Participant participant = participantRepository.save(request.toEntity(event));
        return ParticipantResponse.of(participant);
    }

    @Transactional
    public List<Participant> draw(Long memberId, int eventCode) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        Event event = eventRepository.findEventByMemberAndCode(member, eventCode)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이벤트입니다."));
        if (event.getEventStatus() != EventStatus.OPEN) {
            throw new IllegalArgumentException("추첨할 수 없습니다.");
        }
        event.updateEventStatus(EventStatus.CLOSED);

        List<Participant> participants = participantRepository.findAllByEvent(event);
        List<Participant> winners = selectRandomParticipants(participants, event);
        List<Gift> gifts = giftRepository.findAllByEvent_Code(eventCode);
        for (int i = 0; i < winners.size() ; i++) {
            Gift gift = gifts.get(i).addWinner(winners.get(i));
            giftRepository.save(gift);
        }
        return winners;
    }

    private List<Participant> selectRandomParticipants(List<Participant> participants, Event event) {
        List<Participant> filteredParticipants = filterParticipants(participants, event);

        int maxWinners = event.getMaxWinners();
        if (filteredParticipants.size() <= maxWinners) {
            return filteredParticipants;
        }

        List<Participant> shuffledParticipants = new ArrayList<>(filteredParticipants);
        Collections.shuffle(shuffledParticipants, new Random());

        return shuffledParticipants.stream()
                .limit(maxWinners)
                .collect(Collectors.toList());
    }

    private List<Participant> filterParticipants(List<Participant> participants, Event event) {
        // 날짜 기준
        List<Participant> dateFilteredParticipants = participants.stream()
                .filter(participant -> {
                    LocalDateTime createdAt = participant.getCreatedAt();
                    return !createdAt.isBefore(event.getStartDateTime()) && !createdAt.isAfter(event.getEndDateTime());
                })
                .sorted(Comparator.comparing(Participant::getCreatedAt))
                .toList();

        if (dateFilteredParticipants.isEmpty()) {
            throw new RuntimeException("유효한 참가자가 없습니다.");
        }

        // 인원 기준
        int maxParticipants = event.getMaxParticipants();
        List<Participant> validParticipants = dateFilteredParticipants.stream()
                .limit(maxParticipants)
                .collect(Collectors.toList());

        if (validParticipants.isEmpty()) {
            throw new RuntimeException("유효한 참가자가 없습니다.");
        }
        return validParticipants;
    }
}