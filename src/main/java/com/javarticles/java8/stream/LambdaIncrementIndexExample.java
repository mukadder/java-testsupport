package com.javarticles.java8.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LambdaIncrementIndexExample {

    public static void main(String[] args) {
        /* AtomicInteger in `stream()` */
        new LambdaIncrementIndexExample().UsingStream();

        /* AtomicInteger in `parallelStream()` */
        new LambdaIncrementIndexExample().UsingParallelStream();
    }

    /**
     * Example of using AtomicInteger in `stream()`
     */
    public void UsingStream() {
        /* Create object of AtomicInteger with initial value `0` */
        AtomicInteger atomicInteger = new AtomicInteger(0);

        /* Create list of names. */
        List<String> listNames = new ArrayList<String>(Arrays.asList("Vicky Thakor", "Chirag Thakor", "Dave Hill", "Finn Jones", "Heer Thakor"));

        listNames.stream()
                 .filter(name -> name.endsWith("Thakor"))
                 .forEach(name -> {
                    /* Get the previous value of count and increment it by `1` */
                    atomicInteger.getAndIncrement();

                    /* Print the name */
                    System.out.println(name);
                 });
  
        /* Get value of `atomicInteger` */
        System.out.println("Total match found using `stream()`: " + atomicInteger.get());
        System.out.println("+++++++++++++++++++++++++++");
    }

    /**
     * Example of using AtomicInteger in `parallelStream()`
     */
    public void UsingParallelStream() {
        /* Create object of AtomicInteger with initial value `0` */
        AtomicInteger atomicInteger = new AtomicInteger(0);

        /* Create list of names. */
        List<String> listNames = new ArrayList<String>(Arrays.asList("Vicky Thakor", "Chirag Thakor", "Dave Hill", "Finn Jones", "Heer Thakor"));

         listNames.parallelStream()
                  .filter(name -> name.endsWith("Thakor"))
                  .forEach(name -> {
                    /* Get the previous value of count and increment it by `1` */
                    atomicInteger.getAndIncrement();

                    /* Print the name */
                    System.out.println(name);
                  });
  
        /* Get value of `atomicInteger` */
        System.out.println("Total match found using `parallelStream()`: " + atomicInteger.get());
    }
}