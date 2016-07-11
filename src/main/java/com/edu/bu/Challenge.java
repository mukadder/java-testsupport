package com.edu.bu;

import java.util.Scanner;

public class Challenge {

static Scanner scanner = new Scanner(System.in);
static char[] consonants = {'q','w','r','t','p','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m'};
static char[] vowels = {'a','e','i','o','u','y'};
static char randomLetter;
static char[] result;

public static void main(String[] args) {

    System.out.print("Give me a Input (c and v): ");
    String input = scanner.nextLine();

    char[] splitedInput = input.toCharArray();
    result = new char[splitedInput.length];

    for (int i = 0; i < splitedInput.length; i++) {
            result[i] = getAChar(splitedInput[i]);
            System.out.print(result[i] + " ");
    }
}

static char getAChar(char c) {

    if (c == 'c') { 
        randomLetter = consonants[(int) (Math.random() * consonants.length)];
    } else if (c == 'v') {
        randomLetter = vowels[(int) (Math.random() * vowels.length)];
    } else if (c == 'C') {
        randomLetter = consonants[(int) (Math.random() * consonants.length)];
        randomLetter = Character.toUpperCase(randomLetter);
    } else if (c == 'V') {
        randomLetter = vowels[(int) (Math.random() * vowels.length)];
        randomLetter = Character.toUpperCase(randomLetter);
    }
    else {
        System.out.print("Wrong input there.");
    }

    return randomLetter;
}
}