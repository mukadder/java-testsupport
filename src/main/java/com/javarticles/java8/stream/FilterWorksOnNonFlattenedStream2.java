package com.javarticles.java8.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class FilterWorksOnNonFlattenedStream2 {
 public static void main(String[] args) {
  
  
  List<String> list1=new ArrayList<String>();
  list1.add("Hello");
  list1.add("Developers");
  
  List<String> list2=new ArrayList<String>();
  list2.add("Welcome");
  list2.add("To Codermagnet");
  
  List<String> list3=new ArrayList<String>();
  list3.add("Hello");
  list3.add("WhatsUp?");
  
    
  List<List<String>> itemList=new ArrayList<List<String>>();
  itemList.add(list1);
  itemList.add(list2);
  itemList.add(list3);

  
  //Converting the List of List<String> to Stream
  //List<List<String>> --> Stream<List<String>>
  Stream<List<String>> listStream=itemList.stream();  
  
  //Filtering only the List objects containing the string "Hello"
  Stream<List<String>> filteredList=listStream.filter(x -> x.contains("Hello"));
  
  filteredList.forEach(System.out :: println);
 }
}