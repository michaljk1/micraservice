package com.michaljk.micra.models;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "TRIPS")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRP_ID")
    private Long id;

    @Column(name = "TRP_DATE")
    private Date tripDate;

    @Column(name = "TRP_KMS")
    private Long totalKilometers = 0L;

    @Column(name = "TRP_BALANCE")
    private boolean updateBalance;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trip")
    private List<TripUser> tripUsers = new ArrayList<>();

}
