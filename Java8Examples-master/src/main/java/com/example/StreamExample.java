package com.example;

import com.example.ironman.Iron;
import com.example.ironman.IronMan;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by MHP on 10/08/2015.
 */
public class StreamExample {

    private static final int EMPTY_ENERGY = 0;

    public static void main(String[] args) {

        List<IronMan> army = new ArrayList<>();

        army.forEach(ironMan -> ironMan.fireTomahawk());

        // method reference
        army.forEach(IronMan::fireTomahawk);

        // filter
        List<IronMan> humanInside = army.stream()
                .filter(IronMan::isHumanInside)
                .collect(Collectors.toList());

        // forEach
        humanInside.stream()
                .map(IronMan::getName)
                .forEach(System.out::println);

        // Stream.reduce() returns an Optional
        Integer totalEnergy = army.stream()
                .map(IronMan::getEnergy)
                .reduce((integer, integer2) -> integer + integer2)
                .orElse(EMPTY_ENERGY);

        // sorted
        List<IronMan> reverseOrder = army.stream()
                .sorted(Comparator.comparing(IronMan::getEnergy).reversed())
                .collect(Collectors.toList());


        // Reduction Operations average, sum, min, max, count
        army.stream()
                .mapToInt(IronMan::getEnergy)
                .average()
                .getAsDouble();

        // range rangeClosed, IntStream, LongStream, DoubleStream
        IntStream.range(0, 20)
                .filter(n -> n % 2 == 1);

        IronMan tonyStark = new IronMan(Iron.ADAMANTIUM);

        // Infinite streams
        java.util.stream.Stream<IronMan> newArmy = java.util.stream.Stream.iterate(tonyStark, tonyStark::addPower);
        // Limit the stream (it could be infinite)
        newArmy.limit(5)
                .mapToInt(IronMan::getEnergy)
                .forEach(System.out::println);

        IronMan najor = new IronMan();


        // of, peek, findFirst (findAny)
        java.util.stream.Stream.of(tonyStark, tonyStark, najor)
                .peek(StreamExample::increasePower)
                .findFirst();


        army.add(new IronMan());
        System.out.println(totalEnergy);
        System.out.println(reverseOrder);
    }

    private static void increasePower(IronMan ironMan) {

    }

}
