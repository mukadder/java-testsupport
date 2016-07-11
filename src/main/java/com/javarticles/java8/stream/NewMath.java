package com.javarticles.java8.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class NewMath {
	/*
	 * ouble [] doubles = ... ; // an array of doubles
List<Double> result = new ArrayList<>() ;
doubles
   .stream()
   .forEach(
      d -> NewMath
             .inv(d)
             .flatMap(NewMath::sqrt)
             .ifPresent(
                result::add
             )
   ) ;This code is interesting, it does what we need, but it still has a drawback. The call to ifPresent() takes a list that is built outside of our processing. If we want to go parallel with this processing (and that should be easy in Java 8), we will need a concurrent list, not an ArrayList. It would be better if the API could handle that for us.

In fact, what we need is a collector, and call a map() method instead of forEach().

For that, we are going to transform our optional in a stream. Our optional might be empty, we are going to change it into a stream, that will hold either nothing, either one value.
	Function<Double, Optional<Double>> f =
   d -> NewMath.inv(d)
           .flatMap(NewMath::sqrt) ;
           
           Function<Double, Stream<Double>> invSqrt =
   d -> NewMath.inv(d)
         .flatMap(NewMath::sqrt)
         .map(Stream::of)
         .orElseGet(Stream::empty) ;
	 */

	   public static Optional<Double> sqrt(Double d) {
	      return d > 0 ?
	         Optional.of(Math.sqrt(d)):
	         Optional.empty() ;
	   }

	   public static Optional<Double> inv(Double d) {
	      return d != 0 ?
	         Optional.of(1/d) :
	         Optional.empty() ;
	   }
	   public static void main (String [] args){
		  
		   List<Double> result = new ArrayList<>() ;
		   DoubleStream.of(1.2,2.3,4.5)
		      .forEach(
		         d -> NewMath
		                .sqrt(d)
		                .ifPresent(
		                   result::add
		                )
		      ) ;
		   
		   System.out.println(result);
		   Function<Double, Stream<Double>> invSqrt =
				   d -> NewMath.inv(d)
				         .flatMap(NewMath::sqrt)
				         .map(Stream::of)
				         .orElseGet(Stream::empty) ;
				   List<Double> doubles = Arrays.asList(-1d, 0d, 1d) ;

				   doubles.stream().parallel()
				         .flatMap(invSqrt)
				         .collect(Collectors.toList()) ;
	   }
	}