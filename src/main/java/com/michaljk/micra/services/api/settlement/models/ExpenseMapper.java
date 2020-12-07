package com.michaljk.micra.services.api.settlement.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseMapper {

    public static Map<String, Object> mapExpenseToRequestMap(Expense expense){
        Map<String, Object> params = new HashMap<>();
        params.put("cost", expense.getCost());
        params.put("description", "Micra");
        params.put("currency_code", "PLN");
        params.put("group_id", expense.getGroupId());
        fillRequestWithUsers(params, expense);
        return params;
    }

    private static void fillRequestWithUsers(Map<String, Object> params, Expense expense) {
        List<SettlementUser> users = expense.getUsers();
        for (int i = 0; i < users.size(); i++){
            SettlementUser user = users.get(i);
            params.put("users__" + i + "__user_id", user.getSplitwiseId());
            params.put("users__" + i + "__paid_share", user.isPaying() ? expense.getCost() : "0.00");
            params.put("users__" + i + "__owed_share", user.getTotalCharge().toString());
        }
    }
}
