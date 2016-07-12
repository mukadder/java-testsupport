package com.javarticles.java8.stream;

import java.awt.Point;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Spreadsheet {
  Map<String, String> cells = new HashMap<>();

  public void command(String command) {
    if (command.contains("=")) {
      String[] parts = command.split("=");
      assign(parts[0], parts[1]);
    } else {
      System.out.println(command + " -> " + evaluate(command));
    }
  }

  public void assign(String targets, String value) {
    Parser.parseToCells(targets).stream().forEach((cell) -> {
      cells.put(cell, value);
    });
  }

  public BigDecimal evaluate(String input) {
    if (input.contains("(")) {
      String function = input.substring(0, input.indexOf("("));
      String arguments = input.substring(input.indexOf("(") + 1, input.indexOf(")"));

      Set<String> selection = Parser.parseToCells(arguments);
      BigDecimal result = BigDecimal.ZERO;

      switch (function) {
        case "sum":
          result = BigDecimal.ZERO;
          for (String cell: selection) {
            result = result.add(evaluate(cell));
          }
          break;

        case "product":
          result = BigDecimal.ZERO;
          for (String cell: selection) {
            result = result.multiply(evaluate(cell));
          }
          break;

        case "average":
          result = BigDecimal.ZERO;
          for (String cell: selection) {
            result = result.add(evaluate(cell));
          }
          result = result.divide(new BigDecimal(selection.size()), 10, RoundingMode.HALF_EVEN);
          break;
      }

      return result;
    }

    Pattern p = Pattern.compile("(.*)([+-/*])(.*)");
    Matcher m = p.matcher(input);
    if (m.matches()) {
      BigDecimal result = BigDecimal.ZERO;

      BigDecimal a = evaluate(m.group(1));
      String operator = m.group(2);
      BigDecimal b = evaluate(m.group(3));

      switch(operator) {
        case "+": result = a.add(b); break;
        case "-": result = a.subtract(b); break;
        case "/": result = a.divide(b, 10, RoundingMode.HALF_EVEN); break;
        case "*": result = a.multiply(b); break;
      }

      return result;
    }

    if (Character.isDigit(input.charAt(0))) {
      return new BigDecimal(input);
    }

    if (!cells.containsKey(input))
      return BigDecimal.ZERO;

    return evaluate(cells.get(input));
  }
}

class Parser {
  public static Set<String> parseToCells(String input) {
    return parse(input).execute();
  }

  public static Operation parse(String input) {
    if (input.contains("~")) {
      String[] parts = input.split("\\~", 2);
      return new Operation(parts[0], parts[1]) {
        @Override public Set<String> execute() {
          Set<String> results = operand1.execute();
          results.removeAll(operand2.execute());
          return results;
        }
      };
    }

    if (input.contains("&")) {
      String[] parts = input.split("\\&", 2);
      return new Operation(parts[0], parts[1]) {
        @Override public Set<String> execute() {
          Set<String> results = operand1.execute();
          results.addAll(operand2.execute());
          return results;
        }
      };
    }

    if (input.contains(":")) {
      String[] parts = input.split("\\:", 2);
      return new Operation(parts[0], parts[1]) {
        @Override
        public Set<String> execute() {
          Set<String> results = new HashSet<>();

          Point start = Parser.toPoint((String) operand1.execute().toArray()[0]);
          Point end = Parser.toPoint((String) operand2.execute().toArray()[0]);

          for (int x = start.x; x <= end.x; x++) {
            for (int y = start.y; y <= end.y; y++) {
              results.add(Parser.toString(new Point(x, y)));
            }
          }

          return results;
        }
      };
    }

    return new Operation(input);
  }

  static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  public static Point toPoint(String s) {
    int i = 0;
    while (Character.isLetter(s.charAt(++i)));
    String letters = s.substring(0, i);
    String numbers = s.substring(i);

    int x = 0;
    int exp = 0;
    i = letters.length() - 1;
    while (i >= 0) {
      x += Math.pow(26, exp) * (ALPHABET.indexOf(letters.charAt(i--) + (exp++ > 0 ? 1 : 0)));
    }

    return new Point(x, Integer.parseInt(numbers) - 1);
  }

  public static String toLetter(int x) {
    String s = "";
    boolean first = true;

    while (x > 26) {
      s = ALPHABET.charAt((x % 26) + (!first ? -1 : 0)) + s; 
      x /= 26;
      first = false;
    }

    s = ALPHABET.charAt(x - (!first ? -1 : 0)) + s; 
    return s;
  }

  public static String toString(Point point) {
    return toLetter(point.x) + (point.y + 1);
  }
}

class Operation {
  protected Operation operand1;
  protected Operation operand2;
  protected String value;

  public Operation(String o1, String o2) {
    this.operand1 = Parser.parse(o1);
    this.operand2 = Parser.parse(o2);
  }

  public Operation(String value) {
    this.value = value;
  }

  public Set<String> execute() {
    return new HashSet<>(Arrays.asList(new String[] {value}));
  }
}
