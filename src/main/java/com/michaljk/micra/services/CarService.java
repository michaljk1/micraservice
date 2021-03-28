package com.michaljk.micra.services;

import com.michaljk.micra.config.AppConfig;
import com.michaljk.micra.models.Car;
import com.michaljk.micra.models.Event;
import com.michaljk.micra.repositories.CarRepository;
import com.michaljk.micra.repositories.EventRepository;
import com.michaljk.micra.services.dto.events.WSEventRequest;
import com.michaljk.micra.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@Service
@AllArgsConstructor
public class CarService {

    final private CarRepository carRepository;
    final private EventRepository eventRepository;

    public void updateCarOdometer(Long kilometers) {
        Car car = getCar();
        car.setOdometer(car.getOdometer() + kilometers);
        carRepository.save(car);
    }

    public void updateFuelUsage(Float fuelUsage) {
        Car car = getCar();
        car.setFuelUsage(fuelUsage);
        carRepository.save(car);
    }

    public Car getCar() {
        return carRepository.findAll().stream().findFirst().orElseThrow();
    }

    @Transactional(REQUIRES_NEW)
    public void addEvent(WSEventRequest eventRequest) {
        Event event = Event.builder()
                .name(eventRequest.getName())
                .date(eventRequest.getDate() == null ? new Date() : eventRequest.getDate())
                .description(eventRequest.getDescription())
                .price(eventRequest.getPrice())
                .odometer(eventRequest.getOdometer())
                .build();
        eventRepository.save(event);
    }

    public boolean alarmBasedOnDate(Date baseDate, String eventType, int alarmMonths) {
        Event lastEvent = eventRepository.findTopByTypeOrderByDateDateDesc(eventType).orElse(null);
        if (lastEvent != null) {
            Date lastInsuranceDate = lastEvent.getDate();
            return baseDate.after(DateUtils.addMonthsToDate(lastInsuranceDate, alarmMonths));
        }
        return false;
    }

    public boolean alarmBasedOnKilometers(Long actualOdometer, String eventType, int alarmKilometers) {
        Event lastEvent = eventRepository.findTopByTypeOrderByDateDateDesc(eventType).orElse(null);
        if (lastEvent != null) {
            Long lastEventOdometer = lastEvent.getOdometer();
            return lastEventOdometer + alarmKilometers > actualOdometer;
        }
        return false;
    }
}
