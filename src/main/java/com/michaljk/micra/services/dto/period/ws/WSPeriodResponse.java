package com.michaljk.micra.services.dto.period.ws;

import com.michaljk.micra.models.Period;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class WSPeriodResponse {

    private String month;
    private Integer year;
    private List<WSBalance> balances;


    public WSPeriodResponse(Period period) {
        this.balances = period.getBalances().stream().map(WSBalance::new).collect(Collectors.toList());
        this.month = period.getMonth();
        this.year = period.getYear();
    }
}
