package com.michaljk.micra.models;

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
@Table(name = "events")
public class Event {

    public interface Type {
        String REFUELING = "RF";
        String FILTERS_REPLACEMENT = "FR";
        String GAS_FILTER_CHANGE = "GF";
        String REPAIR = "RP";
        String PURCHASE = "PC";
        String CAR_WASH = "CW";
        String CAR_REVIEW = "CR";
        String INSURANCE = "OC";
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
