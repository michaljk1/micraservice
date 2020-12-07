package com.michaljk.micra.services;

import com.michaljk.micra.services.api.settlement.models.Expense;
import com.michaljk.micra.services.api.settlement.models.ExpenseMapper;
import com.michaljk.micra.services.api.settlement.models.Settlement;
import lombok.AllArgsConstructor;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class SplitwiseService {

    private final Environment environment;
    private static final String CREATE_EXPENSE_URL = "https://secure.splitwise.com/api/v3.0/create_expense";

    public void createExpense(Settlement settlement) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(Objects.requireNonNull(environment.getProperty("splitwise.bearer")));
        Expense expense = new Expense();
        expense.setCost(settlement.getTotalCharge().toString());
        expense.setGroupId(Integer.valueOf(Objects.requireNonNull(environment.getProperty("splitwise.groupId"))));
        expense.setUsers(settlement.getUsers());
        Map<String,Object> expenseRequest = ExpenseMapper.mapExpenseToRequestMap(expense);
        HttpEntity<Map<String,Object>> entity = new HttpEntity<>(expenseRequest, headers);
        ResponseEntity<String> response = restTemplate.exchange(CREATE_EXPENSE_URL, HttpMethod.POST, entity, String.class);
        spyResponse(response);
    }

    //splitwise api returns 200 with errors
    private void spyResponse(ResponseEntity<String> response) throws Exception {
        if (HttpStatus.OK.equals(response.getStatusCode()) && response.getBody()!=null && !response.getBody().contains("error")){
            return;
        } else {
            throw new Exception("Splitwise call failed");
        }
    }
}
