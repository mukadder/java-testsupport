package com.edu.bu;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


class Palindrome {
  static HashMap<Character, HashMap<Character, ArrayList<String>>>
    first_letters = new HashMap<>(), last_letters = new HashMap<>();
  private int counter;

  Palindrome(String file) {
    buildDictionary(file);
  }

  public int getCounter() { return counter; }

  public void buildDictionary(String fname) {
      String s;
      for(char i = 'a'; i <= 'z'; i++) {
        first_letters.put(i, new HashMap<>());
        last_letters.put(i, new HashMap<>());
        for(char j = 'a'; j <= 'z'; j++) {
          first_letters.get(i).put(j, new ArrayList<>());
          last_letters.get(i).put(j, new ArrayList<>());
        }
      }
      try(
        BufferedReader br = new BufferedReader(new FileReader(fname))
      ) {
        while((s = br.readLine()) != null) {
          int l = s.length();
          first_letters.get(s.charAt(0)).get(s.charAt(1))
            .add(s);
          last_letters.get(s.charAt(l - 1)).get(s.charAt(l - 2))
            .add(s);
        }
      } catch(IOException exc) {
        System.out.println("I/O Error: " + exc);
      }
    }

    public void checkPalindromes() {
      counter = 0;

      for(char c1 = 'a'; c1 <= 'z'; c1++) {
        for(char c2 = 'a'; c2 <= 'z'; c2++) {
            ArrayList<String> start = first_letters.get(c1).get(c2);
            ArrayList<String> end = last_letters.get(c1).get(c2);
            for(String word1: start) {
              for(String word2: end) {
                if(word1.equals(word2))
                  continue;
                else if(isPalindrome(word1 + word2))
                  counter++;
              }
            }
        }
      }
    }

    private static boolean isPalindrome(String s) {
      for(int i = 2, j = s.length() - 3; i <= j; i++, j--) {
        if(s.charAt(i) != s.charAt(j))
          return false;
      }
      return true;
    }
}

class GetPalindromes {

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    Palindrome p = new Palindrome("enable1.txt");
    Thread check_pal = new Thread(p::checkPalindromes);
    check_pal.start();
    try {
      while(check_pal.isAlive()) {
        System.out.print("Checking for palindromes! " + p.getCounter() + " found.\r");
        Thread.sleep(300);
      }
    } catch (InterruptedException exc) {
      System.out.println("Main thread interrupted");
    }
    long end = System.currentTimeMillis() - start;

    System.out.println("Found " + p.getCounter() + " palindromes in "
      + ((double) end / 1000) + " seconds.");
  }

}