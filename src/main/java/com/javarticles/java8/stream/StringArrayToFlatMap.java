package com.javarticles.java8.stream;

import java.util.Arrays;
import java.util.stream.Stream;

public class StringArrayToFlatMap {
/*
 * You don't need a flattened Stream to use methods like filter(),sum()....etc

So just to show that you can apply Stream methods here are some examples.

 */
 public static void main(String[] args) {

  String[][] data = new String[][] 
    { 
     { "Java", "Jcr" },
     { "Hello", "Java" }, 
     { "Coder", "Magnet" } 
    };

  //Converting to Stream<String[]>
  Stream<String[]> dataStream = Arrays.stream(data);

  //Flattening the Stream to Stream<String>
  Stream<String> stringStream = dataStream.flatMap(x -> Arrays.stream(x));

  stringStream.forEach(System.out::println);

 }
}