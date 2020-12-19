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

    @Column(name = "PER_SETTLED")
    private boolean settled;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if(Boolean.FALSE.equals(getClass().equals(object.getClass()))) {
            return false;
        }
        Period other = (Period) object;
        return this.getMonth().equals(other.getMonth()) && this.getYear().equals(other.getYear());
    }

}
