package com.michaljk.micra.services;

import com.michaljk.micra.config.AppConfig;
import com.michaljk.micra.models.Balance;
import com.michaljk.micra.models.Car;
import com.michaljk.micra.models.Event;
import com.michaljk.micra.repositories.CarRepository;
import com.michaljk.micra.repositories.EventRepository;
import com.michaljk.micra.services.dto.events.WSEventRequest;
import com.michaljk.micra.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.michaljk.micra.models.Event.Type.*;
import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@Service
@AllArgsConstructor
public class CarService {

    final private CarRepository carRepository;
    final private EventRepository eventRepository;
    @PersistenceContext
    private final EntityManager entityManager;

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
    public void saveEvent(Event event) {
        eventRepository.save(event);
    }

    @Scheduled(cron = "0 0 0 ? * MON")
    public void carAlarm() {
        Long actualOdometer = getCar().getOdometer();
        Date currentDate = new Date();

        List<String> alarmTypes = Arrays.asList(CAR_REVIEW, FILTERS_REPLACEMENT, INSURANCE, GAS_FILTER_CHANGE);
        List<Event> events = getEventsForAlarm(alarmTypes);

        Event lastCarReviewEvent = getEventByType(events, CAR_REVIEW);
        alarmBasedOnDate(currentDate, lastCarReviewEvent, AppConfig.CAR_REVIEW_ALARM_MONTHS);

        Event lastFilterReplacementEvent = getEventByType(events, FILTERS_REPLACEMENT);
        boolean kilometersFilterAlarm = alarmBasedOnKilometers(actualOdometer, lastFilterReplacementEvent, AppConfig.FILTER_EXCHANGE_ALARM_KILOMETERS);
        if(Boolean.FALSE.equals(kilometersFilterAlarm)) {
            alarmBasedOnDate(currentDate, lastFilterReplacementEvent, AppConfig.FILTER_EXCHANGE_ALARM_MONTHS);
        }
        Event lastInsuranceEvent = getEventByType(events, INSURANCE);
        alarmBasedOnDate(currentDate, lastInsuranceEvent, AppConfig.CAR_REVIEW_ALARM_MONTHS);

        Event lastGasFilterExchangeEvent = getEventByType(events, GAS_FILTER_CHANGE);
        alarmBasedOnKilometers(actualOdometer, lastGasFilterExchangeEvent, AppConfig.GAS_FILTER_ALARM_KILOMETERS);
    }

    public boolean alarmBasedOnDate(Date baseDate, Event lastEvent, int alarmMonths) {
        if (lastEvent != null) {
            Date lastInsuranceDate = lastEvent.getDate();
            return baseDate.after(DateUtils.addMonthsToDate(lastInsuranceDate, alarmMonths));
        }
        return false;
    }

    public boolean alarmBasedOnKilometers(Long actualOdometer, Event lastEvent, int alarmKilometers) {
        if (lastEvent != null) {
            Long lastEventOdometer = lastEvent.getOdometer();
            return lastEventOdometer + alarmKilometers > actualOdometer;
        }
        return false;
    }

    private List<Event> getEventsForAlarm(List<String> eventTypes) {
        String queryString = "SELECT * from Events WHERE (evt_type, evt_date) IN " +
                "( SELECT evt_type, MAX(evt_date) FROM events where evt_type in ('" + String.join("','", eventTypes) + "') GROUP BY evt_type)";
        return entityManager.createNativeQuery(queryString, Event.class).getResultList();
    }

    private Event getEventByType(List<Event> events, String type) {
        return events.stream().filter(ev -> type.equals(ev.getType())).findFirst().orElse(null);
    }
}
