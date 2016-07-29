package bloomfilter;

import java.util.concurrent.RecursiveTask;

class CountLetterTask extends RecursiveTask<Integer> {
 
  private static final long serialVersionUID = 1L;
  private static final int ACCEPTABLE_SIZE = 10_000;
  private char[] letterArray;
  private char key;
  private int start;
  private int stop;
 
  public CountLetterTask(char key, char[] letterArray, int start, int stop) {
    this.key = key;
    this.letterArray = letterArray;
    this.start = start;
    this.stop = stop;
  }
 
  @Override
  protected Integer compute() {
    int count = 0;
    int workLoadSize = stop - start;
    if (workLoadSize < ACCEPTABLE_SIZE) {
      // String threadName = Thread.currentThread().getName();
      // System.out.printf("Calculation [%d-%d] in Thread %s\n",start,stop,threadName);
      for (int i = start; i < stop; i++) {
        if (letterArray[i] == key)
          count++;
      }
    } else {
      int mid = start + workLoadSize / 2;
      CountLetterTask left = new CountLetterTask(key, letterArray, start, mid);
      CountLetterTask right = new CountLetterTask(key, letterArray, mid, stop);
 
      // fork (push to queue)-> compute -> join
      left.fork();
      int rightResult = right.compute();
      int leftResult = left.join();
      count = leftResult + rightResult;
    }
    return count;
  }
}
