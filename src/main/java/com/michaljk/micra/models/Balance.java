package com.michaljk.micra.models;

import lombok.Data;

import javax.persistence.CascadeType;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BAL_PER_ID", referencedColumnName = "PER_ID")
    private Period period;

    @Column(name = "BAL_KMS")
    private Long parkingKilometers = 0L;

    @Column(name = "BAL_FREE_KMS")
    private Long parkingFreeKilometers = 0L;

    @Column(name = "BAL_TKN_OVER_KMS")
    private Long parkingTakenOverKilometers = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BAL_USR_ID")
    private User user;

}
