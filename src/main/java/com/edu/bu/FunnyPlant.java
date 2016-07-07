package com.edu.bu;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// this is brute force java 8 in action 
public class FunnyPlant {

	public static void main(String[] args) {
		System.out.println("Enter the people feeding");
		String[] infos= new Scanner(System.in).nextLine().split("");
		final Integer people = Integer.valueOf(infos[0]);
		Integer producton= Integer.valueOf(infos[1]);
		// now the magic begins 
		/*
		 * create a stream with the range 1 to starting number of fruits. 
		 * Then it map every value in the range to the creation of a Plant object 
		 * and I collect them in a List. That's my starting List of plants.
		 */
		List<Plant>plants = IntStream.rangeClosed(1,producton).mapToObj(value->new Plant()).collect(Collectors.toList());
		// now this is the meat of the program 
		/*While the production does not match the number of people to feed, 
		 * I apply the addWeek method to each object in plants.
          Plant::addWeek is a method reference.
           You can also write p -> p.addWeek(). Result is the same.
		 * 
		 */
		while(producton<people){
			plants.forEach(Plant::addWeek);
			/*I map every plant object to an integer.
That integer comes from the getFruits() method.
And it makes the sum of all those Integers. That's the number of fruits I collected this week
			 * 
			 */
			// this is beautiful in  java8 .This is the best thing could have happened to java since 1993.here
			// we are summing up all the fruits from the plant objects 
			producton= plants.stream().mapToInt(Plant::getFruits).sum();
			// now watch the amazing one liner That creates a new plant for each fruit produced this week
			// in java8 you simply tell the function to do the job and you dont control internally how to evolve the logic
			//but I take the getWeek() values and take the max of them
			IntStream.rangeClosed(1, producton).forEach(v->plants.add(new Plant()));
			System.out.println(plants.stream().mapToInt(Plant::getWeek).max().getAsInt());
			
		}
	}
	private static class Plant{
		private Integer week ;
		Plant(){
			week=1;
		}
		public void addWeek(){
			week++;
			
		}
		public Integer getWeek(){
			return week;
			
		}
		// this is the core logic we have plants who cannot bear fruit until it is one week old 
		public Integer getFruits(){
			return week-1;
			
		}
		
	}
}
