package com.michaljk.micra.services.dto.car;

import com.michaljk.micra.models.Car;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WSCarResponse {

    private String name;
    private Long odometer;

    public WSCarResponse(Car car){
        this.name = car.getName();
        this.odometer = car.getOdometer();
    }

}
