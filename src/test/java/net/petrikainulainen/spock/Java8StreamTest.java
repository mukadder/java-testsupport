package net.petrikainulainen.spock;
import java.util.OptionalDouble;

import org.junit.Before;
import static java.util.stream.Collectors.*;
import java.util.Comparator;
import java.util.Calendar;
import java.util.AbstractMap;
import org.junit.Test;
import static java.util.Arrays.asList;
import static java.util.Map.Entry;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import java.time.LocalDate;
import java.util.TreeMap;
import java.util.Random;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Map;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.List;
import java.util.Collections;
import static java.util.stream.Collectors.toList;
import static java.util.Comparator.*;
import java.util.stream.Collectors;

import org.junit.Test;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

import java.util.stream.DoubleStream;

import org.apache.log4j.Logger;

import static com.google.common.truth.Truth.assertThat;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
import java.util.function.Consumer;

import org.paumard.model.McDonald;
import org.paumard.model.Student;
import org.paumard.model.PrimeMinister;
import org.paumard.model.Person;
import org.paumard.model.Country;

import java.util.function.Function;

import org.paumard.model.Student1;
public class Java8StreamTest {
	
	List<Item> items;
	List<Fruit> fruits;
    private Hello hello;
    private static final Logger logger = Logger
			.getLogger(Java8StreamTest.class);
    class Fruit {
    	String name;
    	private double level;
    	
    	public Fruit(String name,double level){
    		this.name=name;
    		this.level=level;
    	}
    	public String getName(){
    		return name;
    	}
    	public double  getLevel(){
    		return level;
    	}
    }
    class Item {

		String key;

		public Item(String key) {
			super();
			this.key = key;
		}

		public String getKey() {
			return key;
		}
	}
    static class Artist {
        private final String name;

        Artist(String name) {
            this.name = name;
        }

        public String toString() {return name;}

    }
    private static <T,U> AbstractMap.SimpleEntry<T,U> pair(T t, U u) {
        return new AbstractMap.SimpleEntry<T,U>(t,u);
    }
    static class Album {
        private List<Artist> artist;

        Album(List<Artist> artist) {
            this.artist = artist;
        }

        List<Artist> getArtist() {
            return artist;
        }

    }
    @Before
    public void createHelloObject() {
        hello = new Hello();
items = new ArrayList<>();
		
		items.add(new Item("ONE"));
		items.add(new Item("TWO"));
		items.add(new Item("THREE"));
		 fruits = Arrays.asList(new Fruit("apple",102),
                new Fruit("grape",102),
                new Fruit("orange",103));
    }

    @Test
    public void sayHello_ShouldSayHelloToPersonWhoseNameIsGivenAsMethodParameter() {
        String greeting = hello.sayHello("Petri");
        assertThat(greeting, is("Hello Petri"));
    }
    
    @Test
    
    public void stramOfStringsToArray() {
    	String[] stringarray= Stream.of("one","two","three").toArray(String[]::new);
    	
    	assertTrue(stringarray.length==3);
    }
    @Test
    
    public void streamofInts() {
    	
    	//Integer[] intarray= Stream.of(1,2,3).toArray(Integer[]:new);
    	Integer[] stringArray = Stream.of(1, 2, 3).toArray(Integer[]::new);
    	assertTrue(stringArray.length==3);
    }
    @Test
    public void stramofsmallints() {
    	int[] arrray= IntStream.of(1,2,3).toArray();
    	assertTrue(arrray.length==3);
    }
    @Test
    public void longstream() {
    	long[] array= LongStream.of(1,2,3).toArray();
    	assertTrue(array.length==3);
    	
    }
   
    @Test
    public void convertiingstramtolist() {
    	
    	List<String> liststring= Stream.of("a","b","c").collect(toList());
    	assertTrue(liststring.size()==3);
    	
    }
    @Test
	public void stream_from_function() {

		Stream.iterate(0, n -> n + 3).limit(10).forEach(System.out::println);

	}
    @Test
    public void mapper(){
    	
    	
    	Map<String, Item> map = items.stream().collect(
				Collectors.toMap(Item::getKey, item -> item));
    	 map.forEach((k,v)->System.out.println("key:"+k +"  value:"+v));
    	 System.out.println(map);
    	 logger.info(map);
		assertTrue(map.keySet().size() == 3);
		
    }
    @Test
    public void streamtoset() {
    	
    	Set set=items.stream().collect(toSet());
    	assertTrue(set.size()==3);
    }
    
