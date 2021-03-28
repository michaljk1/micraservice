package com.michaljk.micra.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@AllArgsConstructor
@Table(name = "events")
public class Event {

    public Event() {

    }

    public interface Type {
        String FILTERS_REPLACEMENT = "FILTERS_REPLACEMENT";
        String GAS_FILTER_CHANGE = "GAS_FILTER_CHANGE";
        String CAR_REVIEW = "CAR_REVIEW";
        String INSURANCE = "INSURANCE";
        String REPAIR = "REPAIR";
        String PURCHASE = "PURCHASE";
        String CAR_WASH = "CAR_WASH";
        String REFUELING = "REFUELING";

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evt_id")
    private Long id;

    @Column(name = "evt_name")
    private String name;

    @Column(name = "evt_type")
    private String type;

    @Column(name = "evt_desc")
    private String description;

    @Column(name = "evt_price")
    private Double price;

    @Column(name = "evt_date")
    private Date date;

    @Column(name = "evt_odometer")
    private Long odometer;

}
