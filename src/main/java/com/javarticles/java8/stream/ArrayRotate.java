package com.javarticles.java8.stream;
/*
 * Given a NxN size 2D array of numbers. Develop a way to rotate the data as if you rotated the data by 90 degrees clockwise.
N = 3
Data:
1 2 3
4 5 6
7 8 9
Rotate 90 degrees:
7 4 1
8 5 2
9 6 3
Rotate it again 90 degrees:
9 8 7
6 5 4
3 2 1

 */
import java.util.Scanner;

public class ArrayRotate {

   static int n;

   static int[][] array;

   static int angle = 0;

   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      System.out.print("Array size? ");
      n = Integer.parseInt(in.nextLine());
      array = new int[n][n];
      for(int i = 0; i < n; i++) {
         Scanner line = new Scanner(in.nextLine());
         for(int j = 0; j < n; j++) {
            array[i][j] = line.nextInt();
         }
      }
      printArray();
      rotate();
      printArray();
      rotate();
      printArray();
      rotate();
      printArray();
   }

   static void printArray() {
      System.out.println("\n" + angle + "\u00B0:");
      for(int i = 0; i < n; i++) {
         for(int j = 0; j < n; j++) {
            System.out.print(array[i][j]);
            if(j < n - 1) {
               for(int k = 0; k < 3 - String.valueOf(array[i][j]).length(); k++) {
                  System.out.print(" ");
               }
            }
         }
         System.out.println();
      }
   }

   static void rotate() {
      int[][] newArray = new int[n][n];
      for(int i = 0; i < n;i++) {
         for(int j = 0; j < n; j++) {
            newArray[i][j] = array[n - j - 1][i];
         }
      }
      array = newArray;
      angle = (angle + 90) % 360;
   }
}