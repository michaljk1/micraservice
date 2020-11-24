package com.michaljk.micra.services;

import com.michaljk.micra.models.Balance;
import com.michaljk.micra.models.Trip;
import com.michaljk.micra.models.TripUser;
import com.michaljk.micra.models.User;
import com.michaljk.micra.repositories.TripRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final UserService userService;
    private final CarService carService;

    public void addTrip(List<TripUser> tripUsers, boolean updateBalance) {
        LocalDate today = LocalDate.now();
        String month = String.valueOf(today.getMonth());
        Long year = (long) today.getYear();
        Trip trip = new Trip(today, tripUsers);
        long tripKilometers = 0L;
        for(TripUser tripUser : tripUsers) {
            User user = userService.getUserByName(tripUser.getName());
            long userKilometers = tripUser.getKilometers();
            tripKilometers += userKilometers;
            if (updateBalance) {
                Balance balance = userService.getOrCreateBalanceByMonthAndYear(user, month, year);
                balance.setKilometers(balance.getKilometers() + userKilometers);
                userService.updateUserBalance(user, balance);
            }
        }
        carService.updateCarMileage(tripKilometers);
        tripRepository.save(trip);
    }


}
