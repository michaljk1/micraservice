package com.michaljk.micra.services;

import com.michaljk.micra.models.Car;
import com.michaljk.micra.repositories.CarRepository;
import com.michaljk.micra.services.api.car.WSCarRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CarService {

    final private CarRepository carRepository;

    public void addCar(WSCarRequest newCar) throws Exception {
        if(carRepository.findAll().size() > 0 ){
            throw new Exception("Car already exists");
        }
        Car car = new Car();
        car.setName(newCar.getName());
        car.setMileage(newCar.getMileage());
        carRepository.save(car);
    }

    public void updateCarMileage(Long kilometers){
        Car car = getCar();
        car.setMileage(car.getMileage() + kilometers);
        carRepository.save(car);
    }

    public Car getCar(){
        return carRepository.findAll().stream().findFirst().orElseThrow();
    }
}
