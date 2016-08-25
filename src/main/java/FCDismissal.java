import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
/*
 * Today I came to the conclusion that I have to change my 
 * fitness center. (The current one is great, but I pay for features 
 * I don’t actually use). I checked the contract to find out the period of notice: 
 * I signed the contract on 27-Feb-2012 for 52 weeks. After the first 52 weeks the contract 
 * is automatically extended by 28 weeks. The period of notice is 12 weeks before the end of
 *  each extension time. Finding out the period of notice by hand is quite cumbersome, 
 *  so I’ve fired up my Java-IDE and played with the new Java 8 Stream (and Date) API. Here’s the result:
 *  The output of the main method is 2015-08-10, so I have to dismiss the contract before this date.

Let’s dig into the code: The iterate method creates an infinite sequential Stream with a seed and a function for the next element. In my case the seed is the date after the initial 52 weeks and a method reference to extendContract.

Each date is mapped to its corresponding period of notice.

1
.map(date -> date.minusWeeks(12))
After that I filter out dates in the past using the filter method.

1
.filter(date -> date.isAfter(LocalDate.now()))
An infinite sequential Stream has to be bounded using a so called “Short-circuiting operation”. findFirst terminates the stream and returns an Optional, which will be printed out when present:

1
nextNoticeDate.ifPresent(System.out::println);
 */
public class FCDismissal {
 
public static void main(String[] args) {
  LocalDate signDate = LocalDate.of(2012,02,27);
  LocalDate firstYear = signDate.plusWeeks(52);
 
  Optional<LocalDate> nextNoticeDate = 
  Stream.iterate(firstYear, 
                 FCDismissal::extendContract)
    .map(date -> date.minusWeeks(12))
    .filter(date -> date.isAfter(LocalDate.now()))
    .findFirst();
  List list= new Arrays.asList("a","b");
  ((Object) list.stream()
  .mapToInt(i -> Integer.parseInt((String) i)))
  .takeWhile(i -> i < 71)
  .forEach(System.out::println);
 
  nextNoticeDate.ifPresent(System.out::println);
}
 
static LocalDate extendContract(LocalDate date) {
  return date.plusWeeks(28);
}
 
}