package map.to.obj;


import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Sample {
  public static void main(String[] args) {
    final List numbers1 = Arrays.asList(1, 2, 3, 4);
    final List numbers2 = Arrays.asList(10, 20, 30);

    IntStream.range(
          0, Math.min(numbers1.size(), numbers2.size()))
        .mapToObj(i -> numbers1.get(i) + numbers2.get(i))
        .forEach(System.out::println);
  }
  public class Main {
	  public static void main(String[] args) {
	    IntStream i = IntStream.of(6,5,7,1, 2, 3, 3);
	    Stream<String> d = i.mapToObj(n ->Integer.toBinaryString(n));
	    d.forEach(System.out::println);
	  }
	}
  List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
  String commaSeparatedNumbers = numbers.stream()
      .map(i -> i.toString())
      .collect(Collectors.joining(", "));
  This one can't be compiled:

  int[] numbers = {1, 2, 3, 4};
  String commaSeparatedNumbers = Arrays.stream(numbers)
      .map((Integer i) -> i.toString())
      .collect(Collectors.joining(", "));
}Arrays.stream(int[]) creates an IntStream, not a Stream<Integer>. So you need to call mapToObj instead of just map, when mapping an int to an object.

String commaSeparatedNumbers = Arrays.stream(numbers)
.mapToObj(i -> ((Integer) i).toString()) //i is an int, not an Integer
.collect(Collectors.joining(", "));
which you can also write:

String commaSeparatedNumbers = Arrays.stream(numbers)
.mapToObj(Integer::toString)
.collect(Collectors.joining(", "));
You would need to call boxed() before in order to get a Stream<Integer> (this is what Arrays.asList(...).stream() returns). Then just call map as you did in the first snippet.
long count = IntStream.rangeClosed(0,9) /* 0 <= A <= 9 */
.parallel()
.mapToObj(Integer::valueOf)
.flatMap(a -> 
        IntStream.rangeClosed(0,9) /* 0 <= B <= 9 */
        .mapToObj(Integer::valueOf)
        .flatMap(b ->
                IntStream.rangeClosed(0,9) /* 0 <= C <= 9 */
                .mapToObj(c -> (new Product()).A(a).B(b).C(c))
        )
 ).count();
System.out.println("Enjoy your Cartesian product." + count);
