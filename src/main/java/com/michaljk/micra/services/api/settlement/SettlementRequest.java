package com.michaljk.micra.services.api.settlement;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SettlementRequest {
    private String month;
    private Integer year;
}
