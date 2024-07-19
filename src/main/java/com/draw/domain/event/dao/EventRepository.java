package com.draw.domain.event.dao;

import com.draw.domain.event.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e JOIN FETCH e.member WHERE e.code = :code")
    Optional<Event> findEventByCodeWithMemberFetch(@Param("code") int code);
}