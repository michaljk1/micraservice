package com.michaljk.micra.repositories;

import com.michaljk.micra.models.TripUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TripUserRepository extends JpaRepository<TripUser, Long> {
}
