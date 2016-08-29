import java.util.function.Function;

public  class  Java8Revision  {

    private  static  int  staticNumber ; 
    private  int  number ;

    public  void  write ()  { 
        Function < Integer ,  Integer >  function  =  ( Integer  a )  ->  staticNumber  =  a ; 
        Function < Integer ,  Integer >  function2  =  ( Integer  a )  ->  number  =  a ; 
        Function < Integer ,  String >  toString  =  n  ->  String . valueOf ( n ); 
        Function < String ,  Integer >  toInteger  =  s  ->  Integer . valueOf ( s );
        //It is a specialization of Functionwhich takes two arguments and returns a result.
        BiFunction < Integer ,  String ,  String >  concat  =  ( Integer  i ,  String  s )  ->  s  +  ":"  +  i ;

        assert  a "1" . equals ( concat . apply ( 1 ,  "a" ));
        assert  "4" . equals ( toString . apply ( 4 )); 
        assert  toInteger . apply ( "4" )  ==  4 ;
        //t is a Functionthat takes one argument and returns a result of the same type. For example, the function identityalways returns the value passed as argument.
//One BinaryOperatoris a specialization of BiFunctionthe parameters and the result share the same type.
        BinaryOperator < String >  concatString  =  ( s1 ,  s2 )  ->  s1 . Concat ( ":" ). Concat ( s2 );
        //One Predicatetakes one argument and returns a boolean.


        Predicate < String >  isEmpty  =  s  ->  s  ==  null  ||  s . IsEmpty () 
        		Predicate < String >  isTrimmed  =  s  ->  s . Equals ( s . Trim ());

        assert  a "1" . equals ( concatString . apply ( "a" ,  "1" ));
        assert  UnaryOperator.identity().apply ( "a" ). equals ( "a" );
/*
 * The method composeapplies the function toIntegerand the function 
 * toStringas the method andThenapplies toStringthen toInteger.
 */
        //negate Conversely a predicate
        //andand orallow to chain predicates as the logical operator,
       //
        The static method isEqualtests the equality of two objects accordingObject#equals
        Predicate < String >  isEmpty  =  s  ->  s  ==  null  ||  s . IsEmpty () 
        		Predicate < String >  isTrimmed  =  s  ->  s . Equals ( s . Trim ());

        		assert  isEmpty . test ( null )  ==  true ; 
        		assert  isEmpty . test ( "" )  ==  true ; 
        		assert  isEmpty . test ( "not empty" )  ==  false ;

        		assert  isEmpty . negate . () and ( isTrimmed ). test ( "not empty" )  ==  true ; 
        		assert  isEmpty . negate . () and ( isTrimmed ). test ( "not empty" )  ==  false ;

        		assert  isEmpty . gold ( isTrimmed ). test ( "" )  ==  true ; 
        		assert  isEmpty . gold ( isTrimmed ). test ( "not empty" )  ==  true ;

        		assert  Predicate . isEqual ( "hello" ). test ( "hello" )  ==  true ;
        assert  "4" . equals ( toString . compose ( toInteger ). apply ( "4" )); 
        assert  toString . andThen ( toInteger ). apply ( 4 )  ==  4 ;
    } 
    //One BiPredicatetakes two arguments and returns a boolean.
    //One Supplierdoes not take an argument and produces a result.

    Supplier < String >  EmptyString  =  ()  ->  "" ;

    assert  "" . equals ( EmptyString . get ());
   // One Consumertakes an argument but returns no result.
    
    Consumer < String >  print  =  s  ->  System . Out . Println ( s ) 
    		Consumer < String >  hello  =  s  ->  System . Out . Printf ( "Hello% s" ,  s );

    		print . accept ( "something" );  // something 
    		print . andthen ( hello ). accept ( "JC" );  // JC JC Hello!
    		//The method andThenof chaining consumers.
    		//he Comparatorhave become FunctionalInterface.
    		Comparator < Integer >  ascending  =  ( a ,  b )  ->  a . CompareTo ( b );
    		assert  ascending . comparing ( 10 ,  1 )  >  0 ; 
    		assert  ascending . reversed . () compares ( 10 ,  1 )  <  0 ;
}
Public  Void sample of Supplier_T () {
    Final AtomicInteger Count = New AtomicInteger ( 65 );
   Supplier <Character> Supplier = () -> ( Char ) Count.GetAndIncrement ();

   assertThat (Supplier.Get (), Is ( 'A' ));
}
}//int, long, doubleit has become

IntSupplier
LongSupplier
DoubleSupplier

UnaryOperator<T>
TThe type of the argument receives one, Tis a Functional Interface that returns an object of type.

Stream#iterateMethod specified by the second argument of.

Public  Void sample of unaryOperator_T () {
    UnaryOperator <Character> Operator = (C) -> ( Char ) (( int ) C Tasu 1 );

    Char Character = 'A' ;
    assertThat (Operator.Apply (Character), Is ( 'B' ));
}
Function<T, R>
TThe type of the argument receives one, Ris a Functional Interface that returns an object of type.
Public  Void sample of function_T_R () {
    Function <String [], String> Function = (Array) -> Array [ 1 ];

    String [] Line = New String [] { "1" , "Mike" , "Mathematics" , "89" };
    assertThat (Function.Apply (Line), Is ( "Mike" ));
}
Function<T, R>The Tis int, long, doubleit became

IntFunction<R>
LongFunction<R>
DoubleFunction<R>
There are, respectively

