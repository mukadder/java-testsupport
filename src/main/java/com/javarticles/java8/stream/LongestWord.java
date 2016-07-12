package com.javarticles.java8.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*
 * Given a string of words and a string of letters. Find the largest string(s) that are in the 1st string of words that can be formed from the letters in the 2nd string.
Letters can be only used once. So if the string has "a b c" then words like "aaa" and "bbb" do not work because there is only 1 "a" or "b" to be used.
If you have tie for the longest strings then output all the possible strings.
If you find no words at all then output "No Words Found"
input:

(String of words)
(String of characters)
example:

abc cca aaaaaa bca
a b c
output:

List of max size words in the first string of words. If none are found "No Words Found" displayed.

 */
public class LongestWord {

static String input;
static String inputChar;
static String[] words;
static String [] letters;
static ArrayList<String> matchingWords = new ArrayList<String>();//use array list because i dont know size of this array
static Scanner sc = new Scanner(System.in);
static String tempMatch = "";
static int matchCounter = 0;

public static void main(String[] args)
{
    System.out.println("Enter in the string of words");
    input = sc.nextLine();//get words
    System.out.println("Enter in the string of characters");
    inputChar = sc.nextLine();
    seperateWords();
    compareWords();
}

public static void seperateWords()
{
     words = input.split(" ");//split string into words
    letters = inputChar.split(" ");         
}

public static void compareWords()
{
    int largest = words[0].length();

    for(int i = 0; i < words.length; i++)
    {
        for(int j = 0; j < letters.length; j++)//for every letter for each word
        {
            if(words[i].contains(letters[j]))//if the letters match add the letter to the temp word
            {
                tempMatch+=letters[j];
            }
        }

        char[] testMatch = tempMatch.toCharArray();//put the testword and temp word into char arrays
        char[] testWord = words[i].toCharArray();//to be sorted 
        Arrays.sort(testMatch);
        Arrays.sort(testWord);

        if(Arrays.equals(testMatch, testWord))//see if they have the same characters
        {
          //System.out.println(words[i]);
          matchingWords.add(words[i]);//store the matching words in an array to be check for length later
          if(words[i].length() > largest)
          {
              largest = words[i].length();//found a word longer than previous largest
          }

          matchCounter++;
        }
        //System.out.println(tempMatch);
        tempMatch = "";//reset the temp word to be used again

    }//done comparing words (for loops)

    if(matchCounter == 0)
    {
        System.out.println("No matches found :'(");
    }

    for(int f = 0; f < matchingWords.size(); f++)//only output words that == the longest length
    {                       
        if(matchingWords.get(f).length()== largest)
        {
            System.out.println(matchingWords.get(f));
        }
    }

}


}