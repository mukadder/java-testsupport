package com.alextheedom.negate;

import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by Developer on 17/11/2015.
 */
public class PredicateNegate
{

    public static <R> Predicate<R> not(Predicate<R> predicate) {
        return predicate.negate();
    }

    public static void main(String... args) {
        System.out.println(
                Stream.of(1, 2, 3, 4, 5, 6, 7).filter(not(c -> c % 2 == 0)).collect(toList())
        );
        System.out.println(
                Stream.of(1, 2, 3, 4, 5, 6, 7).filter(((Predicate<Integer>) c -> c % 2 == 0).negate()).collect(toList())
        );
    }
}
package com.alextheedom.negate;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by Developer on 17/11/2015.
 */
public class OptionThree {

    public static void main(String... args) {

        System.out.println(
                Stream.of(1, 2, 3, 4, 5, 6, 7).filter((c -> c % 2 != 0)).collect(toList())
        );
        System.out.println(
                Stream.of("Cat", "", "Dog")
                        .filter(str -> !str.isEmpty()).collect(toList())
        );
    }
}package com.alextheedom.negate;

import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by Developer on 17/11/2015.
 */
public class OptionTwo {

    public static <T> Predicate<T> predicate(Predicate<T> predicate) {
        return predicate;
    }

    public static void main(String... args) {
        System.out.println(
                Stream.of("Cat", "", "Dog")
                        .filter(predicate(String::isEmpty).negate())
                        .collect(toList())
        );

    }

}