package de.linsin.sample.reflectionjunit.domain;

import java.util.function.Function;
import java.util.stream.IntStream;

public class StringDemoComparison {
  public static void main(String[] args) {
    String input = 
      IntStream.iterate(1, x -> 1 + x)
               .mapToObj(x -> Character.toString((char)(x % 58 + 65)))
               .limit(100000)
               .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
               .toString();
  
    runDemo(StringDemo1::invertCapitals, input);
    runDemo(StringDemo2::invertCapitals, input);
  }
 
  public static void runDemo(Function func, String input) {
    long start = System.currentTimeMillis();
    String result = func.apply(input);
    long duration = System.currentTimeMillis() - start;
    System.out.println(result.length());
    System.out.println("Duration for: " + func.toString() + " is: " + duration);
  }
}
public class StringDemo2 {
	  public static String invertCapitals(String other) {
	    return other.chars()
	      .mapToObj(StringDemo1::flipCap)
	      .map(c -> Character.toString(c))
	      .collect(StringBuilder::new,StringBuilder::append,StringBuilder::append)
	      .toString();
	  }
	}

import java.util.stream.IntStream;

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