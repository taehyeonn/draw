package com.draw.domain.event.domain;

import com.draw.domain.event.dto.EventCreateRequest;
import com.draw.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_GIFT")
public class Gift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private GiftType giftType;

    @Column
    private String string;

    @OneToOne
    @JoinColumn(name = "WINNER_ID")
    private Participant winner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENT_ID")
    private Event event;

    protected Gift(Long id, GiftType giftType, String string, Participant winner, Event event) {
        this.id = id;
        this.giftType = giftType;
        this.string = string;
        this.winner = winner;
        this.event = event;
    }

    public static Gift of(GiftType giftType, String string, Event event) {
        return new Gift(null, giftType, string, null, event);
    }

    public Gift addWinner(Participant participant) {
        this.winner = participant;
        return this;
    }
}
