package com.michaljk.micra.services;

import com.michaljk.micra.models.Balance;
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

    public SettlementResponse getSettlement(String month, Long year){
        List<SettlementUser> users = getUsersForSettlement(month, year);
        Long totalKilometers = users.stream()
                .map(SettlementUser::getKilometers)
                .reduce(0L, Long::sum);
        users.forEach(user -> calculateCharges(user, totalKilometers));
        return new SettlementResponse(users, totalKilometers);
    }

    private List<SettlementUser> getUsersForSettlement(String month, Long year){
        List<User> users = userRepository.findAll();
        List<SettlementUser> settlementUsers = new ArrayList<>();
        for(User user : users){
            Optional<Balance> balance = user.getBalances().
                    stream().filter(b -> b.periodEqual(month, year)).findFirst();
            balance.ifPresent(value -> settlementUsers.add(new SettlementUser(user.getName(), value.getKilometers())));
        }
        return settlementUsers;
    }


    private void calculateCharges(SettlementUser user, Long totalKilometers){
        user.setParkingCharge(calculateParkingCharge(user.getKilometers(), totalKilometers));
        user.setGasCharge(SettlementUtils.convertKilometersToZl(user.getKilometers()));
    }

    private Double calculateParkingCharge(Long userKilometers, Long totalKilometers) {
        double parkingCharge = userKilometers / (double) totalKilometers * SettlementUtils.PARKING_CHARGE;
        return MathUtils.roundDouble(parkingCharge);
    }

    public static void main(String[] args){

    }


}
