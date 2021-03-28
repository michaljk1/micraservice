package com.michaljk.micra.services.dto.events;

import com.michaljk.micra.models.Event;
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
    private String type;

    public Event toEvent(){
        return Event.builder()
                .name(name)
                .date(date == null ? new Date() : date)
                .description(description)
                .price(price)
                .odometer(odometer)
                .type(type)
                .build();
    }

}
