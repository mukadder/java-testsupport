package com.javarticles.java8.stream;
/**
 * Functional Interface in Java 8
By Arvind Rai, April 06, 2014
Java 8 has introduced the concept of functional interface. Functional interface is nothing but a simple java interface containing only one method. In the old style functional interface can be used with inner class or anonymous class to implement. But in java 8, we have lambda expressions to use functional interface. The difference is that lambda expression is clean and small code in comparison to inner or anonymous class. java.util.function.Function is a functional interface introduced in java 8.
How to declare Functional Interface in Java 8 | Use @FunctionalInterface
Functional interface will have one method and can have any number of arguments. The interface will be annotated with @FunctionalInterface. By this annotation we ensure that the functional interface will not have more than one method. If we try to add more than one method, then there will be compile time error as Unexpected @FunctionalInterface annotation.
Find the functional interface with no argument. 
Display.java
package com.concretepage.function;
@FunctionalInterface
public interface Display {
    public String show();    
 } Find the functional interface with one argument. 
Multiply.java
package com.concretepage.function;
@FunctionalInterface
public interface Multiply {
    public int multiply(int num);    
} Find the functional interface with two arguments. 
Add.java
package com.concretepage.function;
@FunctionalInterface
public interface Add {
    public int addData(int n1, int n2);    
} 
How to run Functional Interface with Lambda Expressions
Now we will see how to use functional interface with lambda expression. Find the example which will implement the above functional interface. To run the example I am using JDK 8 and NetBeans IDE 8.0. 
FunctionalInterfaceDemo.java
package com.concretepage.function;
public class FunctionalInterfaceDemo {
    public static void main(String[] args) {
        // functional interface with no argument
         Display display = () -> "Functional interface with no argument";
         String s= display.show();
         System.out.println(s);
        // functional interface with one argument
        Multiply multiply = (int num) -> num*10;
        int res= multiply.multiply(5);
        System.out.println(res);
        // functional interface with two argument
        Add add =(int a, int b) -> a+b;
        int rs = add.addData(15, 20);
        System.out.println(rs);
    }
 */
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
public class FunctionDemo {
    public static void main(String[] args) {
        List<Student1> list = new ArrayList();
        list.add(new Student1("Ram",20));
        list.add(new Student1("Shyam",22));
        list.add(new Student1("Kabir",18));
        
        // Simple use of function
        for(Student1 st: list){
            System.out.println(st.customShow(s->s.name+": "+s.name));
        }
        
        //Style one to declare function 
        Function<Student1,String> styleOne = s->{
            String result =  "Name:"+s.name +" and Age:"+s.age;
            return result;
        };
        
        //Style two to declare function
        Function<Student1,String> styleTwo = s->        
            "Name:"+s.name +" and Age:"+s.age;
        
        System.out.println("--print value by style one--");
        //print the values of list using stle one function
        for(Student1 st: list){
            System.out.println(st.customShow(styleOne));
        }
        
        System.out.println("--print value by style two--");
        //print the values of list using style two function
        for(Student1 st: list){
            System.out.println(st.customShow(styleTwo));
        }
        
    }
} 