package com.michaljk.micra.services;

import com.michaljk.micra.models.Car;
import com.michaljk.micra.models.Trip;
import com.michaljk.micra.models.TripUser;
import com.michaljk.micra.repositories.CarRepository;
import com.michaljk.micra.repositories.TripRepository;
import com.michaljk.micra.services.api.car.NewCarRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CarService {

    final private CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void addCar(NewCarRequest newCar) throws Exception {
        if(carRepository.findAll().size() > 0 ){
            throw new Exception("Car already exists");
        }
        Car car = Car.builder()
                .name(newCar.getName())
                .mileage(newCar.getMileage())
                .build();
        carRepository.save(car);
    }

    public void updateCarMileage(Long kilometers){
        Car car = carRepository.findAll().stream().findFirst().orElseThrow();
        car.setMileage(car.getMileage() + kilometers);
        carRepository.save(car);
    }
}
