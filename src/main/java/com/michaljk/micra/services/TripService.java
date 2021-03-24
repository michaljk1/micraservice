package com.michaljk.micra.services;

import com.michaljk.micra.models.Balance;
import com.michaljk.micra.models.Period;
import com.michaljk.micra.models.Trip;
import com.michaljk.micra.models.TripUser;
import com.michaljk.micra.repositories.TripRepository;
import com.michaljk.micra.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final CarService carService;
    private final BalanceService balanceService;

    public void addTrip(List<TripUser> tripUsers, boolean updateBalance) {
        Trip trip = new Trip();
        long tripKilometers = 0L;
        Period period = balanceService.findPeriodOrCreateNew(DateUtils.getCurrentMonth(), DateUtils.getCurrentYear());
        for (TripUser tripUser : tripUsers) {
            Balance balance = balanceService.getUserBalance(tripUser.getUser(), period);
            long userKilometers = tripUser.getKilometers();
            tripKilometers += userKilometers;
            balanceService.addKilometersToBalance(balance, userKilometers, updateBalance);
            balanceService.saveBalance(balance);
        }
        trip.setTotalKilometers(tripKilometers);
        trip.setUpdateBalance(updateBalance);
        carService.updateCarOdometer(tripKilometers);
        saveTrip(trip, tripUsers);
    }

    private void saveTrip(Trip trip, List<TripUser> tripUsers) {
        trip.setTripUsers(tripUsers);
        tripRepository.save(trip);
    }

}
