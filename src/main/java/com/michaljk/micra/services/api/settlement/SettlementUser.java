package com.michaljk.micra.services.api.settlement;

import com.michaljk.micra.services.utils.MathUtils;
import com.michaljk.micra.services.utils.SettlementUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class SettlementUser {
    private String name;
    private Long kilometers;
    private Double parkingCharge;
    private Double gasCharge;
    private Double totalCharge;

    public SettlementUser(String name, Long kilometers){
        this.name = name;
        this.kilometers = kilometers;
    }
}
