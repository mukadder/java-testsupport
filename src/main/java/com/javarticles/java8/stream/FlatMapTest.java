package com.javarticles.java8.stream;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class FlatMapTest {

    @Test
    public void flatMap() {
        List<Developer> team = new ArrayList<>();
        Developer polyglot = new Developer("esoteric");
        polyglot.add("clojure");
        polyglot.add("scala");
        polyglot.add("groovy");
        polyglot.add("go");

        Developer busy = new Developer("pragmatic");
        busy.add("java");
        busy.add("javascript");

        team.add(polyglot);
        team.add(busy);

        List<String> teamLanguages = team.stream().
                map(d -> d.getLanguages()).
                flatMap(l -> l.stream()).
                collect(Collectors.toList());
        assertTrue(teamLanguages.containsAll(polyglot.getLanguages()));
        assertTrue(teamLanguages.containsAll(busy.getLanguages()));
    }
    @Test
    public void test_add_to_date() {
    	LocalDate today = LocalDate.parse("2014-02-27");
    	//or this method
    	LocalDate bday = LocalDate.of(2014,3,18);
        LocalDate oneMonthFromNow = today.plusDays(30);
        assertTrue(oneMonthFromNow.isEqual(LocalDate.parse("2014-03-29")));

        LocalDate nextMonth = today.plusMonths(1);
        assertTrue(nextMonth.isEqual(LocalDate.parse("2014-03-27")));

        LocalDate future = today.plus(4, ChronoUnit.WEEKS);
        assertTrue(future.isEqual(LocalDate.parse("2014-03-27")));

    }
    @Test
    public void test_subtract_from_date() {
    	LocalDate today = LocalDate.parse("2014-02-27");
        assertEquals(today.minusWeeks(1).toString(), "2014-02-20");

        assertEquals(today.minusMonths(2).toString(), "2013-12-27");

        assertEquals(today.minusYears(4).toString(), "2010-02-27");

        Period twoMonths = Period.ofMonths(2);

        assertEquals(today.minus(twoMonths).toString(), "2013-12-27");

    }
    @Test
    public void test_get_days_between_dates() {
    	LocalDate today = LocalDate.parse("2014-02-27");
        LocalDate vacationStart = LocalDate.parse("2014-07-04");
        Period timeUntilVacation = today.until(vacationStart);

        assertEquals(timeUntilVacation.getMonths(),4);

        assertEquals(timeUntilVacation.getDays(),7);

        assertEquals(today.until(vacationStart, ChronoUnit.DAYS), 127L);

        LocalDate libraryBookDue = LocalDate.parse("2000-03-18");

        assertEquals(today.until(libraryBookDue).isNegative(), true);


        assertEquals(today.until(libraryBookDue, ChronoUnit.DAYS), -5094L);

        LocalDate christmas = LocalDate.parse("2014-12-25");
        assertEquals(today.until(christmas, ChronoUnit.DAYS), 301);

    }
}