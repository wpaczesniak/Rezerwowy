package com.example.rezerwowy.factories;

import com.example.rezerwowy.models.Stadium;

public class StadiumFactory {
    private static Long id = 1L;
    public static Stadium createStadiumCase1() {
        return Stadium.builder()
                .id(id++)
                .name("Stadion Narodowy")
                .city("Warszawa")
                .capacity(50000)
                .build();
    }
    public static Stadium createStadiumCase2() {
        return Stadium.builder()
                .id(id++)
                .name("Resovia")
                .city("Rzeszów")
                .capacity(5000)
                .build();
    }

    public static Stadium createStadiumCase3() {
        return Stadium.builder()
                .id(id++)
                .name("Wrocław")
                .city("Wrocław")
                .capacity(10000)
                .build();
    }

}
