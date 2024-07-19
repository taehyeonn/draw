package com.draw.domain.event.dao;

import com.draw.domain.event.domain.Event;
import com.draw.domain.event.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    List<Participant> findAllByEvent(Event event);
}