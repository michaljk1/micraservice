package com.michaljk.micra.services.api.settlement.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Integer splitwiseId;
    private boolean paying;

    public SettlementUser(String name, Long kilometers, Integer splitwiseId, boolean paying){
        this.splitwiseId = splitwiseId;
        this.name = name;
        this.kilometers = kilometers;
        this.paying = paying;
    }
}
