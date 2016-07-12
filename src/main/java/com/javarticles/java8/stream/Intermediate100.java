package com.javarticles.java8.stream;
/*
 * Write a program called 'censor' that takes in one argument on the command line. This argument is the filename of a newline-separated wordlist of profanity such as
http://urbanoalvarez.es/blog/2008/04/04/bad-words-list/ or
http://www.bannedwordlist.com/SwearWordResources.htm
The program should then read a text from standard in, and print it out again, but replacing every instance of a word in the wordlist with a 'censored' version. The censored version of a word has the same first character as the word, and the rest of the characters are replaced with '*'.
For example, the 'censored' version of 'peter' would be 'p****'
Example:
>echo 'You jerkface!' | censor badwords.txt
You j***face!
 */
import java.util.Scanner;
import java.io.*;
public class Intermediate100 {

public static void main(String[] args) throws IOException{
    File f = new File("swearWords.txt");
    Scanner read = new Scanner(f);
    int wordCounter = 0;

    while (read.hasNext()) {
        read.nextLine();
        wordCounter++;
    } read.close(); // end while, close Scanner

    String[]badWords = new String[wordCounter];
    int i = 0;
    Scanner readAgain = new Scanner(f);
    while(readAgain.hasNext()) {
        badWords[i] = readAgain.nextLine();
        i++;
    } readAgain.close(); // end while, close Scanner

    for (int j = 0; j < args.length; j++) {
        for (int k = 0; k < badWords.length; k++) {
            if (args[j].equals(badWords[k])) {
                for (int m = 1; m < args[j].length(); m++) {
                    args[j] = args[j].replace(args[j].charAt(m), '*');
                } // end inner for
            } // end if
        } // end middle for
        System.out.print(args[j] + " ");
    } // end outer for
    System.out.println();
} // end main
} // end class
