package com.michaljk.micra.repositories;

import com.michaljk.micra.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TripRepository extends JpaRepository<Trip, Long> {
}
