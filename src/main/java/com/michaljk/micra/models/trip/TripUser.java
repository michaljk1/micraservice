package com.michaljk.micra.models.trip;

import com.michaljk.micra.models.User;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
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
    @Builder.Default
    private Long kilometers = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TUS_TRP_ID", referencedColumnName = "TRP_ID")
    private Trip trip;

    @Transient
    public User user;

    @Transient
    boolean parkingUser;

}
