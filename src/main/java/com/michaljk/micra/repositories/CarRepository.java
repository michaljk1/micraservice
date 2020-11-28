package com.michaljk.micra.repositories;

import com.michaljk.micra.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarRepository extends JpaRepository<Car, Long> {
}
