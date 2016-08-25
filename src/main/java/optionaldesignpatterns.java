OPTIONAL PATTERNS
7 DECEMBER 2014 JOSÉ

Much has been said about Java 8 and about the way it will change the way we design applications and APIs. Of course the fisrt big thing is lambda expressions, followed by the Stream API.

Another element was introduced, the final class Optional, which also changes the way we can write API and applications, leading us to better and more fluent patterns. The purpose of this article is to detail the concept of optional, and show the available patterns to use optionals efficiently and elegantly. There are very powerful and very elegant way to mix optionals and streams, the aim of this article is to describe those patterns.


What is an Optional?

This concept is not particularly new, and already exists in other languages. This class was introduced to model the fact that a method may well not be able to return a value. In that case, the most common choice that is made is to return some kind of default value. For example, the call to map.get(key) returns null if the key that we pass as a parameter is not in the hash table. In fact, if the key is in the table associated with the value null, this method will also return null. So returning null is not the best solution, because it does not tell us if the key is present int he map or not.

It would be a mistake to think that the Optional is there only for that purpose: handle corner cases. In fact, we have been dealing with corner cases since Java was there, without optionals.  So we might wonder: do we really need optionals? But the way this class was designed authorizes new patterns which, combined with the patterns of the Stream API, become particularly simple.

Optionals are not an option

In fact this concept of “a result that does not exist” is required in many application cases. Let us see an example with the Stream API. Let us consider the simple calculation of a max. Since the Stream API exposes a max() method, taking a look at that point might prove interesting.

Let us write a Stream.max() call.

1
Stream<Integer> stream =
2
   Stream.of(1, 2, 3, 4) ;
3
stream.max(Comparator.naturalOrder()) ;
In this case, no worry, the result must be 4.

Let us consider another case, in which our stream is in fact empty. What is the value one should return?

We need to extend our example if we want to see the full problem. Suppose we have two streams, and let us write the following code.

1
Stream<Integer> stream1 =
2
   Stream.of(1, 2) ;
3
int max1 = stream1.max(Comparator.naturalOrder()) ;
4
 
5
Stream<Integer> stream2 =
6
   Stream.of(3, 4) ;
7
int max2 = stream2.max(Comparator.naturalOrder()) ;
Obviously the following property should hold:

1
int max = Integer.max(max1, max2) ;
2
 
3
Stream<Integer> stream3 =
4
   Stream.concat(stream1, stream2) ;
5
int max3 = stream3.max() ;
6
 
7
assert max == max3
If we merge two streams, the max of this stream should also be the max of the two max. We are doomed…

Can the max() method return null ? In fact the real question is the following: do we really want to have to handle null values in our data processing code? We do not know what to do with them, we will have to handle them as special cases, and to protect the rest of our code from them. Obviously the answer is in fact in the question: no.

Suppose that the max() method returns an int, or, in a more general way, the type on which the stream is built. What value should we choose in the case the stream on which we compute that max() is empty?

The first answer that will come to mind will probably be 0. Let us examine the following example.

1
int max1 =
2
   Stream.empty().max() ; // suppose max1 is 0
3
int max2 =
4
   Stream.of(-1, -2, -3).max() ; // max2 is -1
5
 
6
int max = Integer.max(max1, max2) ; // thus 0
7
 
8
Stream stream3 = Stream.concat(stream1, stream2) ;
9
int max3 = stream3.max() ; // stream3 is NOT empty!
10
                           // max3 is -1
11
 
12
assert max == max3 ; // nope, max is 0
Bad luck, the property “max of the max” does not work anymore, our method max() does not work. Stating that the max() method returns an int is in fact a bug in itself.

Why that? Because the default value that we have to choose for the max(), in other words, the max() of the empty set, has to be the identity element of the max operation. And the problem, precisely, is that the max operation has no identity element.

Optional to the rescue

The max of an empty set is undefined, and to choose a value will lead to inconsistencies if we are not careful. Finding a correct solution to this problem is important because the max is not the only operation that has no identity elemnt. The same goes for the min, and also for the average operation…

The choice that was made is to state that the max() method returns an instance of Optional, a new concept introduced in Java 8. Incidentally, the min() method also returns an optional, as well as the average() method, for the same reasons.

The type Optional has been introduced to deal with the fact that a value may not exist, which is different from saying that it is null.

Optional: first patterns

There are two categories of patterns that have been implemented on the Optional class.

For the first category, an instance of Optional is the same kind of an instance of a wrapper class (Long, Double, etc…), in which you might have no value.

So we have two methods: isPresent() and get() to handle that.

1
Optional<Integer> opt = ... ;
2
if (opt.isPresent()) {
3
   int value = opt.get() ; // there is a value
4
} else {
5
   // decide what to do
6
}
We have then two variants of this pattern. The first one proposes a default value, which is valid in the context of our application, that we can write in two ways.

