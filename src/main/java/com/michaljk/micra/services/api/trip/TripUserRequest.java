package com.michaljk.micra.services.api.trip;

import com.michaljk.micra.models.TripUser;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TripUserRequest {

    private String name;
    private Long kilometers;


    public TripUser toTripUser(){
        return TripUser.builder()
                .kilometers(kilometers)
                .name(name)
                .build();
    }
}
