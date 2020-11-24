package com.michaljk.micra.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@Builder
@Document(collection = "car")
public class Car {

    @Id
    private String id;
    private Long mileage;
    private String name;
    private List<CarEvent> events;

}

