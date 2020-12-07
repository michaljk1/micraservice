package com.michaljk.micra.services.api.settlement.ws;

import com.michaljk.micra.services.api.settlement.models.SettlementUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class WSSettlementUser {

    private String name;
    private Long kilometers;
    private Double parkingCharge;
    private Double gasCharge;
    private Double totalCharge;


    public WSSettlementUser(SettlementUser user){
        this.name = user.getName();
        this.kilometers = user.getKilometers();
        this.parkingCharge = user.getParkingCharge();
        this.gasCharge = user.getGasCharge();
        this.totalCharge = user.getTotalCharge();
    }
}
