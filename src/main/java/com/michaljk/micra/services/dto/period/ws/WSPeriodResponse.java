package com.michaljk.micra.services.dto.period.ws;

import com.michaljk.micra.models.Period;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Setter
@Getter
public class WSPeriodResponse {

    private String month;
    private Integer year;
    private List<WSBalance> balances;


    public WSPeriodResponse(Period period) {
        this.balances = period.getBalances().stream().map(WSBalance::new).collect(Collectors.toList());
        this.month = period.getMonth();
        this.year = period.getYear();
    }

    public static void main(String[] args){
        String name = "xD";
        System.out.println(name.toLowerCase());
        add(name);
        System.out.println(name);


//        int a = 4;
//        int b = 5;
//        System.out.println(add(a,b));
    }


    public static void add(String x) {
        x = x +"XDDDDD";
        System.out.println(x);
    }
}
