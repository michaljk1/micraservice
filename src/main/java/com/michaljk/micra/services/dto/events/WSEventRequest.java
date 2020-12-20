package com.michaljk.micra.services.dto.events;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class WSEventRequest {

    private String name;
    private String description;
    private Double price;
    private Date date;
    private Long odometer;

}
