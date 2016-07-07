package com.edu.bu;

import java.util.Scanner;

// this is simple api 
public class Scannor {
	public static void main (String[] args) {
		// get the sysytgem input 
		Scanner scanner = new Scanner(System.in);
		int people = scanner.nextInt();
		int plants = scanner.nextInt();
		scanner.close();
		int weeks=0;
		int unbelievableplants = plants;
		int fruits=0;
		
		while(plants<people) {
			fruits +=plants-unbelievableplants;
			unbelievableplants-=plants;
			plants += fruits;
			unbelievableplants+=fruits;
			
			weeks++; 
		}
		System.out.println(weeks);
		
		System.exit(1);
	}

}
