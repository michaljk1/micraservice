package com.michaljk.micra.services.dto.settlement.ws;

import com.michaljk.micra.services.dto.settlement.models.Settlement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class WSSettlementResponse {

    public WSSettlementResponse(Settlement settlement){
        this.users = settlement.getUsers().stream().map(WSSettlementUser::new).collect(Collectors.toList());
        this.totalKilometers = settlement.getTotalKilometers();
        this.gasCharge = settlement.getGasCharge();
        this.totalCharge = settlement.getTotalCharge();
        this.litersOfGasPer100Km = settlement.getLitersOfGasPer100Km();
        this.priceOfOneLiterOfGas = settlement.getPriceOfOneLiterOfGas();
        this.parkingCharge = settlement.getParkingCharge();
    }

    private Double litersOfGasPer100Km;
    private Double priceOfOneLiterOfGas;
    private Double parkingCharge;
    private Long totalKilometers;
    private Double gasCharge;
    private Double totalCharge;
    private List<WSSettlementUser> users;

}