1
Optional<Person> opt = ... ;
2
// 1st way, decide of a default value
3
Person p1 = opt.orElse(Person.DEFAULT_PERSON) ;
4
// 2nd way, the same, lazily built
5
Person p2 =
6
   opt.orElseGet(() -> Person.DEFAULT_PERSON) ;
This first variant uses the orElseGet() method, that takes a Supplier as a parameter. This method acts as a lazy constructor, that will build the result object only if needed. Nice pattern, easy to write, thanks to the use of lambda expressions.

And the second variant, throws an exception, lazily constructed with the same pattern.

1
Optional<Person> opt = ... ;
2
// lazy construction of the exception
3
Person p1 = opt.orElseThrow(
4
   PersonNonExistentException::new) ;
Optional: second patterns

First version

An optional can also be seen from a different point of view, that will lead us to much more interesting patterns.

First of all, the Optional class has the same kind of methods we have on Stream: map(), filter(), and ifPresent().

Let us build an example on a NewMath class, that, instead of throwing exceptions or returning NaN when a result cannot be computed, will return Optional. This example is taken from this excellent book by Cay Horstman [1].

1
public class NewMath {
2
 
3
   public static Optional<Double> sqrt(Double d) {
4
      return d > 0 ?
5
         Optional.of(Math.sqrt(d)) ;
6
         Optional.empty() ;
7
   }
8
 
9
   public static Optional<Double> inv(Double d) {
10
      return d != 0 ?
11
         Optional.of(1/d) ;
12
         Optional.empty() ;
13
   }
14
}
This class is built on a simple idea. Instead of adding special cases (exceptions, special values), it always return optional, that will be empty if a returned value cannot be computed. The difference might look tiny, it is in fact fundametal.

Suppose we want to process a list of doubles with those methods.

1
double [] doubles = ... ; // an array of doubles
2
List<Double> result = new ArrayList<>() ;
3
doubles
4
   .stream()
5
   .forEach(
6
      d -> NewMath
7
             .sqrt(d)
8
             .ifPresent(
9
                result::add
10
             )
11
   ) ;
At the end of those operations, all the doubles that are valid have generated a value in the list result. The other values have been silently discarded from that result.

Using this new concept of optional allows to get rid of the if-then-else pattern, leading to more simple and cleaner code. But also faster.

The following question is: we have computed here the square root, can we compute the inverse of the square root? In other words, how can we chain operations?

A special method from Optional does exactly what we want: flatMap(). This method takes an optional as a parameter, and returns another optional. It has been added precisely to chain method calls. Our code is then the following.

1
double [] doubles = ... ; // an array of doubles
2
List<Double> result = new ArrayList<>() ;
3
doubles
4
   .stream()
5
   .forEach(
6
      d -> NewMath
7
             .inv(d)
8
             .flatMap(NewMath::sqrt)
9
             .ifPresent(
10
                result::add
11
             )
12
   ) ;
Second version

This code is interesting, it does what we need, but it still has a drawback. The call to ifPresent() takes a list that is built outside of our processing. If we want to go parallel with this processing (and that should be easy in Java 8), we will need a concurrent list, not an ArrayList. It would be better if the API could handle that for us.

In fact, what we need is a collector, and call a map() method instead of forEach().

For that, we are going to transform our optional in a stream. Our optional might be empty, we are going to change it into a stream, that will hold either nothing, either one value.

Let us take a closer look to the function we used in our previous code.

1
Function<Double, Optional<Double>> f =
2
   d -> NewMath.inv(d)
3
           .flatMap(NewMath::sqrt) ;
It is in fact possible to map this Optional into an Optional<Stream>. This optional will have the same semantic as the previous one: it can me empty.

Since it is an optional, one can call its orElse() method, and return an empty stream in case this optional is itself empty.

1
Function<Double, Stream<Double>> invSqrt =
2
   d -> NewMath.inv(d)
3
         .flatMap(NewMath::sqrt)
4
         .map(Stream::of)
5
         .orElseGet(Stream::empty) ;
This function works internaly with optionals, but hides them from the external code. It returns a stream, empty if the processed double is invalid (here null or negative), or holding the computed result.

We just have to use that function to write our processing using streams.

1
List<Double> doubles = Arrays.asList(-1d, 0d, 1d) ;
2
 
3
doubles.stream().parallel()
4
      .flatMap(invSqrt)
5
      .collect(Collectors.toList()) ;
The resut we get is the one we expect, the two invalid values have been removed from the result, in a silent way.

1
[1]
This code is pure stream code, so we can use a parallel stream without any problem, as well as all the other methods of the Stream interface.
tionnalités de l’API Stream.

Conclusion

This second way of using optionals is with no doubt the most interesting than the first one, that consist in checking if there is a value in it, and taking it out. Here we write things in a fluent way, and we process all the data without errors or exceptions. We just remove the the invalid data from the input stream, without the need for if-then-else. A new Java 8 pattern, leveraging optionals, stream, and the flat-map methods.

References