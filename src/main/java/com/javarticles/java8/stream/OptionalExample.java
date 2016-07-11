package com.javarticles.java8.stream;

import java.util.Arrays;
import java.util.Optional;

public class OptionalExample {

    public static void main(String[] args) {
        Fruit[] fruits = {new Fruit("Mangoe", 80), new Fruit("Apple", 100), new Fruit("Orange", 80)};
        System.out.println("Find fruits  for a price less than 20, shouldn't be able to find any");
        Optional<Fruit> optional = Arrays.stream(fruits).filter(f -> f.getPrice().get() < 20).findAny();
        System.out.println("Got any? " + optional.isPresent());
        try {
            optional.get();
        } catch(java.util.NoSuchElementException e) {
            System.out.println("If there is no fruit found, optional.get() throws NoSuchElementException");
        }
        System.out.println("Find fruits  for a price less than 200");
        Arrays.stream(fruits).filter(f -> f.getPrice().get() < 200)
        .findFirst()
        .ifPresent(f -> f.eat());
    }
}
