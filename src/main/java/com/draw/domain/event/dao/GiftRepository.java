package com.draw.domain.event.dao;

import com.draw.domain.event.domain.Gift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GiftRepository extends JpaRepository<Gift, Long> {

    List<Gift> findAllByEvent_Code(int code);
}