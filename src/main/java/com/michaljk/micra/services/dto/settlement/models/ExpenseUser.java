package com.michaljk.micra.services.dto.settlement.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ExpenseUser {

    private Integer id;
    private String paidShare;
    private String owedShare;

}
