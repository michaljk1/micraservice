package com.michaljk.micra.repositories;

import com.michaljk.micra.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;



public interface EventRepository extends JpaRepository<Event, Long> {
}
