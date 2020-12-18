package com.michaljk.micra.services.dto.trip;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TripUserRequest {

    private String name;
    private Long kilometers;

}
