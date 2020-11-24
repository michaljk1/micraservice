package com.michaljk.micra.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TripUser {
    private String name;
    private Long kilometers;
}
