package com.michaljk.micra.services.dto.trip;

import com.michaljk.micra.validators.CorrectParkingUser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@CorrectParkingUser
public class TripRequest {
    private List<TripUserRequest> tripUsers;
    private boolean updateBalance;
    private String parkingUser;
}
