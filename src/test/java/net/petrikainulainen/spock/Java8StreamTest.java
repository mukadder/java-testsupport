package net.petrikainulainen.spock;
import java.util.OptionalDouble;

import com.bu.edu.MostPopular;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import static java.util.stream.Collectors.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Calendar;
import java.util.AbstractMap;
import org.junit.Test;
import static java.util.Arrays.asList;
import static java.util.Map.Entry;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
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
@Test
public void functionTest() {
	Arrays.stream("-4,-1,2".split(",")).map(Integer::parseInt).map(i->  ( i< 0 )? -i: i).forEach(System.out::println);
	
}
@Test
public void coreFunctionTest() {
	Function<String, Integer> strLenghth = str ->str.length();
	System.out.println(strLenghth.apply("padu"));
	// you can also combine functions 
	Function<String,Integer> parseInt= Integer::parseInt;
	Function<Integer,Integer>absInt = Math::abs;
	Function<String,Integer>parseAbsInt= parseInt.andThen(absInt);
	/*What is the difference between andThen() and compose() methods in Function interface? The andThen() method applies the passed argument after calling the current Function (as in this example). The compose() method calls the argument before calling the current Function, as in:

Function<String, Integer> parseAndAbsInt = absInt.compose(parseInt);*/
	
}