IntStream#mapToObj
LongStream#mapToObj
DoubleStream#mapToObj
In will be used.
Public  Void sample of intFunction_R () {
    IntFunction <Character> Function = (Value) -> ( Char ) Value;

    int Ch = 65 ;
    assertThat (Function.Apply (Ch), Is ( 'A' ));
}
Public  Void sample of toLongFunction_T () {
    ToLongFunction <Date> Function = (Date) -> Date.GetTime ();

    Calendar = Calendar.getInstance Calendar (TimeZone.getTimeZone ( "GMT" ));
    Calendar.Set ( 1970 , Calendar.JANUARY, 1 , 0 , 0 , 0 );
    Calendar.Set (Calendar.MILLISECOND, 0 );
    Date date = calendar.getTime ();

    assertThat (Function.ApplyAsLong (Date), Is ( 0L ));
}
Public  Void sample of doubleUnaryOperator () {
    Operator = DoubleUnaryOperator (Value) -> Value * Value;

    Double Value = 0.5D ;
    assertThat (Operator.ApplyAsDouble (Value), Is ( 0.5D * 0.5D ));
}
Public  Void sample of predicate_T () {
    Predicate <Date> Predicate = (Date) -> Date.GetTime () <System.currentTimeMillis ();

    Date = Date New Date (System.currentTimeMillis () - 1000 );
    assertThat (Predicate.Test (Date), Is ( True ));
}
Public  Void sample of intPredicate_T () {
    Predicate = IntPredicate (Value) -> Value Pasento 2 == 0 ;

    int Value = 11 ;
    assertThat (Predicate.Test (Value), Is ( False ));
}
Public  Void sample of comparator_T () {
    Comparator <String> Comparator = (Left, Right) -> Left.CompareTo (Right);

    Left = String "January" ;
    Right = String "December" ;

    assertThat (Comparator.Compare (Left, Right), Is (GraterThan ( 0 )));
}
Public  Void sample of Consumer_T () {
    Final StringBuilder Builder = New StringBuilder ( "Hello" );
   Consumer <String> consumer = builder :: append;

   Consumer.Accept ( "Ichiro" );
   assertThat (Builder.ToString (), Is ( "Hello Ichiro" ));
}
Public  Void sample of IntConsumer () {
    Final AtomicInteger Value = New AtomicInteger ( 10 );
   IntConsumer consumer = value :: addAndGet;

   Consumer.Accept ( 1 );
   assertThat (Value.Get (), Is ( 11 ));
}
Public  Void sample of IntConsumer () {
    Final AtomicInteger Value = New AtomicInteger ( 10 );
   IntConsumer consumer = value :: addAndGet;

   Consumer.Accept ( 1 );
   assertThat (Value.Get (), Is ( 11 ));
}BinaryOperator<T>
TThe type of the argument receives two, Tis a Functional Interface that returns an object of type.

Stream#reduce(BinaryOperator<T>)
Stream#reduce(T, BinaryOperator<T>)
Stream#reduce(U, BiFunction<U, T, U>, BinaryOperator<U>)
Public  Void sample of binaryOperator_T () {
    BinaryOperator <String> Operator = (Left, Right) -> Left Tasu '' Tasu Right;

    assertThat (Operator.Apply ( "Hello" , "World" ), Is ( "Hello World" ));
}
Public  Void sample of doubleBinaryOperator () {
    Operator = DoubleBinaryOperator (Left, Right) -> Left * Right;

    assertThat (Operator.ApplyAsDouble ( 0.3D , 1.5D ), Is ( 0.3D * 1.5D ));
}

BiFunction<T, U, R>
TType of argument and Ureceives the type of the argument, Ris a Functional Interface that returns an object of type.

Stream#reduce(U, BiFunction<U, T, U>, BinaryOperator<U>)And press to use.



Public  Void sample of biFunction_T_U_R () {
    BiFunction <ZoneId, DateTimeFormatter, String> Function = (Zone, Formatter) -> {
        LocalDate now = LocalDate.now (zone);
        Return Formatter.Format (Now);
    };
    
    BiConsumer<R, T>
    RType of argument and Ttake the type of argument, is a Functional Interface that does not return anything.

    Stream#collect(Supplier<R>, BiConsumer<R, T>, BiConsumer<R, R>)And press to use.

    It should be noted, Stream#collectsuch as when used in, the first argument ( Rtowards),

    We will write in such a way that we collectively set.

    Public  Void sample of biConsumer_R_T () {
        BiConsumer <List <String>, String> consumer = List :: add;

        List <String> List = New ArrayList <> (Arrays.asList ( ". Uno" , "Dos" ));
        Three = String "tres" ;

        consumer.accept (list, three);

        assertThat (List.Size (), Is ( 3 ));
        assertThat (List, HasItems ( "tres" ));
    }Public  Void sample of objIntConsumer_R () {
        ObjIntConsumer <AtomicInteger> consumer = AtomicInteger :: addAndGet;

        Sum = AtomicInteger New AtomicInteger ( 10 );
        Consumer.Accept (Sum, 1 );

        assertThat (Sum.Get (), Is ( 11 ));
    }

    Zone = ZoneId.Of ZoneId ( "Asia / Tokyo" );
    Pattern = DateTimeFormatter.OfPattern DateTimeFormatter ( "yyyy / MM" );
    Now = String New SimpleDateFormat ( "yyyy / MM" ) .Format ( New Date ());

    assertThat (function.apply (zone, pattern), is (now));
}