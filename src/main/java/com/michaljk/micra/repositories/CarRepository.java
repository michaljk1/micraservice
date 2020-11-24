package com.michaljk.micra.repositories;

import com.michaljk.micra.models.Car;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CarRepository extends MongoRepository<Car, String> {
}
