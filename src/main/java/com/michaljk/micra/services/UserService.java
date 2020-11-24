package com.michaljk.micra.services;

import com.michaljk.micra.models.Balance;
import com.michaljk.micra.models.User;
import com.michaljk.micra.repositories.UserRepository;
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
        User user = User.builder()
                .name(userName)
                .balances(List.of())
                .build();
        userRepository.save(user);
    }

    public Balance getOrCreateBalanceByMonthAndYear(User user, String month, Long year){
        return user.getBalances().stream()
                .filter(b -> b.periodEqual(month, year))
                .findFirst().orElse(new Balance(month, year));

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
}


