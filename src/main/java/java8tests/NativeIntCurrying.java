package java8tests;

import java.util.function.IntBinaryOperator ;
import java.util.function.IntFunction ;
import java.util.function.IntUnaryOperator ;

public class NativeIntCurrying {

    public void currying() {
        // Create a function that adds 2 ints
        IntBinaryOperator adder = ( a, b ) -> a + b ;

        // And a function that takes an integer and returns a function
        IntFunction<IntUnaryOperator> currier = a -> b -> adder.applyAsInt( a, b ) ;

        // Call apply 4 to currier (to get a function back)
        IntUnaryOperator curried = currier.apply( 4 ) ;
        
        // Results
        System.out.printf( "int curry : %d\n", curried.applyAsInt( 3 ) ) ; // ( 4 + 3 )
    }

    public void composition() {
        // A function that adds 3
        IntUnaryOperator add3   = (a) -> a + 3 ;
        
        // And a function that multiplies by 2
        IntUnaryOperator times2 = (a) -> a * 2 ;
        
        // Compose add with times
        IntUnaryOperator composedA = add3.compose( times2 ) ;
        
        // And compose times with add
        IntUnaryOperator composedB = times2.compose( add3 ) ;
        
        // Results
        System.out.printf( "int times then add: %d\n", composedA.applyAsInt( 6 ) ) ;  // ( 6 * 2 ) + 3
        System.out.printf( "int add then times: %d\n", composedB.applyAsInt( 6 ) ) ;  // ( 6 + 3 ) * 2
    }
    
    public static void main( String[] args ) {
        new NativeIntCurrying().currying() ;
        new NativeIntCurrying().composition() ;
    }
}