    @Test
    public void stramofstrings() {
    	
    String string=	Stream.of("abc","def").collect(Collectors.joining());
    assertEquals("abcdef" ,string);
    }
    @Test
    public void DouleStreamBuilder() {
    	
    	//Double sum = DoubleStream().builder().add(10).add(10).sum();
    	double sum = DoubleStream.builder().add(10).add(10).build().sum();
    	assertEquals(sum ,20,0);
    }
    @Test
    public void Doublestreamconcat() {
    	
    	//DoubleStream first= DoubleStream().builder().add(10).build();
    	//DoubleStream second = DoubleStream().builder().add(10).build();
    	//DoubleStream concat= DoubleStream().concat(first,second);
    	//assertEquals(20,concat.sum(),0);
    	DoubleStream first = DoubleStream.builder().add(10).build();
		DoubleStream second = DoubleStream.builder().add(10).build();

		DoubleStream third = DoubleStream.concat(first, second);

		assertEquals(20, third.sum(), 0);
    }
    
    
    @Test
    public void DoubleStreamTest() {
    	
    	DoubleStream empty = DoubleStream.empty();
    	assertTrue(empty.count()==0);
    }
    
    @Test
    public void getMaxofThestream() {
    	
    	OptionalDouble max= DoubleStream.of(5,10,15).max();
    	
    	assertEquals(15,max.getAsDouble(),0);
    }
    @Test
    
    public void parsetoDoubleStream() {
    	
    	List<String>doubles = new ArrayList<String>();
    	doubles.add("1");
    	doubles.add("2");
    	doubles.add("3");
    	OptionalDouble  doublenumber = doubles.stream().mapToDouble(Double::parseDouble).max();
    	assertEquals(3, doublenumber.getAsDouble(),0);
    }
    @Test 
    
    public void getTheLastElementOfthestream() {
    	Optional<String>value = Stream.of("a","b","c").reduce((a,b)->b);
    	assertEquals("c",value.get());
    	String lastValue = Stream.of("a", "b", "c").reduce((a, b) -> b)
				.orElse("false");

		assertEquals("c", lastValue);
    }
    public  Optional<Fruit> find(String name, List<Fruit> fruits) {
    	   for(Fruit fruit : fruits) {
    	      if(fruit.getName().equals(name)) {
    	         return Optional.of(fruit);
    	      }
    	   }
    	   return Optional.empty();
    	}
    @Test
    public void useOptionsal() {
    	Optional<Fruit> found = find("orange", fruits);	
    	 Fruit fruit = found.get();
    	//assertEquals("lemon" , fruit.getName(),0);
    	 assertEquals("orange" , fruit.getName());
    	 Optional<Fruit> found1 = find("loe", fruits);	
    	 assertEquals(found1,Optional.empty());
    }
    @Test
    
    public void useGroupingslevels() {
    	Map<Double, List<Fruit>> groupByLevel = fruits.stream()
				.collect(Collectors.groupingBy(Fruit::getLevel));

    	//Map<Double,Long>levels = fruits.stream().collect(Collectors.groupingBy(Fruit::getLevel));
    	assertEquals(2.0,groupByLevel.get(102.0).size(),0);
    	
    }
    @Test
    
    public void useGroupingsbyCounting() {
    	
    	//Map<Double, Long>groupsbycounting = fruits.stream().collect(Fruit::getLevel, Collectors.counting());
    	Map<Double, Long> groupByLevel = fruits.stream().collect(
				Collectors.groupingBy(Fruit::getLevel,
						Collectors.counting()));
    	assertEquals(2.0, groupByLevel.get(102.0), 0);
    }
    @Test 
    public void findElementLessThan3() {
    	long element = Stream.of(1,2,3,4,5).filter(p-> p.intValue()<4).count();
    	assertEquals(3,element);
    }
    @Test 
    public void applymaptostream() {
    	List<String> strings = Stream.of("one", null, "three").map(s -> {
			if (s == null)
				return "[unknown]";
			else
				return s;
		}).collect(Collectors.toList());
    	
    }
 
