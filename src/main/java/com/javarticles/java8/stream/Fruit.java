package com.javarticles.java8.stream;

import java.util.Optional;

public class Fruit {
    private Seed seed;
    private Optional<Integer> optionalPrice;
    
    public Fruit(Seed seed) {
        this.seed = seed;
        this.optionalPrice = Optional.empty();
    }
    
    public Fruit(String name, Integer price) {
        this.seed = new Seed(name);
        this.optionalPrice = Optional.of(price);
    }
    
    public Fruit(Seed seed, Integer price) {
        this.seed = seed;
        this.optionalPrice = Optional.of(price);
    }

    public Optional<Integer> getPrice() {
        return optionalPrice;
    }

    public void eat() {
        System.out.println("Found " + seed.getName() + ", let's eat...");
    }

    public Seed getSeed() {
        return seed;
    }

    public void setPrice(int price) {
        optionalPrice = Optional.of(price);        
    }       
}