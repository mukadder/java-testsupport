import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class bifunctionexample {
	public static void main(String[] args){

List<Integer>list = Arrays.asList(1,2,3);
	BiFunction<List<Integer>,Predicate<Integer>,List<Integer>>filter;
	filter= (l,predicate)->{
		List<Integer>result= new ArrayList<>();
		for(Integer element :l)
			if(predicate.test(element))
					result.add(element);
			
	
		return result;
	
	};
	MONDAY, AUGUST 15, 2016

	What is bound to generic types for method references in java 8, types and exceptions
	So most of the time you are probably aware of method references that either reference instance of static methods directly, and they are useful but not necessarily that interesting.

	Integer example = 1;

	// We can bind the instance variables, in this case just the return type is bound but there couple be method parameters also
	Supplier<String> s = example::toString;

	// We can bind to static method, here the generic parameter are the method parameter and return type
	Supplier<Long> f = System::currentTimeMillis;
	Function<Integer,Integer> f = example::compareTo;


	A less common formulation is to pass in instance methods and find the first generic parameter of of the function to be the type, this allows you to easily pass in a range of actions to operate on a common type:

	Function<Integer,String> f = Integer::toString

	For example you can create an equals method that works on a subset of properties using functions mapped to instance methods as in the above example:

	public static <T> boolean equals(T one, T two, Function<? super T, ?>... accessors) {

	    if (one == two) {
	        return true;
	    } else  if (one==null || two==null) {
	        return false;
	    }

	    return Stream.of(accessors).allMatch(accessor ->
	        Objects.equals(accessor.apply(one),accessor.apply(two)));
	}

	if (equals(one, two, Thing::getName, Thing:getOtherProperty)) ...;



	Finally you can also bind the exception thrown from the method to one of the generic parameters. (Here I am using ThrowingException and ThrowingSupplier my home brew interfaces that are like there namesakes but have a generic parameter E for the exception thrown) This allows you to make you "closure" transparent to exceptions. This is more useful in a lot of cases when compared to the Stream throw nothing and "throws Exception" extremes. 

	ThrowingException<String,Integer,NumberFormatException> te = Integer::parseInt;


	You can write funky closure methods that will throw different exceptions based on the passed in method reference does, no more catch (Exception).

	public static <T, E extends Exception> T withCC(Class<?> contextClass, 
	   ThrowingSupplier<T,E> action) throws E {

	    Thread t = Thread.current();
	    ClassLoader cl = t.getContextClassLoader();
	    try {
	        t.setContextClassLoader(contextClass.getClassLoader());
	        return action.get();
	    } finally {
	        s.setContextClassLoader(cl);
	    }
	}
	       

	// Throws IOException, complier knows that this method call throws IOException

	String value = withCC(Example.class, () -> {
	    ...
	    ... new FileOutpuStream(file); ...
	    ...
	});

	// Throws another exception, complier knows that this method call throws RMIExeption

	String value = withCC(Example.class, () -> {
	        ...
	        throw new RMIException();
	    });

