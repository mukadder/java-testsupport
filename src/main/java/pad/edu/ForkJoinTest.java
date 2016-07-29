package pad.edu;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
/*
 * Now in Java 8 you can use this commonPool directly with the new method on ForkJoinPool commonPool().  This returns an instance of ForkJoinPool (which is an ExecutorService) with the commonPool of threads - the same ones that are used in parallelStream. This means that any work you do directly with the commonPool will play very nicely with work done in parallelStream especially the thread scheduling and work stealing between threads.

Let's work through an example of how you use ForkJoin especially in dealing with the tricky subject of exceptions.

Firstly obtain an instance of the commonPool by calling ForkJoin.commonPool().  You can submit tasks to it using the submit() method. Because we are using Java8 we can pass in lambda expressions which is really neat.  As with all ExecutorService implementations you can pass either instances of Runnable or Callable into submit().  When you pass a lambda into the submit method it will automatically turn it into a Runnable or a Callable by inspecting the method signature.

This leads to an interesting problem which highlights how lambdas work.  Supposing that you have a method of return type void (like a Runnable) but throws a checked exception (like a Callable).  See the method throwException() in the code listing below for such an example.  If you write this code it won't compile.
Future task1 = commonPool.submit(() -> {
            throwException("task 1");
        });
The reason for this is that the compiler assumes, because of the void return type, that you are trying to create a Runnable.  Of course a Runnable can't throw an Exception.  To get around this problem you need to force the compiler to understand that you are creating a Callable which is allowed to throw an Exception using this code trick. 
Future task1 = commonPool.submit(() -> {
            throwException("task 1");
            return null;
        });
This is a bit messy but does the job. Arguably, the compiler, could have worked this out itself.

Two more things to highlight in the full code listing below.  One, the fact that you can see how many threads are going to be available in the pool using commonPool.getParallelism(). This can be adjusted with the parameter '-Djava.util.concurrent.ForkJoinPool.common.parallelism'. Two, notice how you can unwrap the ExecutionException so that your code can just present an IOException to its callers rather a rather non-specific ExecutionException.  Also note that this code fails on the first exception.  If you want to collect all the exceptions you would have to structure the code appropriately, possibly returning a List of Exceptions.  Or maybe more neatly throwing a custom exception containing a list of underlying exceptions. 
 */
public class ForkJoinTest {
    public void run() throws IOException{
        ForkJoinPool commonPool = ForkJoinPool.commonPool();

        Future task1 = commonPool.submit(() -> {
            throwException("task 1");
            return null;
        });
        Future task2 = commonPool.submit(() -> {
            throwException("task 2");
            return null;
        });

        System.out.println("Do something while tasks being " +
                "executed on " + commonPool.getParallelism()
                + " threads");

        try {
            //wait on the result from task2
            task2.get();
            //wait on the result from task1
            task1.get();
        } catch (InterruptedException e) {
            throw new AssertionError(e);
        } catch (ExecutionException e) {
            Throwable innerException = e.getCause();
            if (innerException instanceof RuntimeException) {
                innerException = innerException.getCause();
                if(innerException instanceof IOException){
                    throw (IOException) innerException;
                }
            }
            throw new AssertionError(e);
        }
    }

    public void throwException(String message) throws IOException,
            InterruptedException {
        Thread.sleep(100);
        System.out.println(Thread.currentThread() 
            + " throwing IOException");
        throw new IOException("Throw exception for " + message);
    }

    public static void main(String[] args) throws IOException{
        new ForkJoinTest().run();
    }
}