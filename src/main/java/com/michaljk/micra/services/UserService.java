package com.michaljk.micra.services;

import com.michaljk.micra.models.Balance;
import com.michaljk.micra.models.Trip;
import com.michaljk.micra.models.TripUser;
import com.michaljk.micra.models.User;
import com.michaljk.micra.repositories.UserRepository;
import com.michaljk.micra.services.api.trip.TripUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByName(String name){
        return userRepository.findByName(name).orElseThrow();
    }

    public void addUser(String userName){
        User user = new User();
        user.setName(userName);
        userRepository.save(user);
    }

    public Balance getOrCreateBalanceByMonthAndYear(User user, String month, Long year){
        Balance balance = user.getBalances().stream()
                .filter(b -> b.periodEqual(month, year))
                .findFirst().orElse(null);
        if (balance == null) {
            balance = new Balance();
            balance.setMonth(month);
            balance.setYear(year);
        }
        return balance;
    }

    public void updateUserBalance(User user, Balance balance) {
        boolean balanceByPeriodFound = false;
        for (Balance userBalance : user.getBalances()){
            if (userBalance.periodEqual(balance)){
                userBalance.setKilometers(balance.getKilometers());
                balanceByPeriodFound = true;
                break;
            }
        }
        if (Boolean.FALSE.equals(balanceByPeriodFound)){
           user.getBalances().add(balance);
        }
        userRepository.save(user);
    }

    public List<TripUser> mapToTripUsersByName(List<TripUserRequest> requestUsers) {
        List<TripUser> users = new ArrayList<>();
        for (TripUserRequest tripUserRequest : requestUsers){
            users.add(getMappedTripUser(tripUserRequest));
        }
        return users;
    }

    private TripUser getMappedTripUser(TripUserRequest tripUserRequest){
        User user = getUserByName(tripUserRequest.getName());
        TripUser tripUser = new TripUser();
        tripUser.setUser(user);
        tripUser.setUserId(user.getId());
        tripUser.setKilometers(tripUserRequest.getKilometers());
        return tripUser;
    }
}


