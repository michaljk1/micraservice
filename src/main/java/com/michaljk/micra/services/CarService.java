package com.michaljk.micra.services;

import com.michaljk.micra.models.Car;
import com.michaljk.micra.models.Event;
import com.michaljk.micra.repositories.CarRepository;
import com.michaljk.micra.repositories.EventRepository;
import com.michaljk.micra.services.dto.events.WSEventRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class CarService {

    final private CarRepository carRepository;
    final private EventRepository eventRepository;

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

    public void addEvent(WSEventRequest eventRequest) {
        Event event = new Event();
        event.setName(eventRequest.getName());
        event.setDate(eventRequest.getDate() == null ? new Date() : eventRequest.getDate());
        event.setDescription(eventRequest.getDescription());
        event.setPrice(eventRequest.getPrice());
        event.setOdometer(eventRequest.getOdometer());
        eventRepository.save(event);
    }
}
