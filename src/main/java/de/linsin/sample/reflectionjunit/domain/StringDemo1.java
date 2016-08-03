package de.linsin.sample.reflectionjunit.domain;

public class StringDemo1 {
	  public static String invertCapitals(String other) {
	    return other.chars()
	      .mapToObj(StringDemo1::flipCap)
	      .map(c -> Character.toString(c))
	      .reduce("", (s, c) -> s + c);
	  }
	  /*
	   * irst, we need to convert the string to a stream. That is what the chars() method does. Unfortunately, it creates an IntStream. We use the mapToObj() method to turn the IntStream into a Stream<Character>. Having done this, we use map() to turn it into a Stream<String>, and finally we can use reduce() to combine it all into a single string. 

While this does get the job done, it is very inefficient, as a new String object must be allocated for each reduction.  The following variation uses collect() to use a StringBuilder to accumulate the new String efficiently:
Prelude Data.Char> let flip c = if (isUpper c) then (toLower c) else (toUpper c) 
Prelude Data.Char> map flip "This is a TEST"
"tHIS IS A test"

	   */
	 
	  public static Character flipCap(int c) {
	    if (c >= 'A' && c <= 'Z') {
	       return (char)(c - 'A' + 'a');
	    } else if (c >= 'a' && c <= 'z') {
	       return (char)(c - 'a' + 'A');
	    } else {
	       return (char)c;
	    }
	  }
	}
