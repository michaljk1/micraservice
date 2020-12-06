package com.michaljk.micra.services;

import com.michaljk.micra.models.Balance;
import com.michaljk.micra.models.Period;
import com.michaljk.micra.models.User;
import com.michaljk.micra.repositories.UserRepository;
import com.michaljk.micra.services.api.settlement.SettlementResponse;
import com.michaljk.micra.services.api.settlement.SettlementUser;
import com.michaljk.micra.services.utils.MathUtils;
import com.michaljk.micra.services.utils.SettlementUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SettlementService {

    private final UserRepository userRepository;

    public SettlementResponse getSettlement(Period period){
        List<SettlementUser> users = getUsersForSettlement(period);
        Long totalKilometers = users.stream()
                .map(SettlementUser::getKilometers)
                .reduce(0L, Long::sum);
        users.forEach(user -> calculateCharges(user, totalKilometers));
        return new SettlementResponse(users, totalKilometers);
    }

    private List<SettlementUser> getUsersForSettlement(Period period){
        List<User> users = userRepository.findAll();
        List<SettlementUser> settlementUsers = new ArrayList<>();
        for(User user : users){
            Optional<Balance> balance = user.getBalances().
                    stream().filter(b -> b.getPeriod().periodEqual(period)).findFirst();
            balance.ifPresent(value -> settlementUsers.add(new SettlementUser(user.getName(), value.getKilometers())));
        }
        return settlementUsers;
    }


    private void calculateCharges(SettlementUser user, Long totalKilometers){
        user.setParkingCharge(calculateParkingCharge(user.getKilometers(), totalKilometers));
        user.setGasCharge(SettlementUtils.convertKilometersToZl(user.getKilometers()));
        user.setTotalCharge(MathUtils.roundDouble(user.getGasCharge()+user.getParkingCharge()));
    }

    private Double calculateParkingCharge(Long userKilometers, Long totalKilometers) {
        double parkingCharge = userKilometers / (double) totalKilometers * SettlementUtils.PARKING_CHARGE;
        return MathUtils.roundDouble(parkingCharge);
    }



}
