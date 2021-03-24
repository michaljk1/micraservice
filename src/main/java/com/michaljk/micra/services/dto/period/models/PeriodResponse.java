package com.michaljk.micra.services.dto.period.models;

import com.michaljk.micra.models.Balance;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PeriodResponse {

    private String month;
    private Integer year;
    private List<Balance> balances;
}
