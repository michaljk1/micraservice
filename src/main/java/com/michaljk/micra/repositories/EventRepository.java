package com.michaljk.micra.repositories;

import com.michaljk.micra.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findTopByTypeOrderByDateDateDesc(String type);
}
