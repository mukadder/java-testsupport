package com.edu.bu;

import java.util.Scanner;

public class DailyProgrammer{
	private static boolean palindrome = false;

	  public static void main(String[] args) throws Exception{

	     Scanner scanner = new Scanner(System.in);

	     int linesCounter = scanner.nextInt();

	     String lineToCheck = "";

	     lineToCheck += scanner.nextLine();

	     while(linesCounter > 0) {

	     lineToCheck += scanner.nextLine() + " ";

	     linesCounter--;
	     }


	     lineToCheck = lineToCheck.replaceAll("\\W","").replaceAll("\\s","");

	     System.out.println(lineToCheck);

	     palindromeCheck(lineToCheck);
	     String[] strings = {"This string is not palindrome",   //Not palindrome
	     "A Man, A Plan, A Canal-Panama!"}; //Palindrome

	     //loop trough the object containing all sentences
	     for (String sentence : strings){
	         //create a StrinbBuilder object to store all the data in
	         StringBuilder cleanString = new StringBuilder();
	         //first we remove all whitespaces and characters like ',./?! .etc
	         cleanString.append(RemoveSpecialCharacters(sentence)); //returns amanaplanacanalpanama like strings
	         //create an char array from the sentence the normal way
	         char[] cleanChars = cleanString.toString().toCharArray();
	         //create an char array from the sentence the reversed way
	         char[] reverseChars = cleanString.reverse().toString().toCharArray();
	         //loop for each character in the string
	         for(int i = 0; i < cleanString.length(); i++){
	             //compare the normal and reversed char array to each other
	             //if they match then the boolean palindrome will be true and the loop will continue to the next character
	             //if they dont match, the boolean palindrome will return false and the loop wil break because we now know that the string is not palindrome
	             if(cleanChars[i] == reverseChars[i]){
	                 palindrome = true;
	             } else {
	                 palindrome = false;
	                 break;
	             }
	         }
	         //checks if the string is palindrome or not
	         if(palindrome){
	             System.out.println(sentence + " is palindrome ");
	         } else {
	             System.out.println(sentence + " is NOT palindrome ");
	         }
	     }       

	  } // main method ends


	  public static void palindromeCheck2(String lineToCheck) {


	     String reverse = "";

	     for(int i = lineToCheck.length() - 1 ; i >= 0 ; i--) {
	        reverse += lineToCheck.charAt(i);     
	     } 

	     if(lineToCheck.toLowerCase().equals(reverse.toLowerCase())){
	        System.out.println("Palindrome");
	     } else {
	        System.out.println("Not a palindrome");
	     }

	  }

	  public static String RemoveSpecialCharacters(String sentence) {
		    //StringBuilder container to store all the data in
		    StringBuilder stringB = new StringBuilder();
		    //loop trough all the characters from the sentence
		    for (char c : sentence.toCharArray()) {
		        //only store characters that are equal to the below values
		        if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') ) {
		            stringB.append(c);
		        }
		    }
		    return stringB.toString().toLowerCase();
		}
	  public static boolean isPalindrome(String s)
	    {
	        int start = 0;
	        int end = s.length() - 1;
	        while(start < end)
	        {
	            char first = Character.toLowerCase(s.charAt(start));
	            char last = Character.toLowerCase(s.charAt(end));

	            if(Character.isLetter(first) && Character.isLetter(last))
	            {
	                if(first == last)
	                {
	                    start++;
	                    end--;
	                }
	                else
	                {
	                    return false;
	                }
	        }
	        if(!Character.isLetter(last)){ end--; }
	        if(!Character.isLetter(first)){ start++; }
	        }
	        return true;
	    }
	  public static boolean palindrome(String word, int start, int end) {

	        if (start >= end) return true;

	        if (word.charAt(start) == word.charAt(end)) {
	            return palindrome(word,start+1,end-1);
	        } 

	        return false;
	    }
	  public static boolean palindromeCheck(String inputWord) {
	        String inputChars = removeNonAlpha(inputWord);
	        return symmetryCheck(inputChars);
	    }

	    public static String removeNonAlpha(String inputWord) {
	        inputWord = inputWord.replaceAll("[^A-Za-z0-9]", "");
	        return inputWord.toLowerCase();
	    }

	    public static boolean symmetryCheck(String inputChars) {
	        int wordLength = inputChars.length() - 1; // To work with charAt
	        int midpoint = wordLength / 2;
	        for (int i = 0; i < midpoint; i++) { // Look through the letters in the
	                                                // first half, make sure that
	                                                // they mirror the letters in
	                                                // the symmetrical position.
	            int index = wordLength - i;
	            if (inputChars.charAt(i) != inputChars.charAt(index)) {
	                return false;
	            }
	        }
	        return true;
	    }

	  }