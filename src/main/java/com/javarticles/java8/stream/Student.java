package com.javarticles.java8.stream;

public class Student {
    public String name;
    public int age;
    public Student(String name,int age){
        this.name = name;
        this.age = age;
    }
    public void printData(){
        System.out.println("Name:"+name + " age:"+age);
    }
}
