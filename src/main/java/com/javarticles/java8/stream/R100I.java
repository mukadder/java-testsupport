package com.javarticles.java8.stream;

import java.io.*;
import java.util.Scanner;

public class R100I 
{
private String[] badWords;

public static void main(String[] args) 
{
    R100I r = new R100I();
    r.readFile();
    Scanner sc = new Scanner(System.in);
    System.out.print("Input: ");
    System.out.println(r.censor(sc.nextLine()));
}

private void readFile()
{
    badWords = new String[500];

    try(BufferedReader br = 
                new BufferedReader(
                new FileReader(
                new File("badwords.txt"))))
    {
        int count = 0;
        String line;
        while((line = br.readLine())!=null)
        {
            badWords[count] = line;
            count++;
        }
    }
    catch(IOException e)
    {
        System.out.println(e.getMessage());
    }

}

private String censor(String vile)
{
    String[] textToCensor = vile.split(" ");
    String cleanText = "";

    outer:
    for(String s : textToCensor)
    {
        for(String badWord : badWords)
        {
            if(s.equalsIgnoreCase(badWord))
            {
                cleanText+=s.charAt(0);
                for(int i = 0; i < s.toCharArray().length - 1; i++)
                {
                    cleanText+="*";
                }
                cleanText+= " ";
                continue outer;
            }
        }

        cleanText += s + " ";

    }

    return cleanText;
}


}