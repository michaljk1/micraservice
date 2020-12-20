package com.michaljk.micra.services.dto.settlement.models;

import com.michaljk.micra.utils.SettlementUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Settlement {

    public Settlement(List<SettlementUser> users, Long totalKilometers, boolean alreadySettled){
        this.users = users;
        this.totalKilometers = totalKilometers;
        this.gasCharge = SettlementUtils.convertKilometersToZl(totalKilometers);
        this.totalCharge = gasCharge + parkingCharge;
        this.alreadySettled = alreadySettled;
    }

    private Double litersOfGasPer100Km = SettlementUtils.LITERS_OF_GAS_PER_100KM;
    private Double priceOfOneLiterOfGas = SettlementUtils.PRICE_OF_ONE_LITER_OF_GAS;
    private Double parkingCharge = SettlementUtils.PARKING_CHARGE;
    private Long totalKilometers;
    private Double gasCharge;
    private Double totalCharge;
    private boolean alreadySettled;
    private boolean settlingRequest;
    private List<SettlementUser> users;

}
