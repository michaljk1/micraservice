package com.michaljk.micra.services;

import com.michaljk.micra.models.Balance;
import com.michaljk.micra.models.Period;
import com.michaljk.micra.models.User;
import com.michaljk.micra.repositories.BalanceRepository;
import com.michaljk.micra.repositories.PeriodRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BalanceService {

    private final PeriodRepository periodRepository;
    private final BalanceRepository balanceRepository;

    public Balance getUserBalance(User user, Period period) {
        return user.getBalances().stream()
                .filter(b -> b.getPeriod().equals(period))
                .findFirst().orElse(getCreatedBalance(user, period));
    }

    public void addKilometersToBalance(Balance balance, long userKilometers, boolean updateBalance) {
        if (updateBalance) {
            balance.setKilometers(balance.getKilometers() + userKilometers);
        } else {
            balance.setFreeKilometers(balance.getFreeKilometers() + userKilometers);
        }
    }

    public Period findPeriodOrCreateNew(String month, Integer year) {
        return periodRepository.findByMonthAndYear(month, year).orElse(getCreatedPeriod(month, year));
    }

    public Period getPeriod(String month, Integer year) {
        return periodRepository.getByMonthAndYear(month, year);
    }

    public void saveBalance(Balance balance) {
        balanceRepository.save(balance);
    }

    public void savePeriod(Period period) {
        periodRepository.save(period);
    }

    private Period getCreatedPeriod(String month, Integer year) {
        Period period = new Period();
        period.setMonth(month);
        period.setYear(year);
        return period;
    }

    private Balance getCreatedBalance(User user, Period period) {
        Balance balance = new Balance();
        balance.setUser(user);
        balance.setPeriod(period);
        return balance;
    }
}
