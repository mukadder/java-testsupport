package com.javarticles.java8.stream;

import java.util.Optional;

public class Seed {
    private String name;

    public Seed(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public Optional<Fruit> growIntoFruit() {
        return Optional.of(new Fruit(this));
    }
    
    public Optional<Fruit> growIntoFruit(Integer price) {
        return Optional.of(new Fruit(this, price));
    }
}