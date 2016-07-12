package com.javarticles.java8.stream;

/*Function.apply in Java 8
By Arvind Rai, April 06, 2014
java.util.function.Function is an interface and has been introduced in java 8. Function is a functional interface. So it can be used to accept lambda expression. Function accepts one argument and returns the result. Function interface contains one method that is apply(). This is the functional interface method. Find the declaration of apply() method.
R apply(T t)
Where T is the function argument and R is the result. 
To use it we need to define Function. Suppose we have a method customShow () inside student class which will accept Function instance. Find the student class. 
Student.java*/
import java.util.function.Function;
public class Student1 {
    public String name;
    public int age;
    public Student1(String name,int age){
        this.name = name;
        this.age = age;
    }
    public  String customShow(Function<Student1,String> fun){
        return fun.apply(this);
    }
}