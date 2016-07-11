package com.edu.bu;

import java.util.Random;

public class ConsonantsAndVowels {

    private static Random rng = new Random();

    public static void main(String[] args) {
        String consonants = "bcdfghjklmnpqrstvwxyz";
        String vowels = "aeiou";
        for (char c : args[0].toCharArray()) {
            char out;
            switch (c) {
                case 'C': out = (char)(randomSelect(consonants) - 32); break;
                case 'c': out = randomSelect(consonants); break;
                case 'V': out = (char)(randomSelect(vowels) - 32); break;
                case 'v': out = randomSelect(vowels); break;
                default: throw new RuntimeException("Input must consist of "
                    + "vVcC only.");
            }
            System.out.print(out);
        }
    }

    // Returns a random character in a given String.
    public static char randomSelect(String s) {
        return s.charAt(rng.nextInt(s.length()));
    }
}