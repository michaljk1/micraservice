package com.michaljk.micra.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
@Table(name = "BALANCES")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BAL_ID")
    private Long id;

    @Column(name = "BAL_MONTH")
    private String month;

    @Column(name = "BAL_YEAR")
    private Long year;

    @Column(name = "BAL_KMS")
    private Long kilometers = 0L;

    @Column(name = "BAL_FREE_KMS")
    private Long freeKilometers = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BAL_USR_ID")
    private User user;

    public boolean periodEqual(Balance other) {
        return periodEqual(other.getMonth(), other.getYear());
    }

    public boolean periodEqual(String month, Long year){
        return  getMonth().equals(month) && getYear().equals(year);
    }
}
