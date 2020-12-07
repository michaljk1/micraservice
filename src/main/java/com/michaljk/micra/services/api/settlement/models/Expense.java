package com.michaljk.micra.services.api.settlement.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Expense {

    private String cost;
    private Integer groupId;
    private List<SettlementUser> users;

}
