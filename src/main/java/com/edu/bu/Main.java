package com.edu.bu;

import java.util.ArrayList;
import java.util.List;

public class Main {
	
	
	
	/*
	 * problem statement Scientist have discovered a new plant. 
	 * The fruit of the plant can feed 1 person for a whole week and best of all, the plant never dies.
	 *  Fruits needs 1 week to grow, so each weak you can harvest it fruits. 
	 *  Also the plant gives 1 fruit more than the week before and to get more plants you need to plant a fruit.
Now you need to calculate after how many weeks, you can support a group of x people, given y fruits to start with.
Input
15 1
Output
5
	 */
	// This is an example of high mutability 
	// now the static variables 
	public static int PEOPLE =15000;
	public static int FRUIT= 250;
	public static int PLANTS=0;
	public static int WEEKS=1;
	public static void main(String[] args){
		WEEKS = plantAnswer(PEOPLE);
		System.out.println("it took"+WEEKS+"to finish");
	}
	private static int plantAnswer(int people) {
		// create arraylist of plant objects 
		List<Plant>pArray= new ArrayList<Plant>();
		System.out.println(pArray.size());
		// see how many plants we have
		PLANTS= pArray.size();
		if(PLANTS>=people) return WEEKS;
		// now plant intial fruit 
		pArray= plantFruit(pArray);
		System.out.println("After planting initial fruit, we now have plants = "+ PLANTS);
		while(pArray.size()<people){
			// increment the week 
			WEEKS++;
			// age all plants by one 
			pArray= plantAge(pArray);
			// harvest all plants 
			pArray = harvestPlants(pArray);
			
			//replant all fruits
			pArray= plantFruit(pArray);
			
			
		}
		
		return WEEKS;
	}
	
 private static List<Plant> harvestPlants(List<Plant> pArray) {
	 int harvest =0;
	  for(Plant x:pArray){
		  harvest+=x.getAge();
		  
	  }
	  FRUIT+= harvest;
	  System.out.println("Size of pArray = "+pArray.size());
		
		return pArray;
	}
private static List<Plant> plantAge(List<Plant> pArray) {
		for(Plant x:pArray){
			x.setAge(x.getAge()+1);
			
		}
		return pArray;
	}
private static List<Plant> plantFruit(List<Plant> pArray) {
		for(int i=0;i<FRUIT;i++){
			Plant plant = new Plant(0);
			pArray.add(plant);
			
		}
		PLANTS=pArray.size();
	    FRUIT=0;
	    
		return pArray;
	}

public static class Plant{
private int age;
	public Plant() {
		this(0);
	}
	public Plant(int x) {
		this.setAge(x);
		
	}
	public void setAge(int x) {
		this.age= x;
	}
	public int getAge(){
		return age;
	}
	 
 }

}
