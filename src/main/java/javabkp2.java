4.1. Optional
The famous NullPointerException is by far the most popular cause of Java application failures. Long time ago the great Google Guavaproject introduced the Optionals as a solution to NullPointerExceptions, discouraging codebase pollution with null checks and encouraging developers to write cleaner code.

Inspired by Google Guava, the Optional is now a part of Java 8 library.
Optional is just a container: it can hold a value of some type T or just be null. It provides a lot of useful methods so the explicit nullchecks have no excuse anymore. Please refer to official Java 8 documentation for more details.

We are going to take a look on two small examples of Optional usages: with the nullable value and with the value which does not allownulls.

Optional< String > fullName = Optional.ofNullable( null );
System.out.println( "Full Name is set? " + fullName.isPresent() );       
System.out.println( "Full Name: " + fullName.orElseGet( () -> "[none]" ) );
System.out.println( fullName.map( s -> "Hey " + s + "!" ).orElse( "Hey Stranger!" ) );

The isPresent() method returns true if this instance of Optional has non-null value and false otherwise. The orElseGet() method provides the fallback mechanism in case Optional has null value by accepting the function to generate the default one. The map()method transforms the current Optional’s value and returns the new Optional instance. The orElse() method is similar to orElseGet()but instead of function it accepts the default value. Here is the output of this program:
Full Name is set? false
Full Name: [none]
Hey Stranger!

Let us briefly look on another example:
Optional< String > firstName = Optional.of( "Tom" );
System.out.println( "First Name is set? " + firstName.isPresent() );       
System.out.println( "First Name: " + firstName.orElseGet( () -> "[none]" ) );
System.out.println( firstName.map( s -> "Hey " + s + "!" ).orElse( "Hey Stranger!" ) );
System.out.println();