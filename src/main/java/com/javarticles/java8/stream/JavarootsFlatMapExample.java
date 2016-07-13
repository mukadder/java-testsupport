package com.javarticles.java8.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by abhishek.somani on 8/7/16.
 */
public class JavarootsFlatMapExample {

    public static void main(String[] args) {
        String[] arr = {"abhishekl","somani"};
        String[] arr2 = {"java","example","1.8","stream"};
        List<List<String>> list = new ArrayList<>();
        list.add(Arrays.asList(arr));
        list.add(Arrays.asList(arr2));
        final List<String> strings = list.stream().flatMap(l -> l.stream()).collect(Collectors.toList());
        System.out.println(strings);
    }

}