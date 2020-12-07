package com.michaljk.micra.services.api.settlement.models;

import com.michaljk.micra.services.api.settlement.ws.WSSettlementUser;
import com.michaljk.micra.services.utils.SettlementUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class Settlement {

    public Settlement(List<SettlementUser> users, Long totalKilometers){
        this.users = users;
        this.totalKilometers = totalKilometers;
        this.gasCharge = SettlementUtils.convertKilometersToZl(totalKilometers);
        this.totalCharge = gasCharge + parkingCharge;
    }

    private Double litersOfGasPer100Km = SettlementUtils.LITERS_OF_GAS_PER_100KM;
    private Double priceOfOneLiterOfGas = SettlementUtils.PRICE_OF_ONE_LITER_OF_GAS;
    private Double parkingCharge = SettlementUtils.PARKING_CHARGE;
    private Long totalKilometers;
    private Double gasCharge;
    private Double totalCharge;
    private List<SettlementUser> users;

}
