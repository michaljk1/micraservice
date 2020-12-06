package com.michaljk.micra.models;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long id;

    @Column(name = "car_name")
    private String name;

    @Column(name = "car_odometer")
    private Long odometer;

    @Column(name = "car_fuel_usage")
    private Float fuelUsage;


//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "car")
//    private List<CarEvent> events = new ArrayList<>();

}

