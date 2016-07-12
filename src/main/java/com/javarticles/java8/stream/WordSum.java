package com.javarticles.java8.stream;
/*
 * magine each letter and its position within the alphabet. Now assign each letter its corresponding value ie a=1, b=2,... z=26. When given a list of words, order the words by the sum of the values of the letters in their names.
Example: Shoe and Hat
Hat: 8+1+20 = 29
Shoe: 19+8+15+5 = 47
So the order would be Hat, Shoe.
For extra points, divide by the sum by the number of letters in that word and then rank them.
 */
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
public class WordSum {
    public static void main(String[] args) {
        SortedMap<Integer,String> sortedWords = new TreeMap<Integer,String>();
        Scanner keyboard = new Scanner(System.in);
        String word = keyboard.nextLine();
        while(!word.equals("x")){
           int sum = 0;
           for(int k=0;k<word.length();k++){
               char c = word.charAt(k);
               sum += c - 'a'+1;
           }
           sortedWords.put(sum, word);
           word = keyboard.nextLine();
        }
        for(Integer key:sortedWords.keySet()){
           System.out.println(sortedWords.get(key));
        }
    }
}