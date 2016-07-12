package com.javarticles.java8.stream;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
public class ForEachDemo {
   public static void main(String[] args) {
        List<Student> list = new ArrayList();
        list.add(new Student("Ram",20));
        list.add(new Student("Shyam",22));
        list.add(new Student("Kabir",18));
        //Creating instance of Consumer functional interface
        Consumer<Student> style = (Student s) -> System.out.println("Name:"+s.name +" and Age:"+s.age);
        //first way using Consumer
        list.forEach(style);
        //second way using method reference 
        list.forEach(Student::printData);
        //third way using lambda expression
        list.forEach(s -> s.printData());
   }
} 