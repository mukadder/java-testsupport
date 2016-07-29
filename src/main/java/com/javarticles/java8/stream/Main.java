package com.javarticles.java8.stream;

import java.io.IOException;
/*
 * Don't Let Your Java Objects Escape
How to use Escape Analysis with your Java projects to create more optimal, useful code.
So, after calling the method a million times, there might be millions of StringBuilder objects lying around? Not so! By employing EA, the compiler can allocate the StringBuilder on the stack instead. So when our method returns, the object is automatically deleted upon return, as the stack pointer is restored to the previous value it had before the method was called.

Escape analysis has been available for a relatively long time in Java. In the beginning we had to enable it using command line options, but nowadays it is used by default. Java 8 has improved Escape Analysis compared to previous Java versions.

How It Works
Based on EA, an object's escape state will take on one of three distinct values:

GlobalEscape: An object may escape the method and/or the thread. Clearly, if an object is returned as the result of a method, its state is GlobalEscape. The same is true for objects that are stored in static fields or in fields of an object that itself is of state GlobalEscape. Also, if we override the finalize() method, the object will always be classified as GlobalEscape and thus, it will be allocated on the heap. This is logical, because eventually the object will be visible to the JVM's finalizer. There are also some other conditions that will render our object's status GlobalEscape.
ArgEscape: An object that is passed as an argument to a method but cannot otherwise be observed outside the method or by other threads.
NoEscape: An object that cannot escape the method or thread at all.
GlobalEscape and ArgEscape objects must be allocated on the heap, but for ArgEscape objects it is possible to remove some locking and memory synchronization overhead because these objects are only visible from the calling thread.

The NoEscape objects may be allocated freely, for example on the stack instead of on the heap. In fact, under some circumstances, it is not even necessary to construct an object at all, but instead only the object's scalar values, such as an int for the object Integer. Synchronization may be removed too, because we know that only this thread will use the objects. For example, if we were to use the somewhat ancient StringBuffer (which as opposed to StringBuilder has synchronized methods), then these synchronizations could safely be removed.

EA is currently only available under the C2 HotSpot Compiler so we have to make sure that we run in -server mode.

Why It Matters
In theory, NoEscape objects can be allocated on the stack or even in CPU registers using EA, with very fast execution.

When we allocate objects on the heap, we start to drain our CPU caches because objects are placed on different addresses on the heap possibly far away from each other. This way we will quickly deplete our L1 CPU cache and performance will decrease. With EA and stack allocation on the other hand, we are using memory that (most likely) is already in the L1 cache anyhow.  So, EA and stack allocation will improve our localization of data. This is good from a performance standpoint.

Obviously, the garbage collects need to run much less frequently when we are using EA with stack allocation. This is perhaps the biggest performance advantage. Recall that each time the JVM runs a complete heap scan, we take performance out of our CPUs and the CPU caches will quickly deplete. Not to mention if we have virtual memory paged out on our server, whereby GC is devastating for performance.

The most important advantage of EA is not performance though. EA allows us to use local abstractions like Lambdas, Functions, Streams, Iterators etc. without any significant performance penalty so that we can write better and more readable code. Code that describes what we are doing rather than how it is done.
he code above will create a single instance of a Point and then it will call that Point's toString() method a large number of times. We will do it in three steps where the first step is just for warming up and then GC away all the objects that were created. The two following steps will not remove anything from the heap and we will be able to examine the heap between each step.

If we run the program with the following parameters, we will be able to see what is going on within the JVM:
 */
public class Main {
	  public static void main(String[] args)  throws IOException {
	    Point p = new Point(100, 200);
	    sum(p);
	    System.gc();
	    System.out.println("Press any key to continue");
	    System.in.read();
	    long sum = sum(p);
	    System.out.println(sum);
	    
	    System.out.println("Press any key to continue2");
	    System.in.read();
	    sum = sum(p);
	    System.out.println(sum);
	    System.out.println("Press any key to exit");
	    System.in.read();
	  }
	  private static long sum(Point p) {
	    long sumLen = 0;
	    for (int i = 0; i < 1_000_000; i++) {
	      sumLen += p.toString().length();
	    }
	    return sumLen;
	  }
	}