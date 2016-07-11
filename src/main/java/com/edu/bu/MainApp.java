package com.edu.bu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    public static void main(String... args){
        int week = 1;

        List<fruit> f = new ArrayList<>();
        Scanner s = new Scanner(System.in);

        //get the inputs
        System.out.println("Enter the number of people to support:");
        int numberOfPeople = s.nextInt();
        System.out.println("Enter the number of fruits to start with:");
        int startingFruits = s.nextInt();

        //initialise
        for (int i = 0; i<startingFruits; i++){
            f.add(new fruit());
        }

        //main loop
        while(getFruitCount(f) < numberOfPeople){

            //increment the count and add the new fruits
            int size = f.size();
            for (int i = 0; i < size; i++){
                f.get(i).incrementCurrentFruitCount();
                for (int j = 0; j < f.get(i).getCurrentFruitCount(); j++) {
                    f.add(new fruit());
                }
            }

            //go to the next week
            week += 1;

        }

        System.out.println("It takes " + Integer.toString(week) + " weeks to support these people!");
    }

    public static int getFruitCount(List<fruit> f){
        int count = 0;

        for (fruit aF : f) {
            count += aF.getCurrentFruitCount();
        }

        return count;
    }

}

class fruit {
    int currentFruitCount = 0;

    public int getCurrentFruitCount(){
        return currentFruitCount;
    }

    public void incrementCurrentFruitCount(){
        currentFruitCount += 1;
    }
}