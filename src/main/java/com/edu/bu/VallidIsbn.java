package com.edu.bu;
/**
 * Determines if given string is a valid ISBN-10 number
 * @param isbnNumber can be in dash form or all numbers
 * @return true if parameter is a valid ISBN number, false if parameter is not a valid ISBN number
 */
/*
 * 
 */
public class VallidIsbn {
/**
 * @param
 * @return 
 * 
 */
	public static boolean isValidIsbn(String isbnNumber) {
		isbnNumber=isbnNumber.trim();
		//remove dashes
		if(isbnNumber.length()==13)	{
			isbnNumber=isbnNumber.replace("-", "").trim();
		}
		if(isbnNumber.length()==10 && isbnNumber.matches("[0-9]+")) {
			int sum =0;
			for(int i=10;i>0;i--) {
				sum += isbnNumber.charAt(i-1)*i;
			}
			if(sum%11 == 0){
				return true;
			}
		}
		return false;
	}
}