@Test 
public void identityTest() {
	Arrays.stream("4, -9, 16".split(", ")).map(Integer::parseInt).map(str->str*str)
    .map(Function.identity())
    .forEach(System.out::println);

}
/*
 * @FunctionalInterface
public interface Supplier<T> {
    T get();
    // no other methods in this interface
}
 */
 @Test
 public void testSupplier(){
Random random = new Random();
Stream.generate(random::nextBoolean)
         .limit(22)
         .forEach(System.out::println);
Supplier<String>currentdaterortime =()-> LocalDateTime.now().toString();
System.out.println(currentdaterortime.get());
}
 /*
  * @FunctionalInterface
public interface BiFunction<T, U, R> {
    R apply(T t, U u);
    // other methods elided
} bi functional interface takes two arguments anre return of type r
  */
 @Test
 public void biFunctionTest() {
	 
	 BiFunction<String,String,String> concatStr= (x,y) -> x+y;
	 System.out.println(concatStr.apply("hello", "India"));
	 BiFunction<Double,Double,Integer> doubleTree= Double::compare;
	 System.out.println(doubleTree.apply(10.0, 10.0));
	 /*
	  * BiFunction<T, U, Boolean> how does bi predicate behaves two difeerent types of arguments and return type boolean 
	  * Note still called Bifunction but named as bi predicate
	  * 
	  */
	 BiPredicate<List<Integer> ,Integer>listcontains=  List::contains;
	 
	 List aslist= Arrays.asList(23,44,45);
	 System.out.println(listcontains.test(aslist,3));
	 /*
	  * there is biconsumeer interface also which takes two arguments and return nothing 
	  * 
	  */
	// BiConsumer<List<Integer> ,Integer>listadd = List::add;
	// listadd.accept(aslist,23);
	 /*
	  * The UnaryOperator InterfaceUnaryOperator is a 
	  * functional interface and it extends Function interface,
	  *  and you can use the apply() method declared in the Function interface; further, 
	  *  it inherits the default functions compose() and andThen() from the Function interface. 
	  *  Similar to UnaryOperator that extends Function interface, there is a BinaryOperator that extends BiFunction interface.


	  */
	 List<Integer> ell = Arrays.asList(-11, 22, 33, -44, 55);
	 System.out.println("Before: " + ell);
	 ell.replaceAll(Math::abs);
	     System.out.println("After: " + ell);
	 
	 
 }
 // The stream API
 
 @Test
 public void streamMapPeekTest() {
	 long count = Stream.of(1,2,3).map(a->a*a).peek(i->System.out.printf("%d",i)).count();
	 // now the stream has to be of type double to do summing operations 
	 DoubleStream.of(1.0,2.0).map(Math::sqrt).peek(System.out::println).sum();
	 //Average temperatures in Concordia, Antarctica in a week in October 2015
     boolean anyMatch
             = IntStream.of(-56, -57, -55, -52, -48, -51, -49).anyMatch(temp -> temp > 0);
     System.out.println("anyMatch(temp -> temp > 0): " + anyMatch);

     boolean allMatch
             = IntStream.of(-56, -57, -55, -52, -48, -51, -49).allMatch(temp -> temp > 0);
     System.out.println("allMatch(temp -> temp > 0): " + allMatch);

     boolean noneMatch
             = IntStream.of(-56, -57, -55, -52, -48, -51, -49).noneMatch(temp -> temp > 0);
     System.out.println("noneMatch(temp -> temp > 0): " + noneMatch);
     Method[] methods = Stream.class.getMethods();
     Optional<String> methodName = Arrays.stream(methods)
             .map(method -> method.getName())
             .filter(name -> name.endsWith("Match"))
             .sorted()
             .findFirst();
     System.out.println("Result: " + methodName.orElse("No suitable method found"));
     OptionalDouble temperature = DoubleStream.of(-10.1, -5.4, 6.0, -3.4, 8.9, 2.2)
             .filter(temp -> temp > 0)
             .findFirst();
     System.out.println("First matching temperature > 0 is " + temperature.getAsDouble());
     selectHighestTemperature(Stream.of(24.5, 23.6, 27.9, 21.1, 23.5, 25.5, 28.3));
     Optional<String> string = Optional.of("  abracadabra  ");
     string.map(String::trim).ifPresent(System.out::println);
     Optional<String> string2 = Optional.ofNullable(null);
     System.out.println(string.map(String::length).orElse(-1));
     Optional<String> string3 = Optional.ofNullable(null);  
     System.out.println(string.map(String::length).orElseThrow(IllegalArgumentException::new));
     String[] string33 = "you never know what you have until you clean your room".split(" ");
     //he min() and max() methods take a Comparator object as the argument and return an Optional<T>
     //String::compareTo compares two strings lexicographically,
     
     System.out.println(Arrays.stream(string33).min(String::compareTo).get());
     Comparator<String> lengthCompare = (str1, str2) -> str1.length() - str2.length();
     System.out.println(Arrays.stream(string33).min(lengthCompare).get());
     String limerick = "There was a young lady named Bright " +
             "who traveled much faster than light " +
             "She set out one day " +
             "in a relative way " +
             "and came back the previous night ";

IntSummaryStatistics wordStatistics =
   Pattern.compile(" ")
   .splitAsStream(limerick)
   .mapToInt(word -> word.length())
   .summaryStatistics();

System.out.printf(" Number of words = %d \n Sum of the length of the words = %d \n" +
             " Minimum word size = %d \n Maximum word size %d \n " +
             " Average word size = %f \n", wordStatistics.getCount(),
              wordStatistics.getSum(), wordStatistics.getMin(),
              wordStatistics.getMax(), wordStatistics.getAverage());
//factorial of 5
System.out.println(IntStream.rangeClosed(1, 5).reduce((x, y) -> (x * y)).getAsInt());
//prints: 120
//Here is a program that sorts strings with lexicographical comparison (Listing 6-7).


List words =
Arrays.asList("follow your heart but take your brain with you".split(" "));
words.stream().distinct().sorted().forEach(System.out::println);
List words2 =
Arrays.asList("follow your heart but take your brain with you".split(" "));
Comparator<String> lengthCompare2 = (str1, str2) -> str1.length() - str2.length();
words2.stream().distinct().sorted(lengthCompare).forEach(System.out::println);
/*
 * great class to rescue Save results to a collection using the 
 * collect method and group/partition data using the Collectors class
 */
String frenchCounting = "un:deux:trois:quatre";
List gmailList = Pattern.compile(":")
        .splitAsStream(frenchCounting)
        .collect(Collectors.toList());
gmailList.forEach(System.out::println);
//The collect() method in Stream takes a Collector as an argument:
Map<String, Integer> nameLength = Stream.of("Arnold", "Alois", "Schwarzenegger")
.collect(Collectors.toMap(name -> name, name -> name.length()));
nameLength.forEach((name, len) -> System.out.printf("%s - %d \n", name, len));
//We can simplify it by passing Function.identity() instead, as in:

//Collectors.toMap(Function.identity(), name -> name.length());
String []roseQuote = "a rose is a rose is a rose".split(" ");
//Set words4 = Arrays.stream(roseQuote).collect(Collectors.toCollection(TreeSet::new));
//words4.forEach(System.out::println);
String []roseQuote2 = "a rose is a rose is a rose".split(" ");
Set words55 = Arrays.stream(roseQuote2).collect(Collectors.toSet());
words55.forEach(System.out::println);

}
 public void groupinByTest() {
	 
	 String []string= "you never know what you have until you clean your room".split(" ");
     Stream<String> distinctWords = Arrays.stream(string).distinct();
     Map<Integer, List<String>> wordGroups =
                       distinctWords.collect(Collectors.groupingBy(String::length));
     wordGroups.forEach(
             (count, words) -> {
                      System.out.printf("word(s) of length %d %n", count);
                             words.forEach(System.out::println);
             });
     /*
      * The groupingBy() method in Collectors class takes a Function as an argument. It uses the result of the function to return a Map. 
      * The Map object consists of the values returned by the Function and the List of elements that matched.
      */
     String []string2= "you never know what you have until you clean your room".split(" ");
     Stream<String> distinctWords2 = Arrays.stream(string2).distinct();
     Map<Boolean, List<String>> wordBlocks =
             distinctWords2.collect(Collectors.partitioningBy(str -> str.length() > 4));

     System.out.println("Short words (len <= 4): " + wordBlocks.get(false));
     System.out.println("Long words (len > 4): " + wordBlocks.get(true));
     /*
      * How are the methods groupingBy() and partitioningBy() different? The groupingBy() method takes 
      * a classification function (of type Function) and returns the input elements and their matching 
      * entries based on the classification function (and organizes the results in a Map<K, List<T>>). 
      * The partitioningBy() method takes a Predicate as the argument and classifies the entries as true and false 
      * based on the given Predicate (and organizes the results in a Map<Boolean, List<T>>).
      */
 }

 
 public static void selectHighestTemperature(Stream<Double> temperatures) {
	    System.out.println(temperatures.max(Double::compareTo));
	    //The max() method in Stream takes a Comparator as an argument and returns an Optional<T>:
	    //To get the value from Optional, you can use isPresent() and get() methods, as in:
	}
 public static void selectHighestTemperature2(Stream<Double> temperatures) {
	    Optional<Double> max = temperatures.max(Double::compareTo);
	    if(max.isPresent()) {
	        System.out.println(max.get());
	    }
	    //  or max.ifPresent(System.out::println); functional way
	}
 
 public void flatmapTest(){
	 //Using flatMap Method in Stream
	 String []string= "you never know what you have until you clean your room".split(" ");
     Arrays.stream(string)
             .flatMap(word -> Arrays.stream(word.split("")))
             .distinct()
             .forEach(System.out::print);
     List<List<Integer>> intsOfInts = Arrays.asList(
             Arrays.asList(1, 3, 5),
             Arrays.asList(2, 4));
intsOfInts.stream()
.flatMap(ints -> ints.stream())
.sorted()
.map(i -> i * i)
.forEach(System.out::println);

 
 }
 
 //Using the Java SE 8 Date/Time API
 /*
  * The new Java date and time API is provided in the java.time package. 
  * This new API in Java 8 replaces the older classes supporting date- and time-related functionality
  *  such as the Date, Calendar, and TimeZone classes provided as part of the java.util package.
  *  LocalDate is represented in the ISO-8601 calendar system in a year-month-day format (YYYY-MM-DD)
  */
 
 @Test
 public void testDateandTime() {
	LocalDate today =  LocalDate.now();
	System.out.println("Today's date is^^^^^^^^: " + today);
	LocalDate newYear2016 = LocalDate.of(2016, 1, 1);
	System.out.println("New year 2016: " + newYear2016);
	//LocalDate valentinesDay = LocalDate.of(2016, 14, 2);
	//System.out.println("Valentine's day is on: " + valentinesDay);
	// the above code will spit out error as month is 14 so use the enum 
	LocalDate valentinesDay = LocalDate.of(2016, Month.FEBRUARY, 14);
	System.out.println("Valentine's day is on: " + valentinesDay);
	long visaValidityDays = 180L;
	LocalDate currDate = LocalDate.now();
	System.out.println("My Visa expires on: " + currDate.plusDays(visaValidityDays));
	/*
	 * The java.time.LocalTime class is similar to LocalDate except that LocalTime 
	 * represents time without dates or time zones. The time is in the ISO-8601 calendar system format: HH:MM:SS.nanosecond. 
	 * Both LocalTime and LocalDate use the system clock and the default time zone.
	 * 
	 */
	LocalTime currTime = LocalTime.now();
	System.out.println("Current time is: " + currTime);
	System.out.println(LocalTime.of(18,30));
	long hours = 6;
	long minutes = 30;
	LocalTime currTime2 = LocalTime.now();
	System.out.println("Current time is: " + currTime2);
	System.out.println("My meeting is at: " + currTime2.plusHours(hours).plusMinutes(minutes));
	LocalDateTime currDateTime = LocalDateTime.now();
	System.out.println("Today's date and current time is: " + currDateTime);
	LocalDateTime christmas = LocalDateTime.of(2015, 12, 25, 0, 0);
	LocalDateTime newYear = LocalDateTime.of(2016, 1, 1, 0, 0);
	System.out.println("New Year 2016 comes after Christmas 2015? "+newYear.isAfter(christmas));
	LocalDateTime dateTime = LocalDateTime.now();
	System.out.println("Today's date and current time: " + dateTime);
	System.out.println("The date component is:  " + dateTime.toLocalDate());
	System.out.println("The time component is: " + dateTime.toLocalTime());
	// now using instant class 
	// prints the current timestamp with UTC as time zone
    Instant currTimeStamp = Instant.now();
    System.out.println("Instant timestamp is: "+ currTimeStamp);

    // prints the number of seconds as Unix timestamp from epoch time
    System.out.println("Number of seconds elapsed: " + currTimeStamp.getEpochSecond());

    // prints the Unix timestamp in milliseconds
    System.out.println("Number of milliseconds elapsed: " + currTimeStamp.toEpochMilli());
    //What is the difference between LocalDateTime and Instant? Here is an example that
    LocalDateTime localDateTime = LocalDateTime.now();
    Instant instant = Instant.now();
    System.out.println("LocalDateTime is: " + localDateTime + " \nInstant is: " + instant);
    //use of period class 
    LocalDate manufacturingDate = LocalDate.of(2016, Month.JANUARY, 1);
    LocalDate expiryDate = LocalDate.of(2018, Month.JULY, 18);

    Period expiry = Period.between(manufacturingDate, expiryDate);
    System.out.printf("Medicine will expire in: %d years, %d months, and %d days (%s)\n",
                    expiry.getYears(), expiry.getMonths(), expiry.getDays(), expiry);
    //Using the Duration Class
    //The Duration class represents time in terms of hours, minutes, seconds, and so on.
    //Say you want to wish your best friend Becky a happy birthday at midnight tonight. Here is how you can find out how many hours to go:
    LocalDateTime comingMidnight =
            LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIDNIGHT);
    LocalDateTime now = LocalDateTime.now();

    Duration between = Duration.between(now, comingMidnight);
    System.out.println(between);
    //Using the ZoneId Class
    System.out.println("My zone id is: " + ZoneId.systemDefault());
    Set<String> zones = ZoneId.getAvailableZoneIds();
    System.out.println("Number of available time zones is: " + zones.size());
    zones.forEach(System.out::println);
    ZoneId myZone = ZoneId.of("Asia/Kolkata");
    LocalDateTime dateTime2 = LocalDateTime.now();
    ZonedDateTime zonedDateTime = dateTime2.atZone(myZone);
    ZoneOffset zoneOffset = zonedDateTime.getOffset();
    System.out.println(zoneOffset);

 }
 @Test
 public void callSingaporeTimezone() {
	 ZoneId singaporeZone = ZoneId.of("Asia/Singapore");
     ZonedDateTime dateTimeInSingapore = ZonedDateTime.of(
     LocalDateTime.of(2016, Month.JANUARY, 1, 6, 0), singaporeZone);

     ZoneId aucklandZone = ZoneId.of("Pacific/Auckland");
     ZonedDateTime sameDateTimeInAuckland =
                 dateTimeInSingapore.withZoneSameInstant(aucklandZone);

     Duration timeDifference = Duration.between(
                             dateTimeInSingapore.toLocalTime(),
                             sameDateTimeInAuckland.toLocalTime());

     System.out.printf("Time difference between %s and %s zones is %d hours",
                 singaporeZone, aucklandZone, timeDifference.toHours());
 }
 public static class Multiplication
 {
     public static IntStream getTable(int i)
     {
         return IntStream.rangeClosed(1, 10).map(j -> i * j);
     }
 }
  
 @Test
 
	public void StreamTest() {
		IntStream.rangeClosed(1, 10).forEach(
				i -> IntStream.rangeClosed(1, 10).forEach(j -> System.out.println(i + " * " + j + " = " + i * j)));
		//second example
		int[] a = IntStream.rangeClosed(1, 10).flatMap(Multiplication::getTable).toArray();
		int i =10;
		System.out.println(IntStream.rangeClosed(1, 10).map(j -> i * j));

		Arrays.stream(a).forEach(System.out::println);
	}
 @Test 
 public void intTest() {
	 List<String> x = Arrays.asList("1","2","3");
     List<String> y = Arrays.asList("a","b","c");
     x.stream().flatMap( a -> y.stream().map(b -> (a+ ""  + b) )).forEach(System.out::println);
     List<String> list1 = Arrays.asList("AAA","BBB");
     List<String> list2 = Arrays.asList("CCC","DDD");
     Stream.of(list1,list2).flatMap(list -> list.stream()).forEach(s->System.out.println(s));
    
    	    ArrayList<String> l = new ArrayList<>();
    	    l.add("AB");
    	    l.add("A");
    	    l.add("AA");
    	    l.forEach(m -> m = "b" + m);
    	    System.out.println(l);
    	    List<String> l2 = new ArrayList<>(Arrays.asList("AB","A","AA"));
    	    l2 = l2.stream().map(w -> "b" + w).collect(Collectors.toList());
    	    System.out.println(l2);
 }
 /*
  * Creating Optional Objects
  * Optional<String> empty = Optional.empty();
 Optional<String> nonEmptyOptional = Optional.of("abracadabra");Optional Stream
 states.stream().forEach( state -> {
    cities.stream().filter( city -> state.containsPoint( city.getLocation() ) ).forEach( city -> {
        System.out.printf( "%30s is part of %-30s\n", city.getName(), state.getName() );
    } );
} );


You can also consider Optional as a stream that can have zero elements or one element. 
So you can apply methods such as map(), filter(), and flatMap() operations on this stream!
  */
 
 // concurreny is your friend
 
 @Test
 public void StringReplacement(){
	 Map<String, String> map = new HashMap<>(); 
	 map.put("ABC", "123");
	 final List<String> msg = Arrays.asList("helloABC");
	 map.forEach((key, value) -> msg.set(0, msg.get(0).replace(key, value)));
	 String test = msg.get(0);
	 System.out.println("heeeeeeeeeeeee"+test);
	 Map<String, Integer> words = new HashMap<>();
	 words.put("hello", 3);
	 words.put("world", 4);
	 // it add s to the map as key and updated the value by one 
	 words.computeIfPresent("hello", (k, v) -> v + 1);
	 System.out.println("IIIIIIII"+words.get("hello"));
 }
 @Test
	public void findMaxinMap() {
		final Map<LocalDate, Integer> foobar = new TreeMap<>();

		// Fill a date -> int map with 12 random ints between 0 and 100,
		new Random(System.currentTimeMillis()).ints(0, 100).limit(12)
				.forEach(value -> foobar.put(LocalDate.now().withMonth(foobar.size() + 1), value));
		// print them for verbosity
		foobar.entrySet().forEach(System.out::println);
		// get the maximum
		Map.Entry<LocalDate, Integer> max = foobar
				// from all entries
				.entrySet()
				// stream them
				.stream()
				// max, obviously
				.max(
						// this one is cool. It generates
						// Map.Entry comparators by delegating to another
						// comparator, exists also for keys
						Map.Entry.comparingByValue(Integer::compareTo))
				// Get the optional (optional because the map can be empty)
				.get();
		System.out.println("Max is " + max);
	}
 
 @Test
 public void groupingByList() {
	 Map<String, Long> m1 = new HashMap<>();
	    m1.put("A", 1l);
	    m1.put("B", 100l);

	    Map<String, Long> m2 = new HashMap<>();
	    m2.put("A", 10l);
	    m2.put("B", 20l);
	    m2.put("C", 100l);

	    List<Map<String, Long>> beforeFormatting = new ArrayList<>();
	    beforeFormatting.add(m1);
	    beforeFormatting.add(m2);

	    Map<String, List<Long>> afterFormatting =
	        beforeFormatting.stream()
	                        .flatMap(m -> m.entrySet().stream())
	                        .collect(groupingBy(Map.Entry::getKey, mapping(Map.Entry::getValue, toList())));
	    //You need to flatMap the entry set of each Map and 
	    //collect that Stream by grouping by the key and mapping each element to its value.

	    System.out.println(afterFormatting); // prints {A=[1, 10], B=[100, 20], C=[100]}
	    
	    //lets start we hava
	    /*
	     * Map<String, List<Employee>> result = new HashMap<>();
for (Employee e : employees) {
  String city = e.getCity();
  List<Employee> empsInCity = result.get(city);
  if (empsInCity == null) {
    empsInCity = new ArrayList<>();
    result.put(city, empsInCity);
  }
  empsInCity.add(e);
  or 
  Map<String, List<Employee>> employeesByCity =
  employees.stream().collect(groupingBy(Employee::getCity));
  //{New York=[Charles], Hong Kong=[Dorothy], London=[Alice, Bob]}
//Map<String, Long> numEmployeesByCity =
  employees.stream().collect(groupingBy(Employee::getCity, counting()));
  {New York=1, Hong Kong=1, London=2}
  Map<String, Double> avgSalesByCity =
  employees.stream().collect(groupingBy(Employee::getCity,
                               averagingInt(Employee::getNumSales)));
                               {New York=160.0, Hong Kong=190.0, London=175.0}
  Partitioning

Partitioning is a special kind of grouping, in which the resultant map contains at most two different groups - one for true and one for false. For instance, if you want to find out who your best employees are, you can partition them into those who made more than N sales and those who didn't, using the partitioningBy collector:

Map<Boolean, List<Employee>> partitioned =
  employees.stream().collect(partitioningBy(e -> e.getNumSales() > 150));
  Partitioning

Partitioning is a special kind of grouping, in which the resultant map contains at most two different groups - one for true and one for false. For instance, if you want to find out who your best employees are, you can partition them into those who made more than N sales and those who didn't, using the partitioningBy collector:

Map<Boolean, List<Employee>> partitioned =
  employees.stream().collect(partitioningBy(e -> e.getNumSales() > 150));
  {false=[Bob], true=[Alice, Charles, Dorothy]}
  Map<Boolean, Map<String, Long>> result =
  employees.stream().collect(partitioningBy(e -> e.getNumSales() > 150,
                               groupingBy(Employee::getCity, counting())));
                               {false={London=1}, true={New York=1, Hong Kong=1, London=1}}
}
Accumulator

The job of the accumulator() is to return a function which performs
 the reduction operation. It accepts two arguments. First one being the mutable result container (accumulator) and
 the second one the stream element that should be folded into the result container.
	     */
	    
 }
 
 @Test 
 public void Testjavacollections(){
	 List<Integer> integers = Lists.newArrayList(1, 1, 2, 2, 2, 3, 4, 5, 5);
     List<Character> characters = Lists.newArrayList('a', 'b', 'c', 'c', 'c', 'd');

     showMostPopular(integers);
     showMostPopular(characters);
 }

 private static <T> void showMostPopular(List<T> list) {
     Optional<T> o = list.stream()
             .collect(new MostPopular<>());
     //referhttp://blog.radoszewski.pl/programming/java/2015/07/31/custom-java-8-collectors.html

     System.out.println("Most popular element in [" + StringUtils.join(list, ",") + "]: ");
     o.ifPresent(System.out::println);
 }
 private static List<Integer> unaryOperatorFun(UnaryOperator<Integer> unaryOpt, List<Integer> list){
     List<Integer> uniList = new ArrayList<>();
     list.forEach(i->uniList.add(unaryOpt.apply(i))); 
     return uniList;
  }
 
 @Test
 //java.util.function.UnaryOperator is a java 8 functional interface that extends java.util.function
 public void testUnioperator(){
	 //UnaryOperator can be used as lambda expression to pass as an argument
	 //While defining UnaryOperator, we need to define Function.apply(Object) where Function will be the instance of UnaryOperator
	 List<Integer> list = Arrays.asList(10,20,30,40,50);
	 //caller of the API can pass in a lambda expression in place of an implementation of the interface.
     unaryOperatorFun(i->i*i,list).forEach(x->System.out.println(x));        
 }
 private static List<String> binaryOperatorFun(BinaryOperator<String> binaryOpt, Map<String,String> map){
     List<String> biList = new ArrayList<>();
     map.forEach((s1,s2)->biList.add(binaryOpt.apply(s1,s2))); 
     return biList;
  }
 @Test 
 //java.util.function.BinaryOperator is a functional interface that can be assigned as lambda expression. 
 public void testBinaryOperator() {
	 //BinaryOperator extends java.util.function.BiFunction. It accepts two operands of the same type and process it and then returns results of the same type as operands
	 Map<String,String> map = new HashMap<>();
     map.put("X", "A");
     map.put("Y", "B");
     map.put("Z", "C");
     binaryOperatorFun((s1,s2)-> s1+"-"+s2,map).forEach(x->System.out.println(x)); 
     //X-A
     //Y-B
    // Z-C
 }
 @Test
 public void TestMaxyByMinyBinaryOp(){
	 Student12 s1 = new Student12("Shyam", 22,"A");
	 Student12 s2 = new Student12("Ram",23,"A");
	 Student12 s3 = new Student12("Mohan",22,"B");
	 Student12 s4 = new Student12("Ramesh",21,"B");
     List<Student12> list = Arrays.asList(s1,s2,s3,s4);
     Comparator<Student12> ageComparator = Comparator.comparing(Student12::getAge); 
     //Using BinaryOperator.maxBy        
     System.out.println("---BinaryOperator.maxBy---");
     Map<String, Optional<Student12>> eldestByClass = list.stream().collect(Collectors.groupingBy(Student12::getClassName, 
             Collectors.reducing(BinaryOperator.maxBy(ageComparator))));
     eldestByClass.forEach((k,v)->System.out.println("Class:"+k+" Age:"+
             ((Optional<Student12>)v).get().getAge()+" Name:"+((Optional<Student12>)v).get().getName()));
     
     //Using BinaryOperator.minBy        
     System.out.println("---BinaryOperator.minBy---");
     Map<String, Optional<Student12>> youngestByClass = list.stream().collect(Collectors.groupingBy(Student12::getClassName, 
             Collectors.reducing(BinaryOperator.minBy(ageComparator))));
     youngestByClass.forEach((k,v)->System.out.println("Class:"+k+" Age:"+
             ((Optional<Student12>)v).get().getAge()+" Name:"+((Optional<Student12>)v).get().getName()));
 } 
 
 
 /*
  * // Define Lambda Functions
  19     Function<Person, String> shortWestern = p -> {
  20         return "\nName: " + p.getGivenName() + " " + p.getSurName() + "\n" +
  21         "EMail: " + p.getEmail() + "\n" +
  22         "Phone: " + p.getPhone(); 
  23     };now p is the argument it acts on and function.apply which will take this argument will
   execute the code  so all is based upon
  what parm is passesd it executes the function as long as the signature of function is same it will be used multiple times
  that means where ever  function is expected a lambda is passed in as argument Function interface is 
  uses in cases where you want to encapsulate some code into a method which accepts some value as an input parameter and 
  then returns another value after performing required operations on the input.public class FunctionDemo {

//API which accepts an implementation of
//Function interface
static void modifyTheValue(int valueToBeOperated,
Function<Integer, Integer> function){

int newValue = function.apply(valueToBeOperated);
/*
* Do some operations using the new value.System.out.println(newValue);
* modifyTheValue(myNumber, val-> val * 10);
modifyTheValue(myNumber, val-> val – 100);
*/

 
 }
 
 
 
 
	 
 
 /*
  * now search data with the stream use method ending in match or starting find strarting methods anyMatch(), allMatch(), and noneMatch()
  *  if you are looking for elements in the stream that matches the given condition they return boolean value
  *  methods like find first and findany return Optional object math types take predicate 
  *  Informally, a functional interface is one whose type can be used for a method parameter when a lambda is to be supplied as the actual argument. 
  *  Java 8 treats lambdas as an instance of an interface type
  *  caller of the API can pass in a lambda expression in place of an implementation of the interface.
  */
    


