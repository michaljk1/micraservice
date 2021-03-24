package com.michaljk.micra.models.trip;

import com.michaljk.micra.models.Balance;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class TripSummary {

    private List<Balance> balances;
    private Long totalKilometers;

}