 /* We have a list of Student class. Grouping is done on the basis of student class name.
  List is converted into stream of student object. 
  Then call collect method of stream. groupingBy of Collectors class checks each element of stream 
  and gets class name and then group it as list. Finally we get a map where key is the
  one by which grouping is done. Find the complete example. */
	@Test
	
public void modelgroupingby(){
		Student s1 = new Student("Ram", "A", 20);
        Student s2 = new Student("Shyam", "B", 22);
        Student s3 = new Student("Mohan", "A", 22);
        Student s4 = new Student("Mahesh", "C", 20);
        Student s5 = new Student("Krishna", "B", 21);
        List<Student> list = Arrays.asList(s1,s2,s3,s4,s5);	
        System.out.println("----Group Student on the basis of ClassName----");
        Map<String, List<Student>> stdByClass = list.stream()
                    .collect(Collectors.groupingBy(Student::getClassName));
        stdByClass.forEach((k,v)->System.out.println("Key:"+k+"  "+ 
                ((List<Student>)v).stream().map(m->m.getName()).collect(Collectors.joining(","))));
      //Group Student on the basis of age
        System.out.println("----Group Student on the basis of age----");
        Map<Integer, List<Student>> stdByAge = list.stream()
                    .collect(Collectors.groupingBy(Student::getAge));
        
        stdByAge.forEach((k,v)->System.out.println("Key:"+k+"  "+ 
                ((List<Student>)v).stream().map(m->m.getName()).collect(Collectors.joining(","))));
	}
	@Test
	public void optiontest() {
		Optional<PrimeMinister> pm = Optional.of(new PrimeMinister());
        String pmName = pm.map(PrimeMinister::getName).orElse("None");
        System.out.println(pmName);
        //Assign Some Value to PrimeMinister.name
        pm = Optional.of(new PrimeMinister("Narendra Modi"));
        pmName = pm.map(PrimeMinister::getName).orElse("None");
        System.out.println(pmName);
	}
	
	@Test
	
	public void flatmapDemo() {
		Optional<PrimeMinister> primeMinister = Optional.of(new PrimeMinister("Narendra Modi"));
        Optional<Country> country = Optional.of(new Country(primeMinister));
        Optional<Person> person = Optional.of(new Person(country));
        String pmName= person.flatMap(Person::getCountry).flatMap(Country::getPrimeMinister)
                .map(PrimeMinister::getName).orElse("None");
        System.out.println(pmName);
	}
	@Test
	
	public void OptionalFilterTest() {
		Optional<PrimeMinister> pm = Optional.of(new PrimeMinister("Narendra Modi"));
        //Using Optional.isPresent
        System.out.println(pm.isPresent());
        
	}
	@Test
	public void Predicatetest(){
		Optional<PrimeMinister> pm = Optional.of(new PrimeMinister("Narendra Modi"));
        //Using Optional.filter
        Predicate<PrimeMinister> pmPredicate = (p)-> p.getName().length() > 15;
        System.out.println(pm.filter(pmPredicate));
		
	}
	@Test
	public void Predicatetest2(){
		Optional<PrimeMinister> pm = Optional.of(new PrimeMinister("Narendra Modi"));
        //Using Optional.ifPresent
        Consumer<PrimeMinister> pmConsumer = (PrimeMinister p) -> System.out.println(p.getName());
        pm.ifPresent(pmConsumer);
	}
	//In the example we have created two Predicate and then creating a student object, we are passing it to test method of Predicate. 
	@Test
	public void studenttest() {
		Predicate<Student1> maleStudent = s-> s.age >= 20 && "male".equals(s.gender);
	      Predicate<Student1> femaleStudent = s-> s.age > 15 && "female".equals(s.gender);
	      
	      Function<Student1,String> maleStyle = s-> "Hi, You are male and age "+s.age;
	      
	      Function<Student1,String> femaleStyle = s-> "Hi, You are female and age "+ s.age;
	      
	      Student1 s1 = new Student1(21,"male");
	      if(maleStudent.test(s1)){
	          System.out.println(s1.customShow(maleStyle));
	      }else if(femaleStudent.test(s1)){
	          System.out.println(s1.customShow(femaleStyle));
	      }      
	  }    
		
