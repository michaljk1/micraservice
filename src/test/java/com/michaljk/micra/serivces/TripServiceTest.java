package com.michaljk.micra.serivces;

import com.michaljk.micra.models.Balance;
import com.michaljk.micra.models.Period;
import com.michaljk.micra.models.User;
import com.michaljk.micra.models.trip.TripSummary;
import com.michaljk.micra.models.trip.TripUser;
import com.michaljk.micra.repositories.TripRepository;
import com.michaljk.micra.services.BalanceService;
import com.michaljk.micra.services.CarService;
import com.michaljk.micra.services.TripService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TripServiceTest {

    private TripService tripService;
    private BalanceService balanceService;

    private final static Period PERIOD_STUB = getPeriodStub();

    @BeforeEach
    void init() {
        balanceService = mock(BalanceService.class);
        tripService = new TripService(mock(TripRepository.class), mock(CarService.class), balanceService);
        when(balanceService.findPeriodOrCreateNew(any(), any())).thenReturn(PERIOD_STUB);
    }

    @Test
    void singleUserUpdateBalanceTrip() {
        TripUser firstTripUser = getTripUser("michal", 10L);
        Balance firstBalance = getBalance(10L, 11L, 12L, firstTripUser.getUser());

        when(balanceService.getUserBalance(firstTripUser.getUser(), PERIOD_STUB)).thenReturn(firstBalance);
        TripSummary tripSummary = tripService.addTrip(Collections.singletonList(firstTripUser), true, false);

        assertEquals(10, tripSummary.getTotalKilometers());
        assertEquals(1, tripSummary.getBalances().size());
        Balance testBalance = getBalanceByUserName(tripSummary.getBalances(), "michal");
        assertEquals(20, testBalance.getParkingKilometers());
        assertEquals(11, testBalance.getParkingFreeKilometers());
        assertEquals(12, testBalance.getParkingTakenOverKilometers());
    }

    @Test
    void multipleUserUpdateBalanceTrip() {
        TripUser firstTripUser = getTripUser("michal", 15L);
        Balance firstBalance = getBalance(10L, 10L, 20L, firstTripUser.getUser());

        TripUser secondTripUser = getTripUser("jakub", 23L);
        Balance secondBalance = getBalance(20L, 25L, 0L, secondTripUser.getUser());

        TripUser thirdTripUser = getTripUser("daniel", 10L);
        Balance thirdBalance = getBalance(10L, 11L, 12L, thirdTripUser.getUser());

        when(balanceService.getUserBalance(firstTripUser.getUser(), PERIOD_STUB)).thenReturn(firstBalance);
        when(balanceService.getUserBalance(secondTripUser.getUser(), PERIOD_STUB)).thenReturn(secondBalance);
        when(balanceService.getUserBalance(thirdTripUser.getUser(), PERIOD_STUB)).thenReturn(thirdBalance);

        TripSummary tripSummary = tripService.addTrip(List.of(firstTripUser, secondTripUser, thirdTripUser), true, false);

        assertEquals(48, tripSummary.getTotalKilometers());
        assertEquals(3, tripSummary.getBalances().size());

        Balance michalBalance = getBalanceByUserName(tripSummary.getBalances(), "michal");
        assertEquals(25, michalBalance.getParkingKilometers());
        assertEquals(10, michalBalance.getParkingFreeKilometers());
        assertEquals(20, michalBalance.getParkingTakenOverKilometers());

        Balance jakubBalance = getBalanceByUserName(tripSummary.getBalances(), "jakub");
        assertEquals(43, jakubBalance.getParkingKilometers());
        assertEquals(25, jakubBalance.getParkingFreeKilometers());
        assertEquals(0, jakubBalance.getParkingTakenOverKilometers());

        Balance danielBalance = getBalanceByUserName(tripSummary.getBalances(), "daniel");
        assertEquals(20, danielBalance.getParkingKilometers());
        assertEquals(11, danielBalance.getParkingFreeKilometers());
        assertEquals(12, danielBalance.getParkingTakenOverKilometers());
    }


    private Balance getBalanceByUserName(List<Balance> balances, String name) {
        return balances.stream().filter(balance -> balance.getUser().getName().equals(name)).findFirst().orElseThrow();
    }

    private static Period getPeriodStub() {
        return Period.builder()
                .year(2021)
                .month("March")
                .build();
    }

    private static TripUser getTripUser(String name, Long kilometers) {
        return TripUser.builder()
                .user(getUser(name))
                .kilometers(kilometers)
                .build();
    }

    private static User getUser(String name) {
        User user = new User();
        user.setName(name);
        return user;
    }

    private static Balance getBalance(Long kilometers, Long freeKilometers, Long takenOverKilometers, User user) {
        return Balance.builder()
                .user(user)
                .parkingKilometers(kilometers)
                .parkingFreeKilometers(freeKilometers)
                .parkingTakenOverKilometers(takenOverKilometers).build();
    }
}
