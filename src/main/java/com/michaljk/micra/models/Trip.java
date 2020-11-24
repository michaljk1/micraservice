package com.michaljk.micra.models;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "trip")
public class Trip {

    public Trip(LocalDate tripDate, List<TripUser> tripUsers){
        this.tripDate = tripDate;
        this.tripUsers = tripUsers;
    }
    @Id
    private String id;
    private LocalDate tripDate;
    private List<TripUser> tripUsers;
}
