package com.michaljk.micra.services.api.settlement;

import com.michaljk.micra.services.utils.SettlementUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SettlementResponse {

    public SettlementResponse(List<SettlementUser> users, Long totalKilometers){
        this.users = users;
        this.totalKilometers = totalKilometers;
        this.gasCharge = SettlementUtils.convertKilometersToZl(totalKilometers);
        this.totalCharge = gasCharge + SettlementUtils.PARKING_CHARGE;

    }

    private Double gasCharge;
    private Double totalCharge;
    private Long totalKilometers;
    private List<SettlementUser> users;

}
