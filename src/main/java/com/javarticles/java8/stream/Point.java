package com.javarticles.java8.stream;

public class Point {
	  private final int x, y;
	  public Point(int x, int y) {
	    this.x = x; this.y = y; 
	  } 
	  @Override public String toString() {
	    final StringBuilder sb = new StringBuilder()
	      .append("(")
	      .append(x)
	      .append(", ")
	      .append(y)
	      .append(")");
	    return sb.toString();
	  }
	}