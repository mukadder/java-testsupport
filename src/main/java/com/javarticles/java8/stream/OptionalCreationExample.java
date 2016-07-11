package com.javarticles.java8.stream;

import java.util.Optional;

public class OptionalCreationExample {

    public static void main(String[] args) {
        Optional<Seed> emptySeed = Optional.empty();
        System.out.println("Is Seed empty? " + !emptySeed.isPresent());
        
        Optional<Seed> mangoSeed = Optional.of(new Seed("Mango Seed"));
        System.out.println("Does mango seed exists? " + mangoSeed.isPresent());
        
        Integer price = mangoSeed.flatMap(Seed::growIntoFruit).flatMap(Fruit::getPrice).orElse(100);        
        System.out.println("Price of mango is " + price);
        
        price = mangoSeed.flatMap(seed -> seed.growIntoFruit(200)).flatMap(Fruit::getPrice).orElse(100);        
        System.out.println("Price of mango is " + price);
    }
}
