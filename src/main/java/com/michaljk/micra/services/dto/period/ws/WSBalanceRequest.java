package com.michaljk.micra.services.dto.period.ws;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WSBalanceRequest {

    private String month;
    private Integer year;
}
