package com.javarticles.java8.stream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class RotateArray {
    //The Challenge
    public static int[][] rotate(int[][] arr){
        int[][] newArr = new int[arr.length][arr[0].length];        
        for(int col = 0; col < arr[0].length;col++){
            for(int row = 0; row < arr.length;row++){
                newArr[col][arr.length-row-1] =  arr[row][col];
            }
        }
        return newArr;
    }

    //Boring Reading from File
    public static int[][] readMartrix(File f) throws IOException{
        BufferedReader readFile = new BufferedReader(new FileReader(f));
        int dims = Integer.parseInt(readFile.readLine());
        int[][] matrix = new int[dims][dims];
        for(int r = 0; r < dims;r++){
            String[] split = readFile.readLine().split(" ");
            for(int c = 0; c < dims; c++){
                matrix[r][c] = Integer.parseInt(split[c]);
            }
        }
        readFile.close();
        return matrix;
    }

    //read...run...display
    public static void main(String[] args) throws IOException{
        for(int[] nums: rotate(readMartrix(new File("in.txt")))){
            for(int n: nums){
                System.out.printf("%3d",n);
            }
            System.out.println();
        }
    }
}