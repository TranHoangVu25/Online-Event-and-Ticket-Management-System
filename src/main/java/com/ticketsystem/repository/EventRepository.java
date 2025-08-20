package com.ticketsystem.repository;

import com.ticketsystem.entity.Event;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    List<Event> findAll();
    boolean existsByName(String name);
    Optional<Event> findByName(String name);
//    Optional<Event> findByLocation(String location);
}
