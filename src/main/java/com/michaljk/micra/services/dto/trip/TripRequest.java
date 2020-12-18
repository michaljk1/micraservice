package com.michaljk.micra.services.dto.trip;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TripRequest {
    private List<TripUserRequest> tripUsers;
    private boolean updateBalance;
}
