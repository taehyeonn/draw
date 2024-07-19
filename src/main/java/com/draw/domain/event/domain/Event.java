package com.draw.domain.event.domain;

import com.draw.domain.member.domain.Member;
import com.draw.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Random;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "EVENT")
public class Event extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int code;

    @Column
    private String title;

    @Column
    private String content;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column
    private int totalParticipants;

    @Column
    private int winnerCount;

    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time", nullable = false)
    private LocalDateTime endDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected Event(Long id, int code, String title, String content, EventType eventType, int totalParticipants,
                    int winnerCount, LocalDateTime startDateTime, LocalDateTime endDateTime, Member member) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.content = content;
        this.eventType = eventType;
        this.totalParticipants = totalParticipants;
        this.winnerCount = winnerCount;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.member = member;
    }

    public static Event of(String title, String content, EventType eventType, int totalParticipants, int winnerCount,
                           LocalDateTime startDateTime, LocalDateTime endDateTime, Member member) {
        return new Event(null, generateRandomCode(), title, content, eventType, totalParticipants, winnerCount, startDateTime, endDateTime, member);
    }

    private static int generateRandomCode() { //todo 임시
        Random random = new Random();
        return 10000000 + random.nextInt(90000000); // 랜덤한 8자리 정수를 생성. 10000000 to 99999999
    }
}