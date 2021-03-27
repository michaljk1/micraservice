package com.michaljk.micra.services;

import com.michaljk.micra.models.Balance;
import com.michaljk.micra.models.Period;
import com.michaljk.micra.models.User;
import com.michaljk.micra.services.dto.settlement.models.Settlement;
import com.michaljk.micra.services.dto.settlement.models.SettlementUser;
import com.michaljk.micra.services.dto.settlement.ws.WSSettlementResponse;
import com.michaljk.micra.utils.MathUtils;
import com.michaljk.micra.utils.SettlementUtils;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@Service
@AllArgsConstructor
public class SettlementService {

    private final UserService userService;
    private final SplitwiseService splitwiseService;
    private final Environment environment;
    private final BalanceService balanceService;

    @Transactional(REQUIRES_NEW)
    public WSSettlementResponse createSettlement(Period period, boolean callSplitwise) throws Exception {
        List<SettlementUser> users = getUsersForSettlement(period);
        Long totalKilometers = users.stream()
                .map(SettlementUser::getKilometers)
                .reduce(0L, Long::sum);
        users.forEach(user -> calculateCharges(user, totalKilometers));
        boolean alreadySettled = period.isSettled();
        Settlement settlement = new Settlement(users, totalKilometers, alreadySettled);
        if(callSplitwise && Boolean.FALSE.equals(alreadySettled)) {
            splitwiseService.createExpense(settlement);
            period.setSettled(true);
            settlement.setSettlingRequest(true);
            balanceService.savePeriod(period);
        }
        return new WSSettlementResponse(settlement);
    }

    private List<SettlementUser> getUsersForSettlement(Period period) {
        List<User> users = userService.getAllUsers();
        List<SettlementUser> settlementUsers = new ArrayList<>();
        for (User user : users) {
            Optional<Balance> balance = user.getBalances().stream().filter(b -> b.getPeriod().equals(period)).findFirst();
            balance.ifPresent(value -> settlementUsers.add(getSettlementUser(user,
                    value.getParkingKilometers() + value.getParkingTakenOverKilometers())));
        }
        return settlementUsers;
    }

    private SettlementUser getSettlementUser(User user, Long kilometers) {
        boolean paying = user.getName().equals(environment.getProperty("splitwise.payingUser"));
        return new SettlementUser(user.getName(), kilometers, user.getSplitwiseId(), paying);
    }

    private void calculateCharges(SettlementUser user, Long totalKilometers) {
        user.setParkingCharge(calculateParkingCharge(user.getKilometers(), totalKilometers));
        user.setGasCharge(SettlementUtils.convertKilometersToZl(user.getKilometers()));
        user.setTotalCharge(MathUtils.roundDouble(user.getGasCharge() + user.getParkingCharge()));
    }

    private Double calculateParkingCharge(Long userKilometers, Long totalKilometers) {
        double parkingCharge = userKilometers / (double) totalKilometers * SettlementUtils.PARKING_CHARGE;
        return MathUtils.roundDouble(parkingCharge);
    }


}
