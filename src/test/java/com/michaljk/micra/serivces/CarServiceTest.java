package com.michaljk.micra.serivces;

import com.michaljk.micra.config.AppConfig;
import com.michaljk.micra.models.Event;
import com.michaljk.micra.repositories.CarRepository;
import com.michaljk.micra.repositories.EventRepository;
import com.michaljk.micra.services.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CarServiceTest {

    private CarService carService;
    private EventRepository eventRepository;

    @BeforeEach
    void init() {
        eventRepository = mock(EventRepository.class);
        carService = new CarService(mock(CarRepository.class), eventRepository, mock(EntityManager.class));
    }

    @Test
    void shouldReturnFalseWhenNoInsuranceEventFound() {
        Date date = getJobDate(2021, Calendar.FEBRUARY, 25);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.MARCH, 10);
        assertFalse(carService.alarmBasedOnDate(date, null, AppConfig.INSURANCE_ALARM_MONTHS));
    }

    @Test
    void shouldReturnInsuranceAlarm() {
        Date date = getJobDate(2021, Calendar.FEBRUARY, 25);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.MARCH, 10);
        Event lastEvent = getEvent(calendar.getTime(), null);
        assertTrue(carService.alarmBasedOnDate(date, lastEvent, AppConfig.INSURANCE_ALARM_MONTHS));
    }


    @Test
    void shouldNotReturnInsuranceAlarm() {
        Date date = getJobDate(2021, Calendar.JANUARY, 9);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.MARCH, 10);
        Event lastEvent = getEvent(calendar.getTime(), null);
        assertFalse(carService.alarmBasedOnDate(date, lastEvent, AppConfig.INSURANCE_ALARM_MONTHS));
    }

    @Test
    void shouldReturnFalseWhenNoGasFilterChangeEventFound() {
        Long actualOdometer = 410000L;
        assertFalse(carService.alarmBasedOnKilometers(actualOdometer, null, AppConfig.GAS_FILTER_ALARM_KILOMETERS));
    }


    @Test
    void shouldReturnGasFilterAlarm() {
        Long actualOdometer = 410000L;
        Event lastEvent = getEvent(null, 405000L);
        assertTrue(carService.alarmBasedOnKilometers(actualOdometer, lastEvent, AppConfig.GAS_FILTER_ALARM_KILOMETERS));
    }

    @Test
    void shouldNotReturnGasFilterAlarm() {
        Long actualOdometer = 410000L;
        Event lastEvent = getEvent(null, 402000L);
        assertFalse(carService.alarmBasedOnKilometers(actualOdometer, lastEvent, AppConfig.GAS_FILTER_ALARM_KILOMETERS));
    }


    private static Event getEvent(Date date, Long odometer) {
        return Event.builder()
                .date(date)
                .odometer(odometer)
                .build();
    }

    private Date getJobDate(int year, int month, int day) {
        Calendar jobCalendar = Calendar.getInstance();
        jobCalendar.set(year, month, day);
        return jobCalendar.getTime();
    }

}
