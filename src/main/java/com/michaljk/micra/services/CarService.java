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
        car.setOdometer(newCar.getOdometer());
        carRepository.save(car);
    }

    public void updateCarOdometer(Long kilometers){
        Car car = getCar();
        car.setOdometer(car.getOdometer() + kilometers);
        carRepository.save(car);
    }

    public void updateFuelUsage(Float fuelUsage){
        Car car = getCar();
        car.setFuelUsage(fuelUsage);
        carRepository.save(car);
    }

    public Car getCar(){
        return carRepository.findAll().stream().findFirst().orElseThrow();
    }
}