	@Test
	public void comparingComparotorTest() {
		String[] stringsArray = "The quick brown fox has a dirty ladder".split("\\s+");
		System.out.println(
                Arrays.stream(stringsArray)
              .sorted(Comparator.<String,String>comparing(String::toLowerCase).reversed())
                .collect(Collectors.toList())
        );
    }
	@Test
	public void TestingSummingoperationsonList() {
		List<Integer> list = Arrays.asList(1,2,3,4,5,6,7);
		//final IntSummaryStatistics intSummaryStatistics = list.stream().mapToInt(Integer::intValue).summaryStatistics();
        //System.out.println("intSummaryStatistics = " + intSummaryStatistics);
		//sum it, flavour 1 There's an extra level of boxing going on in as the reduction function is a BinaryOperator<Integer> - it gets passed two Integer values, unboxes them, adds them, and then re-boxes the result
	    int sum1 = list.stream().reduce(0, (acc, e) -> acc + e).intValue();
//integers.values().stream().mapToInt(Integer::intValue).sum();
	 //   integers.values().stream().collect(summingInt(Integer::intValue)));
	    //sum it, flavour 2 he mapToInt version unboxes the Integer elements from the list once and then works with primitive int values from that point on as an IntStream.
	    int sum2 = list.stream().mapToInt(e -> e).sum();
	    List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
	    //IntSummaryStatistics. It is this object which provides us utility method like getMin(), getMax(), getSum() or getAverage()

//Read more: http://java67.blogspot.com/2014/04/java-8-stream-api-examples-filter-map.html#ixzz3wUz9eEcM
	    IntSummaryStatistics stats = primes.stream().mapToInt((x) -> x).summaryStatistics();
	    		 System.out.println(stats + "ffffffffffffffffffff " + stats);
	    		//Read more: http://java67.blogspot.com/2014/04/java-8-stream-api-examples-filter-map.html#ixzz3wUxwLMWV
	    System.out.println(sum1 + "ffffffffffffffffffff " + sum2);
		
	}
	@Test
	public void find_max_value_from_list_of_integers_java8() {
		List<Integer> CENTERS_ROOKIE_YEAR = Arrays.asList(
		        1946, 1988, 1970, 1931, 1940, 1920, 1980, 1953, 1960, 1974, 1987
		    );
		 Calendar calendar = Calendar.getInstance();
	        int currentYear = calendar.get(Calendar.YEAR);
	    OptionalInt maxElement = CENTERS_ROOKIE_YEAR.stream().filter(rate -> rate<currentYear).mapToInt(p -> p)
	            .max();

	    assertEquals(1988, maxElement.getAsInt());

	    // or

	    Optional<Integer> maxElement2 = CENTERS_ROOKIE_YEAR.stream().reduce(
	            Integer::max);

	    assertEquals(new Integer(1988), maxElement2.get());
	}
	@Test
	public void generatingmapsortingbykeyorvalue(){
		final Map<LocalDate, Integer> foobar = new TreeMap<>();
		 
		// Fill a date -> int map with 12 random ints between 0 and 100, 
		new Random(System.currentTimeMillis()).ints(0,100).limit(12).forEach(value -> 
			foobar.put(
				LocalDate.now().withMonth(foobar.size() + 1), 
				value
			));
		// print them for verbosity
		foobar.entrySet().forEach(System.out::println);
		// get the maximum
		Map.Entry<LocalDate, Integer> max 
			= foobar
			    // from all entries
			    .entrySet()
			    // stream them
			    .stream()
			    // max, obviously
			    .max(
				    // this one is cool. It generates
				    // Map.Entry comparators by delegating to another
				    // comparator, exists also for keys
				    Map.Entry.comparingByValue(Integer::compareTo)
			    )
			    // Get the optional (optional because the map can be empty)
			    .get();
		System.out.println("Max is0000000000000000000 " + max);
	}
	
	@Test 
	public void testinggroupingbyOneToMany() {
		  List<Album> albums = Arrays.asList(
	                new Album(
	                        asList(
	                                new Artist("bob"),
	                                new Artist("tom")
	                        )
	                ),
	                new Album(asList(new Artist("bill")))
	        );
//I think you are after Collectors.mapping which can be passed as a second argument to groupingBy
	        Map<Artist, List<Album>> x = albums.stream()
	                .flatMap(album -> album.getArtist().stream().map(artist -> pair(artist, album)))
	                .collect(groupingBy(Entry::getKey, mapping(Entry::getValue, toList())));

	        x.entrySet().stream().forEach(System.out::println);
	}

