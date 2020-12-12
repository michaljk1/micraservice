package com.michaljk.micra.services;

import com.michaljk.micra.models.Balance;
import com.michaljk.micra.models.Trip;
import com.michaljk.micra.models.TripUser;
import com.michaljk.micra.repositories.BalanceRepository;
import com.michaljk.micra.repositories.TripRepository;
import com.michaljk.micra.repositories.TripUserRepository;
import com.michaljk.micra.services.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final BalanceRepository balanceRepository;
    private final TripUserRepository tripUserRepository;
    private final CarService carService;
    private final BalanceService balanceService;

    public void addTrip(List<TripUser> tripUsers, boolean updateBalance) {
        Trip trip = new Trip();
        long tripKilometers = 0L;
        for (TripUser tripUser : tripUsers) {
            Balance balance = balanceService.getUserBalance(tripUser.getUser(), DateUtils.getCurrentMonth(), DateUtils.getCurrentYear());
            long userKilometers = tripUser.getKilometers();
            tripKilometers += userKilometers;
            balanceService.addKilometersToBalance(balance, userKilometers, updateBalance);
            balanceRepository.save(balance);
        }
        trip.setTotalKilometers(tripKilometers);
        trip.setUpdateBalance(updateBalance);
        carService.updateCarOdometer(tripKilometers);
        saveTrip(trip, tripUsers);
    }

    private void saveTrip(Trip trip, List<TripUser> tripUsers) {
        tripRepository.save(trip);
        tripUsers.forEach(tripUser -> tripUser.setTrip(trip));
        tripUserRepository.saveAll(tripUsers);
    }

}
