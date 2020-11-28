package com.michaljk.micra.services;

import com.michaljk.micra.models.Balance;
import com.michaljk.micra.models.Trip;
import com.michaljk.micra.models.TripUser;
import com.michaljk.micra.models.User;
import com.michaljk.micra.repositories.BalanceRepository;
import com.michaljk.micra.repositories.TripRepository;
import com.michaljk.micra.repositories.TripUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final BalanceRepository balanceRepository;
    private final TripUserRepository tripUserRepository;
    private final UserService userService;
    private final CarService carService;

    public void addTrip(List<TripUser> tripUsers, boolean updateBalance) {
        Date today = new Date();
        String month = String.valueOf(today.getMonth());
        Long year = (long) today.getYear();
        Trip trip = new Trip();
        trip.setTripUsers(tripUsers);
        trip.setTripDate(today);
        long tripKilometers = 0L;
        for(TripUser tripUser : tripUsers) {
            User user = tripUser.getUser();
            long userKilometers = tripUser.getKilometers();
            tripKilometers += userKilometers;
            Balance balance = userService.getOrCreateBalanceByMonthAndYear(user, month, year);
            balance.setUser(user);
            if (updateBalance) {
                balance.setKilometers(balance.getKilometers() + userKilometers);
            } else {
                balance.setFreeKilometers(balance.getFreeKilometers() + userKilometers);
            }
            userService.updateUserBalance(user, balance);
            balanceRepository.save(balance);
        }
        trip.setTotalKilometers(tripKilometers);
        trip.setUpdateBalance(updateBalance);
        carService.updateCarMileage(tripKilometers);
        saveTrip(trip, tripUsers);
    }

    private void saveTrip(Trip trip, List<TripUser> tripUsers){
        tripRepository.save(trip);
        tripUsers.forEach(tripUser -> tripUser.setTrip(trip));
        tripUserRepository.saveAll(tripUsers);
    }

}
