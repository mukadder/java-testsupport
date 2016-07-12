package com.javarticles.java8.stream;
/*
 * Java 8 Supplier Example
By Arvind Rai, November 09, 2014
java.util.function.Supplier is a functional interface in java 8. Supplier can be assigned in lambda expression. Supplier can be passed as an argument to different methods in java 8. Supplier has a functional method get(). Supplier and Consumer can be used to implement producer and consumer. In our example we will describe how to create and use Supplier in our programming. 
Find a class which will store values and will be used in our Supplier Example. 
Item.java
package com.concretepage.util;
public class Item {
    private String name;
    public Item(){     
    }
    public Item(String name){     
        this.name=name;
    }
    public static String getStaticVal(){
        return "Static Val";
    }
    public String getMsg(){
        return "Hello World!";
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
} 
Supplier to return Object of a Class
Supplier can create the object of a class. Pass class name and new keyword while creating supplier. Call Supplier.get() and we will get object of that class. 
SupplierReturnObject.java
package com.concretepage.util;
import java.util.function.Supplier;
public class SupplierReturnObject {
    public static void main(String[] args) {
        Supplier<Item> supplier = Item::new;
        Item item = supplier.get();
        System.out.println(item.getMsg());
    }
} Find the output.
Hello World!  
Supplier to Fetch Method of an Object
Supplier can fetch a method of a class. Create a supplier that will take class name and its static method. Call Supplier.get() method and we will get the result of method. 
SupplierFetchMethod.java
package com.concretepage.util;
import java.util.function.Supplier;
public class SupplierFetchMethod {
    public static void main(String[] args) {
        Supplier<String> supplier = Item::getStaticVal;
        String val = supplier.get();
        System.out.println("Calling  Method:"+val);
    }    
} Find the output.
Calling  Method:Static Val 
Supplier as an Argument with Stream API
While using with Stream in java 8, we can pass Supplier as an augment to some of Stream methods. In the example we are calling Stream.map() in which we are passing Supplier instance. 
SupplierWithStream.java
package com.concretepage.util;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
public class SupplierWithStream {
    public static void main(String[] args) {
        List<Item> list = new ArrayList<>();
        list.add(new Item("AA"));
        list.add(new Item("BB"));
        list.add(new Item("CC"));
        Stream<String> names = list.stream().map(Item::getName);
        names.forEach(n -> System.out.println(n));
    }    
} Find the output.
AA
BB
CC 
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
public class BinaryOperatorDemo {
    public static void main(String[] args) {
       Map<String,String> map = new HashMap<>();
       map.put("X", "A");
       map.put("Y", "B");
       map.put("Z", "C");
       binaryOperatorFun((s1,s2)-> s1+"-"+s2,map).forEach(x->System.out.println(x));       
    }
    private static List<String> binaryOperatorFun(BinaryOperator<String> binaryOpt, Map<String,String> map){
       List<String> biList = new ArrayList<>();
       map.forEach((s1,s2)->biList.add(binaryOpt.apply(s1,s2))); 
       return biList;
    }
} 