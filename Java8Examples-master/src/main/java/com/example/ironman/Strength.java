package com.example.ironman;

import java.util.Optional;

/**
 * Created by MHP on 24/07/2015.
 */
public class Strength {


    private String level;

    public Strength(Double number) {
        Optional.ofNullable(number).ifPresent(n -> {
            level = "";
            for (int i = 0; i < (n % 10); i++)
                this.level += "+";
        });
    }

    public Optional<String> getLevel() {
        return Optional.of(level);
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
