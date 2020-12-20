package com.michaljk.micra.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evt_id")
    private Long id;

    @Column(name = "evt_name")
    private String name;

    @Column(name = "evt_desc")
    private String description;

    @Column(name = "evt_price")
    private Double price;

    @Column(name = "evt_date")
    private Date date;

    @Column(name = "evt_odometer")
    private Long odometer;

}
