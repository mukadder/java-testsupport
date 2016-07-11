package com.javarticles.java8.stream;

import java.util.NoSuchElementException;
import java.util.Optional;

public class OptionalDemo {
	 
	  public static void main(String[] args) {
	    //Creating an instance of Optional
	    //This value can also be returned from some method. 
	    Optional<String> name = Optional.of("Sanaulla");
	 
	    //This represents an instance of Optional containing no value
	    //i.e the value is 'null'
	    Optional empty = Optional.ofNullable(null);
	 
	    //isPresent method is used to check if there is any 
	    //value embedded within the Optional instance.
	    if (name.isPresent()) {
	      //Invoking get method returns the value present
	      //within the Optaional instance.
	      System.out.println(name.get());
	 
	    }
	 
	    try {
	      //Invoking get method on an empty Optaional instance 
	      //throws NoSuchElementException.
	      System.out.println(empty.get());
	    } catch (NoSuchElementException ex) {
	      System.out.println(ex.getMessage());
	    }
	 
	    //ifPresent method takes a lambda expression as a parameter.
	    //The lambda expression can then consume the value if it is present
	    //and perform some operation with it.
	    name.ifPresent((value) -> {
	      System.out.println("The length of the value is: " + value.length());
	    });
	 
	    //orElse method either returns the value present in the Optional instance
	    //or returns the message passed to the method in case the value is null.
	    System.out.println(empty.orElse("There is no value present!"));
	    System.out.println(name.orElse("There is some value!"));
	 
	    //orElseGet is similar to orElse with a difference that instead of passing 
	    //a default value, we pass in a lambda expression which generates the default 
	    //value for us.
	    System.out.println(empty.orElseGet(() -> "Default Value"));
	    System.out.println(name.orElseGet(() -> "Default Value"));
	 
	    try {
	      //orElseThrow similar to orElse method, instead of returning a default
	      //value, this method throws an exception which is genereated from 
	      //the lambda expression/method reference passed as a param to the method.
	      empty.orElseThrow(ValueAbsentException::new);
	    } catch (Throwable ex) {
	      System.out.println(ex.getMessage());
	    }
	     
	    //map method modifies the value present within the Optional instance
	    //by applying the lambda expression passed as a parameter. 
	    //The return value of the lambda expression is then wrapped into another
	    //Optional instance.
	    Optional<String> upperName = name.map((value) -> value.toUpperCase());
	    System.out.println(upperName.orElse("No value found"));
	     
	    //flatMap is exactly similar to map function, the differece being in the
	    //return type of the lambda expression passed to the method.
	    //In the map method, the return type of the lambda expression can be anything
	    //but the value is wrapped within an instance of Optional class before it 
	    //is returned from the map method, but in the flatMap method the return 
	    //type of lambda expression's is always an instance of Optional.
	    upperName = name.flatMap((value) -> Optional.of(value.toUpperCase()));
	    System.out.println(upperName.orElse("No value found"));
	     
	    //filter method is used to check if the given optional value satifies
	    //some condtion. If it satifies the condition then the same Optional instance
	    //is returned, otherwise an empty Optional instance is returned.
	    Optional<String> longName = name.filter((value) -> value.length() > 6);
	    System.out.println(longName.orElse("The name is less than 6 characters"));
	     
	    //Another example where the value fails the condition passed to the 
	    //filter method.
	    Optional<String> anotherName = Optional.of("Sana");
	    Optional<String> shortName = anotherName.filter((value) -> value.length() > 6);
	    System.out.println(shortName.orElse("The name is less than 6 characters"));
	 
	  }
	 
	}