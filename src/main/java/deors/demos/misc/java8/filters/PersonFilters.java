
package deors.demos.misc.java8.filters;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import deors.demos.misc.java8.filters.Person.Gender;
import deors.demos.misc.java8.filters.Person.Region;

public final class PersonFilters {

    static String result1;
    static String result2;
    static Optional<Person> result3;

    private PersonFilters() {
        super();
    }

    public static void main(String[] args) {

        List<Person> persons = fillData();

        System.out.println("select people with more than 40 years");
        persons.stream().
            filter(p -> p.birthDate.isBefore(LocalDate.now().minusYears(40))).
            forEach(System.out::println);
        result1 = persons.stream().
            filter(p -> p.birthDate.isBefore(LocalDate.now().minusYears(40))).
            map(p -> p.toString()).
            collect(Collectors.joining(","));

        System.out.println();
        System.out.println("select males born in second half of year");
        persons.stream().
            filter(p -> p.gender == Gender.MALE).
            filter(p -> p.birthDate.getMonthValue() > 6).
            forEach(System.out::println);
        result2 = persons.stream().
            filter(p -> p.gender == Gender.MALE).
            filter(p -> p.birthDate.getMonthValue() > 6).
            map(p -> p.toString()).
            collect(Collectors.joining(","));

        System.out.println();
        System.out.println("select the largest salary for people in LATAM with last name starting with R");
        persons.stream().
            filter(p -> p.region == Region.LATAM).
            filter(p -> p.last.charAt(0) == 'R').
            max((p, p2) -> p.salary - p2.salary).
            ifPresent(System.out::println);
        result3 = persons.stream().
            filter(p -> p.region == Region.LATAM).
            filter(p -> p.last.charAt(0) == 'R').
            max((p, p2) -> p.salary - p2.salary);
    }

    static List<Person> fillData() {

        List<Person> persons = new ArrayList<>();

        persons.add(new Person(
            "Jorge", "Hidalgo", LocalDate.of(1974, 10, 15), Gender.MALE, Region.EUROPE, 25_000));
        persons.add(new Person(
            "Angie", "López", LocalDate.of(1975, 5, 12), Gender.FEMALE, Region.LATAM, 24_500));
        persons.add(new Person(
            "George", "Harrison", LocalDate.of(1974, 2, 4), Gender.MALE, Region.NA, 42_000));
        persons.add(new Person(
            "John", "Smith", LocalDate.of(1980, 10, 8), Gender.MALE, Region.UKI, 50_000));
        persons.add(new Person(
            "Jane", "Doe", LocalDate.of(1980, 2, 28), Gender.FEMALE, Region.PACIFIC, 8_500));
        persons.add(new Person(
            "Lionel", "Ramírez", LocalDate.of(1975, 5, 15), Gender.MALE, Region.LATAM, 23_000));
        persons.add(new Person(
            "Scarlett", "O'Hara", LocalDate.of(1969, 6, 2), Gender.FEMALE, Region.NA, 45_000));
        persons.add(new Person(
            "Priya", "Krishnan", LocalDate.of(1972, 12, 5), Gender.FEMALE, Region.ASIA, 12_500));
        persons.add(new Person(
            "Karla", "Rodríguez", LocalDate.of(1975, 4, 18), Gender.FEMALE, Region.LATAM, 9_000));

        return persons;
    }
}