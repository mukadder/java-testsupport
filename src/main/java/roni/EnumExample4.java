package roni;
class EnumExample4{  
enum Season{   
WINTER(5), SPRING(10), SUMMER(15), FALL(20);   
  
private int value;  
private Season(int value){  
this.value=value;  
}  
}  
public static void main(String args[]){  
for (Season s : Season.values())  
System.out.println(s+" "+s.value);  
  
}
}
/*The java compiler internally adds the values() method when it creates an enum. 
 * The values() method returns an array containing all the values of the enum.
 * The java compiler internally creates a static and 
 * final class that extends the Enum class as shown in the below example:
 * The enum can be defined within or outside the class because it is similar to a class.
 * 
 * class EnumExample3{  
enum Season { WINTER, SPRING, SUMMER, FALL; }//semicolon(;) is optional here  
public static void main(String[] args) {  
Season s=Season.WINTER;//enum type is required to access WINTER  
System.out.println(s);  
}}  Initializing specific values to the enum constants

The enum constants have initial value that starts from 0, 1, 2, 3 and so on. 
But we can initialize the specific value to the enum constants by defining fields and constructors. 
As specified earlier, Enum can have fields, constructors and methods.
Constructor of enum type is private. If you don't declare private compiler internally creates private constructor.


 * 
 * 
 * 
 * 
 * 
 */
