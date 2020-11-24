package com.michaljk.micra.repositories;

import com.michaljk.micra.models.Trip;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface TripRepository extends MongoRepository<Trip, String> {
}
