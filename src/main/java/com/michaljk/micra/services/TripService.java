package com.michaljk.micra.services;

import com.michaljk.micra.models.Balance;
import com.michaljk.micra.models.Period;
import com.michaljk.micra.models.trip.Trip;
import com.michaljk.micra.models.trip.TripSummary;
import com.michaljk.micra.models.trip.TripUser;
import com.michaljk.micra.repositories.TripRepository;
import com.michaljk.micra.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@Service
@AllArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final CarService carService;
    private final BalanceService balanceService;

    @Transactional(REQUIRES_NEW)
    public TripSummary addTrip(List<TripUser> tripUsers, boolean updateBalance, boolean parkingTakeOver) {
        Trip trip = new Trip();
        Period period = balanceService.findPeriodOrCreateNew(DateUtils.getCurrentMonth(), DateUtils.getCurrentYear());
        TripSummary tripSummary = parkingTakeOver ? processTripWithTakeOver(tripUsers, period, updateBalance)
                : processTrip(tripUsers, period, updateBalance);
        trip.setTotalKilometers(tripSummary.getTotalKilometers());
        trip.setUpdateBalance(updateBalance);
        balanceService.saveBalances(tripSummary.getBalances());
        carService.updateCarOdometer(tripSummary.getTotalKilometers());
        saveTrip(trip, tripUsers);
        return tripSummary;
    }


    private TripSummary processTrip(List<TripUser> tripUsers, Period period, boolean updateBalance) {
        long tripKilometers = 0L;
        List<Balance> balances = new ArrayList<>();
        for (TripUser tripUser : tripUsers) {
            Balance balance = balanceService.getUserBalance(tripUser.getUser(), period);
            long userKilometers = tripUser.getKilometers();
            tripKilometers += userKilometers;
            balance.addKilometers(userKilometers, updateBalance);
            balances.add(balance);
        }
        return TripSummary.builder()
                .balances(balances)
                .totalKilometers(tripKilometers)
                .build();
    }

    private TripSummary processTripWithTakeOver(List<TripUser> tripUsers, Period period, boolean updateBalance) {
        Long takenOverKilometers = 0L;
        List<Balance> balances = new ArrayList<>();
        for (TripUser tripUser : tripUsers) {
            long userKilometers = tripUser.getKilometers();
            if (!tripUser.isParkingUser()) {
                takenOverKilometers += userKilometers;
                Balance balance = balanceService.getUserBalance(tripUser.getUser(), period);
                balance.addKilometers(userKilometers, false);
                balances.add(balance);
            }
        }
        TripUser tripParkingUser = tripUsers.stream().filter(TripUser::isParkingUser).findFirst().orElseThrow();
        Balance parkingUserBalance = balanceService.getUserBalance(tripParkingUser.getUser(), period);
        Long parkingUserOwnKilometers = tripParkingUser.getKilometers();
        parkingUserBalance.addKilometers(parkingUserOwnKilometers, updateBalance);
        if (updateBalance) {
            parkingUserBalance.setParkingTakenOverKilometers(parkingUserBalance.getParkingTakenOverKilometers() + takenOverKilometers);
        }
        balances.add(parkingUserBalance);
        return TripSummary.builder()
                .balances(balances)
                .totalKilometers(takenOverKilometers + parkingUserOwnKilometers)
                .build();
    }

    private void saveTrip(Trip trip, List<TripUser> tripUsers) {
        trip.setTripUsers(tripUsers);
        tripRepository.save(trip);
    }

}
