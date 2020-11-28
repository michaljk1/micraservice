package com.michaljk.micra.services.api.car;

import com.michaljk.micra.models.Car;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WSCarResponse {

    private String name;
    private Long mileage;

    public WSCarResponse(Car car){
        this.name = car.getName();
        this.mileage = car.getMileage();
    }

}
