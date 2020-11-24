package com.michaljk.micra.services.api.car;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewCarRequest {

    String name;
    Long mileage;

}
