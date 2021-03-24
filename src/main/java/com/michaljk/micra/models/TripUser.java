package com.michaljk.micra.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TRIP_USERS")
public class TripUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TUS_ID")
    private Long id;

    @Column(name = "TUS_USR_ID")
    private Long userId;

    @Column(name = "TUS_KMS")
    private Long kilometers = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TUS_TRP_ID")
    private Trip trip;

    @Transient
    public User user;

}
