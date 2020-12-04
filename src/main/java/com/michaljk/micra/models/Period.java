package com.michaljk.micra.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "PERIODS")
public class Period {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PER_ID")
    private Long id;

    @Column(name = "PER_MONTH")
    private String month;

    @Column(name = "PER_YEAR")
    private Integer year;


    public boolean periodEqual(Period other) {
        return periodEqual(other.getMonth(), other.getYear());
    }

    public boolean periodEqual(String month, Integer year){
        return  getMonth().equals(month) && getYear().equals(year);
    }
}
