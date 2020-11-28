package com.michaljk.micra.models;

import lombok.Builder;
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
import javax.persistence.Transient;

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
    private String name;

    @Transient
    public User user;

}
