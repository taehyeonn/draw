package com.draw.domain.event.domain;

import com.draw.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_PARTICIPANT")
public class Participant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENT_ID")
    private Event event;

    protected Participant(Long id, String email, Event event) {
        this.id = id;
        this.email = email;
        this.event = event;
    }

    public static Participant of(String email, Event event) {
        return new Participant(null, email, event);
    }
}