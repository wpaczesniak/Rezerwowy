package com.example.rezerwowy.factories;

import com.example.rezerwowy.models.Person;

public class PersonFactory {
    public static Person createPersonCase1() {
        return Person.builder()
                .name("Cristiano")
                .surname("Ronaldo")
                .build();
    }

    public static Person createPersonCase2() {
        return Person.builder()
                .name("Erling")
                .surname("Haaland")
                .build();
    }

    public static Person createPersonCase3() {
        return Person.builder()
                .name("Quang Hai")
                .surname("Nguyen")
                .build();
    }

    public static Person createPersonCase4() {
        return Person.builder()
                .name("Kevin")
                .surname("Bruyne")
                .build();
    }
}
