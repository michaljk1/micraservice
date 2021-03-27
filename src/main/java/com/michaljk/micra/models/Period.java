package com.michaljk.micra.models;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "period")
    @Builder.Default
    private List<Balance> balances = new ArrayList<>();

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
