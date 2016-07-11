package com.edu.bu;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * Files.lines reads a file and returns a Stream of its lines. But we want words, not lines. No problem. Stream.flatMap takes a function, that returns a Stream, to apply to each element. This gives us a mini-stream of words for each line. Then, flatMap flattens all those Streams into one big Stream, containing all the words in the file. In this case, we want to split the line on whitespace to form our words. Then we pass it along to String::toLowerCase so that we're doing a case-insensitive word count.

Now that we have a Stream of all the words in the file, we can start processing. What we want is a Map that maps each word to the number of occurrences. Collectors.toMap does this for us. The first argument is a function that should return the key in the map. In this case, the key is just the word, which describes the somewhat pointless looking word -> word. The second argument is a function that returns the value in the map. Here's where it gets tricky. We're using the three-argument version of Collectors.toMap, which handles collisions in the value function. The third argument is a function that will combine two colliding values to form a new value.

To sum up the number of occurrences of each word, we start with a value of 1. Here's what happens. Say the word "cat" appears 3 times in the input file. This call to Collectors.toMap will result in three mappings whose key is "cat", and whose value is 1. To get the word count, we want to add the three values (of 1 each) in the event of a collision. So we use Integer::sum to do this for us.

The hard part is done, but we still need to sort and print the results. Because collect is a terminal operation, we'll need a new stream to proceed. Calling stream() on the resulting Map's keySet will give us the stream we need.

To do the sorting, our comparison function should first check the word counts. If the words have the same count, then they should be sorted in alphabetical order. Otherwise, they should be sorted based on the number of occurrences.

Lastly, we print the sorted stream to the console to get the output.
 * @author mukadder
 *
 */
public class WordCount {
    public static void main(String...args) throws IOException {
        Files.lines(Paths.get(args[0]))
            .flatMap(line -> Stream.of(
                line.replaceAll("[-\\.,;!\\?]", " ")
                    .replaceAll("[\"'()\\[\\]=_\\-:]", "")
                    .split("\\s+")))
            .filter(str -> !str.isEmpty())
            .map(String::toLowerCase)
            .collect(Collectors.toMap(word -> word, word -> 1, Integer::sum))
            .entrySet()
            .stream()
            .sorted((a, b) -> a.getValue() == b.getValue() ? a.getKey().compareTo(b.getKey()) : b.getValue() - a.getValue())
            .forEach(System.out::println);
    }
}