	@Test
    public void functionalTest() {

        //Start with a list of words containing nulls, empty strings, and random words.
        List<String> words = Arrays.asList("aardvark", null, "aluminum", "bail", "", "a", "zero");

        //Filter out null and empty strings, map all words to start with an upper case letter, and
        //collect the results back into a list.
        List<String> results = words.stream()
                .filter(s -> s != null && !s.equals(""))
                .map(s -> Character.toUpperCase(s.charAt(0)) + s.substring(1))
                .collect(Collectors.toList());

        //Reduce the result list to count the number of words starting with 'A'. Note that
        //the combiner (third parameter) is not used unless we're doing this in parallel,
        //which we're not as we didn't use stream().parallel() here.  I understood this due
        //to a response from my SO question here:   http://stackoverflow.com/a/30016171/857994.
        Long countOfAWords = results.stream().reduce(
                0L,
                (a, b) -> b.charAt(0) == 'A' ? a + 1 : a,
                Long::sum);

        //Assert the result list and count match our expected values.
        assertEquals("[Aardvark, Aluminum, Bail, A, Zero]", results.toString());
        assertEquals(Long.valueOf(3), countOfAWords);
    }
	@Test
	public void convertStringToUpperCaseStreams() {
		/* filter collection based off other collection streamed 
		 * filePaths.stream()
  .filter(p -> acceptedIds.contains(p.getParent().getFileName().toString()))
  .collect(Collectors.toList());
  
  filePaths.stream()
         .filter(p -> acceptedIds.stream().anyMatch(id -> p.toString().contains(id)))
         .collect(toList());
         final Set<String> acceptedIds = ...
// Matches the number of the file, concluded with the underscore
final Pattern extractor = Pattern.compile("\.*(?<number>\d+)_")
filePaths.stream().filter( path -> {
    final Matcher m = extractor
        .matcher(path.getFileName().toString());
    m.find();
    return acceptedIds.contains(m.group("number"));
})
List removeMissing(List l1, List l2) {
    List ret = l1.stream()
        .filter(o -> l2.contains(o)) //Keep if object o satisfies the condition "l2 contains a reference to this object"
        .collect(Collectors.toList());
    return ret;
}
		 */
		
		
	    List<String> collected = Stream.of("a", "b", "hello") // Stream of String 
	            .map(string -> string.toUpperCase()) // Returns a stream consisting of the results of applying the given function to the elements of this stream.
	            .collect(Collectors.toList());
	    assertEquals(asList("A", "B", "HELLO"), collected);
	}   
	@Test
	public void testflatMap() throws Exception {
		/*
		 * In the second example, a Stream of List is passed. It is NOT a Stream of Integer!
		 *  If a tranformation Function has to be used (through map), then first the Stream has to be
		 *   flattened to something else (a Stream of Integer). If flatMap is removed then the following error is returned: 
		 *   The operator + is 
		 * undefined for the argument type(s) List, int. It is NOT possible to apply + 1 on a List of Integers
		 */
	    List<Integer> together = Stream.of(asList(1, 2), asList(3, 4)) // Stream of List<Integer>
	            .flatMap(numbers -> numbers.stream())
	            .map(integer -> integer + 1)
	            .collect(Collectors.toList());
	    assertEquals(asList(2, 3, 4, 5), together);
	}
	
