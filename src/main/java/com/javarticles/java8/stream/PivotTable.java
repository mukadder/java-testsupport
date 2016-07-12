package com.javarticles.java8.stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
/**
 * An interesting way to represent data is a pivot table. If you use spreadsheet programs like Excel you might have seen these before. If not then you are about to enjoy it.
Say you have data that is related in three parts. We can field this in a table with column and rows and the middle intersection is a related field. For this challenge you will need to make a pivot table for a wind energy farm. These farms of wind mills run several windmills with tower numbers. They generate energy measured in kilowatt hours (kWh).
You will need to read in raw data from the field computers that collect readings throughout the week. The data is not sorted very well. You will need to display it all in a nice pivot table.
Top Columns should be the days of the week. Side Rows should be the tower numbers and the data in the middle the total kWh hours produced for that tower on that day of the week.
input:

The challenge input is 1000 lines of the computer logs. You will find it HERE - gist of it
The log data is in the format:
(tower #) (day of the week) (kWh)
output:

A nicely formatted pivot table to report to management of the weekly kilowatt hours of the wind farm by day of the week.

 * @author mukadder
 *
 */
public class PivotTable {
	  static final List<String> days = new ArrayList(Arrays.asList(new String[] {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}));

	  public static void main(String... args) throws FileNotFoundException {
	    Scanner s = new Scanner(new File("windfarm.dat"));
	    Map<Integer, Map<String, Data>> repository = new HashMap<>();

	    while (s.hasNextInt()) {
	      Data data = new Data();
	      data.tower = s.nextInt();
	      data.day = s.next();
	      data.kwh = s.nextInt();
	      s.nextLine();
	      add(repository, data);
	    }

	    List<Integer> towers = new ArrayList<>(repository.keySet());
	    Collections.sort(towers);

	    System.out.print("Tower ");
	    days.stream().forEach((day) -> {
	      System.out.print(" " + day + " ");
	    });
	    System.out.println();

	    towers.stream().forEach((tower) -> {
	      System.out.print(tower + "  ");
	      days.stream().forEach((day) -> {
	        Data d = repository.get(tower).get(day);
	        System.out.format("%4d ", d != null ? d.kwh : 0);
	      });
	      System.out.println();
	    });
	  }

	  public static void add(Map<Integer, Map<String, Data>> repository, Data data) {
	    Map<String, Data> tower = repository.get(data.tower);
	    if (tower == null)
	      repository.put(data.tower, tower = new HashMap<String, Data>());

	    Data previous = tower.get(data.day);
	    if (previous == null) {
	      tower.put(data.day, data);
	    } else {
	      previous.kwh += data.kwh;
	    }
	  }
	}

	class Data {
	  int tower;
	  String day;
	  int kwh;
	}