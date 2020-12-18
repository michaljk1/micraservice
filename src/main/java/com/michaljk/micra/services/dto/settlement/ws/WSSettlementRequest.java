package com.michaljk.micra.services.dto.settlement.ws;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WSSettlementRequest {
    private String month;
    private Integer year;
}
