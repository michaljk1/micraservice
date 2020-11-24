package com.michaljk.micra.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Balance {

    public Balance(String month, Long year) {
        this.month = month;
        this.year = year;
        this.kilometers = 0L;
    }

    String month;
    Long year;
    Long kilometers;

    public boolean periodEqual(Balance other) {
        return periodEqual(other.getMonth(), other.getYear());
    }

    public boolean periodEqual(String month, Long year){
        return  getMonth().equals(month) && getYear().equals(year);
    }
}
