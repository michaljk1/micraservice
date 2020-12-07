package com.michaljk.micra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicraApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicraApplication.class, args);
    }

}
//package com.michaljk.micra.services.api.settlement.splitwise.requests;
//
//        import lombok.AllArgsConstructor;
//        import lombok.Getter;
//        import lombok.NoArgsConstructor;
//        import lombok.Setter;
//        import org.springframework.http.HttpEntity;
//        import org.springframework.http.HttpHeaders;
//        import org.springframework.http.HttpMethod;
//        import org.springframework.http.MediaType;
//        import org.springframework.http.ResponseEntity;
//        import org.springframework.web.client.RestTemplate;
//
//        import java.util.Arrays;
//        import java.util.Collections;
//        import java.util.HashMap;
//        import java.util.List;
//        import java.util.Map;
//
//@Setter
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//public class Expense {
//
//    private String cost = "1.00";
//    private String description = "Test na produkcji";
//    private Integer payment = 0;
//    private String currency_code = "PLN";
//    private Integer group_id = 19312184;
//    private List<ExpenseUser> users;
//    private Integer users__0__user_id;
//    private String users__0__paid_share;
//    private String users__0__owed_share;
//
//    public static void main(String[] args) {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        headers.setBearerAuth("FlqztWsCOba1Nq4rlCYbyZtkhoILxJLZBnXN4G6F");
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("cost", "1.00");
//        params.put("description", "test");
//        params.put("payment", 0);
//        params.put("currency_code", "PLN");
//        params.put("group_id", 19312184);
////        params.put("split_equally", Boolean.FALSE);
//        params.put("users__0__user_id", 28079081); //ja
//        params.put("users__0__paid_share", "1.00");
//        params.put("users__0__owed_share", "0.34");
//        params.put("users__1__user_id", 26104222); //daniel
//        params.put("users__1__paid_share", "0.00");
//        params.put("users__1__owed_share", "0.33");
//        params.put("users__2__user_id", 26772301); //jakub
//        params.put("users__2__paid_share", "0.00");
//        params.put("users__2__owed_share", "0.33");
//        String url = "https://secure.splitwise.com/api/v3.0/create_expense";
//        com.michaljk.micra.services.api.settlement.models.Expense expense = new com.michaljk.micra.services.api.settlement.models.Expense();
//        expense.setUsers__0__owed_share("1.00");
//        expense.setUsers__0__paid_share("1.00");
//        expense.setUsers__0__user_id(28079081);
//        List<ExpenseUser> users = Arrays.asList(
//                new ExpenseUser(28079081, "1.00", "1.00")
//        );
//        expense.setUsers(users);
//        HttpEntity<com.michaljk.micra.services.api.settlement.models.Expense> entity = new HttpEntity<>(expense, headers);
//        ResponseEntity<String> a = restTemplate.exchange(url, HttpMethod.POST, entity, String.class, expense);
//        System.out.println(a.getBody());
//    }
//
//}
