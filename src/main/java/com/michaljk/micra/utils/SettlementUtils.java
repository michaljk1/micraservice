package com.michaljk.micra.utils;

public class SettlementUtils {

    public static final Double LITERS_OF_GAS_PER_100KM = 8.0;
    public static final Double PRICE_OF_ONE_LITER_OF_GAS = 2.0;
    public static final Double PARKING_CHARGE = 300.0;


    private static Double getPriceForOneKilometer(){
        return PRICE_OF_ONE_LITER_OF_GAS * LITERS_OF_GAS_PER_100KM / 100;
    }

    public static Double convertKilometersToZl(Long kilometers) {
        return MathUtils.roundDouble(kilometers * getPriceForOneKilometer());
    }

}
