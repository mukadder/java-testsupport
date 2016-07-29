package bloomfilter;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinDemo {
	  private static final int ARRAY_SIZE = 1000000;
	  private static char[] letterArray = new char[ARRAY_SIZE];
	 
	  private static int countLetterUsingForkJoin(char key) {
	    int total = 0;
	    ForkJoinPool pool = new ForkJoinPool(); // create thread pool for fork/join
	    CountLetterTask task = new CountLetterTask(key, letterArray, 0, ARRAY_SIZE);
	    total = pool.invoke(task); // submit the task to fork/join pool
	 
	    pool.shutdown();
	    return total;
	  }
	 
	  public static void main(String[] args) {
	    char key = 'A';
	    // fill the big array with A-Z randomly
	    for (int i = 0; i < ARRAY_SIZE; i++) {
	      letterArray[i] = (char) (Math.random() * 26 + 65); // A-Z
	    }
	 
	    int count = countLetterUsingForkJoin(key);
	    System.out.printf("Using ForkJoin, found %d '%c'\n", count, key);
	  }
	}
