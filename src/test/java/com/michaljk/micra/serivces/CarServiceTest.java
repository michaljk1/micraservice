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
        carService = new CarService(mock(CarRepository.class), eventRepository);
    }

    @Test
    void shouldReturnFalseWhenNoInsuranceEventFound() {
        Date date = getJobDate(2021, Calendar.FEBRUARY, 25);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.MARCH, 10);
        when(eventRepository.findTopByTypeOrderByDateDateDesc(eq(Event.Type.INSURANCE))).thenReturn(Optional.empty());
        assertFalse(carService.alarmBasedOnDate(date, Event.Type.INSURANCE, AppConfig.INSURANCE_ALARM_MONTHS));
    }

    @Test
    void shouldReturnInsuranceAlarm() {
        Date date = getJobDate(2021, Calendar.FEBRUARY, 25);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.MARCH, 10);
        when(eventRepository.findTopByTypeOrderByDateDateDesc(eq(Event.Type.INSURANCE))).thenReturn(Optional.of(getEvent(calendar.getTime(), null)));
        assertTrue(carService.alarmBasedOnDate(date, Event.Type.INSURANCE, AppConfig.INSURANCE_ALARM_MONTHS));
    }


    @Test
    void shouldNotReturnInsuranceAlarm() {
        Date date = getJobDate(2021, Calendar.JANUARY, 9);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.MARCH, 10);
        when(eventRepository.findTopByTypeOrderByDateDateDesc(eq(Event.Type.INSURANCE))).thenReturn(Optional.of(getEvent(calendar.getTime(), null)));
        assertFalse(carService.alarmBasedOnDate(date, Event.Type.INSURANCE, AppConfig.INSURANCE_ALARM_MONTHS));
    }

    @Test
    void shouldReturnFalseWhenNoGasFilterChangeEventFound() {
        Long actualOdometer = 410000L;
        when(eventRepository.findTopByTypeOrderByDateDateDesc(eq(Event.Type.GAS_FILTER_CHANGE))).thenReturn(Optional.empty());
        assertFalse(carService.alarmBasedOnKilometers(actualOdometer, Event.Type.GAS_FILTER_CHANGE, AppConfig.GAS_FILTER_ALARM_KILOMETERS));
    }


    @Test
    void shouldReturnGasFilterAlarm() {
        Long actualOdometer = 410000L;
        Long eventOdometer = 405000L;
        when(eventRepository.findTopByTypeOrderByDateDateDesc(eq(Event.Type.GAS_FILTER_CHANGE))).thenReturn(Optional.of(getEvent(null, eventOdometer)));
        assertTrue(carService.alarmBasedOnKilometers(actualOdometer, Event.Type.GAS_FILTER_CHANGE, AppConfig.GAS_FILTER_ALARM_KILOMETERS));
    }

    @Test
    void shouldNotReturnGasFilterAlarm() {
        Long actualOdometer = 410000L;
        Long eventOdometer = 402000L;
        when(eventRepository.findTopByTypeOrderByDateDateDesc(eq(Event.Type.GAS_FILTER_CHANGE))).thenReturn(Optional.of(getEvent(null, eventOdometer)));
        assertFalse(carService.alarmBasedOnKilometers(actualOdometer, Event.Type.GAS_FILTER_CHANGE, AppConfig.GAS_FILTER_ALARM_KILOMETERS));
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
