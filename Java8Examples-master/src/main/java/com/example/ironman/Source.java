package com.example.ironman;

import java.util.Optional;

/**
 * Created by MHP on 22/07/2015.
 */
public class Source {

    private String power;

    public Source(Double number) {
        number = Optional.ofNullable(number).orElse(0.0);
        this.power = "";
        for (int i = 0; i < (number % 10); i++)
            this.power += "+";
    }

    public String getPower() {
        return power;
    }
}
