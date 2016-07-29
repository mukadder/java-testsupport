

package deors.demos.misc.java8.filters;

import java.time.LocalDate;

public class Person {

    String first;
    String last;
    LocalDate birthDate;
    Gender gender;
    Region region;
    Integer salary;

    public Person(String first, String last, LocalDate birthDate, Gender gender, Region region, Integer salary) {

        super();
        this.first = first;
        this.last = last;
        this.birthDate = birthDate;
        this.gender = gender;
        this.region = region;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Person [first=" + first + ", last=" + last + ", birthDate=" + birthDate
            + ", gender=" + gender + ", region=" + region + ", salary=" + salary + "]";
    }

    public enum Gender {
        MALE, FEMALE;
    }

    public enum Region {
        LATAM, NA, EUROPE, UKI, MIDLE_EAST, AFRICA, ASIA, PACIFIC;
    }
}