	/*I have two maps m1 and m2 of type Map<Integer, String>, which has to be merged into 
	 * a single map Map<Integer, List<String>>, where values 
	 * of same keys in both the maps are collected into a List and put into a new Map.
	 * Map<Integer, List<String>> collated = 
    Stream.concat(m1.entrySet().stream(), m2.entrySet().stream())
          .collect(Collectors.groupingBy(Map.Entry::getKey,
                                         Collectors.mapping(Map.Entry::getValue,
                                                            Collectors.toList())));
	 */
	
@Test 

public void ListToMap() {
	/*
	 *  have a List<Item> collection. 
	 *  I need to convert it into Map<Integer, Item> The key of the map must be the index of the item in the collection
	 *  Map<Integer,Item> map = list.stream().collect(Collectors.toMap(i -> list.indexOf(i), i -> i));
	 */
	List<Item> list = new ArrayList<>();
	list.add(new Item("Apple"));
	list.add(new Item("Bat"));
	Map<Integer,Item> map = list.stream().collect(Collectors.toMap(i -> list.indexOf(i), i -> i));
	
	//You can create a Stream of the indices using an IntStream and then convert them to a Map
	/*
	 * Map<Integer,Item> map = 
    IntStream.range(0,items.size())
             .boxed()
             .collect(Collectors.toMap (i -> i, i -> items.get(i)));
	 */
	System.out.println(map.entrySet());
	
}
@Test 
public void findMax(){
	String[] array = new String[] {"1", "2", "3", "25"};
	// this method returns the max value in array 
	Arrays.stream(array).mapToInt(Integer::parseInt).max().orElse(0);
	
	// this method returns the maximum index 
	//System.out.println(Integer.valueOf(Collections.max(Arrays.asList(array)))); //.max returns 0 when empty array
	System.out.println(Arrays.stream(array).mapToInt(Integer::parseInt).max().orElse(0));
	/*
	 * Supposed that integers presented as Strings are all non-negative 
	 * and don't have trailing zeros, you don't need to parse them during the search.
	 *  Use a custom comparator, which first compares strings by length, then by value:
	 */
	Optional<String> max = Stream.of(array).max(comparingInt(String::length).thenComparing(naturalOrder()));
	int maxInt = Integer.parseInt(max.get());
	//System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
	System.out.println(maxInt);
}

@Test

public void findthestringwithlargestlowercaselettersfromlist() {
	List<String> strList = asList("getElementById", "htmlSpecialChars", "httpRequest");
	String maxOfLowercase = strList.stream()
            .max((o1, o2) -> {
                long lowerCount1 = o1.chars().filter(Character::isLowerCase).count();
                long lowerCount2 = o2.chars().filter(Character::isLowerCase).count();
                return Long.compare(lowerCount1, lowerCount2);
            }).get();
	System.out.println("&&&&&&&&&&&&&&&&&ddd&&&&&&&&&&&&&&&&&&");
	String maxOfLowercase2 = strList.stream()
	        .max(Comparator.comparingLong(o -> o.chars().filter(Character::isLowerCase).count()))
	        .get();
	System.out.println(maxOfLowercase2);
	
	
}
@Test
public void BudgetRateParametersTest(){
	
	Character[] budgetrateClasses =  new Character[] {'o','m','m','c','r'};
	
	List<Character>selectedrateClasses = Arrays.stream(budgetrateClasses).collect(Collectors.toList());
	Map<Character,Character>mapofRateclases= Arrays.stream(budgetrateClasses).collect(Collectors.toMap((Character k)->Character.toUpperCase(k),
            Function.identity(),
            (v1, v2) -> v1,
            TreeMap::new));
	System.out.println(selectedrateClasses);
	System.out.println("bund"+mapofRateclases);
	int n= 6;
	System.out.println(IntStream.rangeClosed(1,n).reduce((x, y)->x*y).getAsInt());
	
}
@Test
public void predicateTest() {
	Stream.of("hello","test").filter(str->str.startsWith("h")).forEach(System.out::println);
	
}
@Test
public void predicatest2() {
	Predicate<String> nullcheck = arg -> arg!=null;
	Predicate<String> emptyCheck = arg ->arg.length()>0;
	Predicate<String> nullandEmptyCheck= nullcheck.and(emptyCheck);
	String str= "hello";
	System.out.println(nullandEmptyCheck.test(str));
	
}
@Test 
public void testRemoveIf() {
	List<String>hello= new ArrayList<String>();
	hello.add("hi");
	hello.add("bye");
	hello.removeIf(str->!str.startsWith("h"));
	hello.forEach(System.out::println);// for each takes consumer 
	// the above was Predicate interface example which contains only one test method as long as signature atches 
	
	/*@FunctionalInterface
	public interface Predicate<T> {
	    boolean test(T t);
	    // other methods elided*/
	//}
	
}
// consumers are lazy they take an argument of type T and return nothing 
//@FunctionalInterface java.util.stream.Stream 
/*public interface Consumer<T> {
    void accept(T t);
        // the default andThen method elided
}
*/
@Test 
public void consumerTest() {
	Consumer<String> printupercase= str -> System.out.println(str.toUpperCase());
	String str="hello";
	printupercase.accept(str);// passing actual argiment to accept method
}
// now most important functional interface 
/*
 * @FunctionalInterface
public interface Function<T, R> {
    R apply(T t); takes generic type t and return r type any lamda which makes apply ethod can be substituted 
    
    // other methods elided
}
*/

 
	
    
}
