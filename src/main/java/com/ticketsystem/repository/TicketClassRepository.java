package com.ticketsystem.repository;

import com.ticketsystem.dto.response.EventResponse;
import com.ticketsystem.dto.response.TicketClassResponse;
import com.ticketsystem.entity.Event;
import com.ticketsystem.entity.TicketClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketClassRepository extends JpaRepository<TicketClass,Integer> {
    List<TicketClass> findAllByEvent(Event event);

    Optional<TicketClass> findByEventId(int id);

    Optional<TicketClass> findById(Integer id);

    @Query("SELECT tc FROM TicketClass tc JOIN FETCH tc.event e WHERE e.id = :eventId")
    Optional<TicketClass> findByEventId(@Param("eventId") Integer eventId);

}
