package com.michaljk.micra.services.dto.period.ws;

import com.michaljk.micra.models.Balance;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WSBalance {

    private String name;
    private Long kilometers;
    private Long freeKilometers;

    public WSBalance(Balance balance) {
        name = balance.getUser().getName();
        kilometers = balance.getParkingKilometers();
        freeKilometers = balance.getParkingFreeKilometers();
    }
}
