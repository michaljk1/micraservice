package com.michaljk.micra.models;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "TRIPS")
public class Trip {

    public Trip() {
        this.tripDate = new Date();
    }

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


    @PrePersist
    private void prePersist(){
        for(TripUser tripUser : tripUsers) {
            tripUser.setTrip(this);
        }
    }


}
