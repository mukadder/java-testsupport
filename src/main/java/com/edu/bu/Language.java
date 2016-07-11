package com.edu.bu;


	/*
	 * You were hired to create words for a new language. However, your boss wants these words to follow a strict pattern of consonants and vowels. You are bad at creating words by yourself, so you decide it would be best to randomly generate them.
Your task is to create a program that generates a random word given a pattern of consonants (c) and vowels (v).
Input Description

Any string of the letters c and v, uppercase or lowercase.
Output Description

A random lowercase string of letters in which consonants (bcdfghjklmnpqrstvwxyz) occupy the given 'c' indices and vowels (aeiou) occupy the given 'v' indices.
Sample Inputs

cvcvcc

CcvV

cvcvcvcvcvcvcvcvcvcv
Sample Outputs

litunn

ytie

poxuyusovevivikutire
	 */
import java.util.*;

public class Language {

public static void main(String[] args) {
    System.out.println("Please enter in 'c' for a consant and 'v' for a vowel");
    Scanner input = new Scanner(System.in);
    String str = input.nextLine();
    char[] array = str.toCharArray();
    for(int i = 0; i < array.length; i++){
        if(array[i]=='c'){
            System.out.print(getConsonant());
        }
        if(array[i]=='C'){
            System.out.print(getUpConsonant());
        }
        if(array[i]=='v'){

            System.out.print(getVowel());
        }
        if(array[i]=='V'){
            System.out.print(getUpVowel());
        }
}
}
public static char getVowel(){
    String vow = "aeiou";
    ArrayList<Character> vowels = new ArrayList<Character>();
    for(char c : vow.toCharArray()){
        vowels.add(c);
    }
    Collections.shuffle(vowels);
    return vowels.get(0);
}
public static char getUpVowel(){
    String vow = "AEIOU";
    ArrayList<Character> vowels = new ArrayList<Character>();
    for(char c : vow.toCharArray()){
        vowels.add(c);
    }
    Collections.shuffle(vowels);
    return vowels.get(0);
}
public static char getConsonant(){
    String con = "bcdfghjklmnpqrstvwxyz";
    ArrayList<Character> consonants = new ArrayList<Character>();
    for(char c : con.toCharArray()){
        consonants.add(c);
    }
    Collections.shuffle(consonants);
    return consonants.get(0);
}
public static char getUpConsonant(){
    String con = "BCDFGHJKLMNPQRSTVWXYZ";
    ArrayList<Character> consonants = new ArrayList<Character>();
    for(char c : con.toCharArray()){
        consonants.add(c);
    }
    Collections.shuffle(consonants);
        return consonants.get(0);
    }
}