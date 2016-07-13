package com.javarticles.java8.stream;

import java.util.stream.IntStream;
/**
 * The first step is to get a stream of the numbers from 1 to 100. 
 * This is done by calling IntStream.range(1, 101). The second argument is exclusive, 
 * so to get 1 to 100 we need to specify 101 as the end value.

Once we have a stream, we can do a map operation.
 Since this is an IntStream, but the desired output is Strings,
  we can use IntStream‘s mapToObj method to do the conversion. If we used the standard map method, we’d get a compile error because of an incompatible return type.


 * @author mukadder
 *
 */
public class FizzBuzz {
    public static void main(String...args) {
        IntStream.range(1, 101)
            .mapToObj(n -> {
                if (n % 15 == 0) return "FizzBuzz";
                else if (n % 3 == 0) return "Fizz";
                else if (n % 5 == 0) return "Buzz";
                else return n;
            }).forEach(System.out::println);
    }
}