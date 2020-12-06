package com.michaljk.micra.services;

import com.michaljk.micra.models.Balance;
import com.michaljk.micra.models.Car;
import com.michaljk.micra.models.Period;
import com.michaljk.micra.models.User;
import com.michaljk.micra.repositories.CarRepository;
import com.michaljk.micra.repositories.PeriodRepository;
import com.michaljk.micra.services.api.car.WSCarRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BalanceService {

    private final PeriodRepository periodRepository;

    public Balance getUserBalance(User user, String month, Integer year){
        Period period = getPeriod(month, year);
        Balance balance = user.getBalances().stream()
                .filter(b -> b.getPeriod().periodEqual(period))
                .findFirst().orElse(null);
        if (balance == null) {
            balance = new Balance();
            balance.setUser(user);
            balance.setPeriod(period);
        }
        return balance;
    }

    public void addKilometersToBalance(Balance balance, long userKilometers, boolean updateBalance) {
        if (updateBalance) {
            balance.setKilometers(balance.getKilometers() + userKilometers);
        } else {
            balance.setFreeKilometers(balance.getFreeKilometers() + userKilometers);
        }
    }

    private Period getCreatedPeriod(String month, Integer year) {
        Period period = new Period();
        period.setMonth(month);
        period.setYear(year);
        return period;
    }

    public Period getPeriod(String month, Integer year) {
        return periodRepository.findByMonthAndYear(month, year).orElse(getCreatedPeriod(month, year));
    }
}
