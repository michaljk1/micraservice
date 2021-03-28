package com.michaljk.micra.serivces;

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
        assertFalse(carService.insuranceAlarm(date));
    }

    @Test
    void shouldReturnInsuranceAlarm() {
        Date date = getJobDate(2021, Calendar.FEBRUARY, 25);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.MARCH, 10);
        when(eventRepository.findTopByTypeOrderByDateDateDesc(eq(Event.Type.INSURANCE))).thenReturn(Optional.of(getEvent(calendar.getTime())));
        assertTrue(carService.insuranceAlarm(date));
    }


    @Test
    void shouldNotReturnInsuranceAlarm() {
        Date date = getJobDate(2021, Calendar.JANUARY, 9);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.MARCH, 10);
        when(eventRepository.findTopByTypeOrderByDateDateDesc(eq(Event.Type.INSURANCE))).thenReturn(Optional.of(getEvent(calendar.getTime())));
        assertFalse(carService.insuranceAlarm(date));
    }



    private static Event getEvent(Date date) {
        return Event.builder().date(date).build();
    }

    private Date getJobDate(int year, int month, int day) {
        Calendar jobCalendar = Calendar.getInstance();
        jobCalendar.set(year, month, day);
        return jobCalendar.getTime();
    }

}
