package om.edu;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
/*
 * he below code describes how to read a file when a particular file is actively being written. Here is a full example. For the below example the mentioned file should be existed in 
 * the mentioned path else it will throw FileNotFoundException.
 * The Executor framework helps to decouple a command submission from command execution.In the java.util.concurrent package there are three interfaces:

Executor — Used to submit a new task.

ExecutorService — A subinterface of Executor that adds methods to manage lifecycle of threads used to run the submitted tasks and methods to produce a Future to get a result from an asynchronous computation.

ScheduledExecutorService — A subinterface of ExecutorService, to execute commands periodically or after a given delay.
public class MyCommand implements Runnable {
  public void run() {
    // code to be executed
  }
}Executor Interface
Using an Executor it is possible to remove the manual creation of threads to execute a command.

Given any Runnable implementation, like the following:
The following code with an explicit Thread creation:

Thread t = new Thread(new MyRunnable());
t.start();
can be replaced with the following code that instead uses an Executor:

Executor executor = ... // Executor creation
executor.execute(new MyRunnable());
ExecutorService Interface
ExecutorService adds a more useful and advanced version method to execute commands, submit.

Passing a Callable to the submit method is possible to get a Future object and use it to retrieve the result of the asynchronous computation.

Additionally it is possible to shutdown an ExecutorService rejecting submissions of new commands. Using the shutdown method all submitted commands will be executed before stopping the ExecutorService, but no new command is accepted. A call to shutdownNow 
prevents waiting tasks to be executed and try to stop all currently executing commands.
Runnable myCommand1 = ...
Callable<String> myCommand2 = ...
ExecutorService executorService = ... // Build an executorService
executorService.submit(myCommand1);
// submit Accepts also a Callable
Future<String> resultFromMyCommand2 = executorService.submit(myCommand2);   
// Will wait for myCommand1 and myCommand2 termination
executorService.shutdown();  
Runnable myCommand3 = ...;
// Will throw a RejectedExecutionException because no new task can be submitted
executorService.submit(myCommand3);ScheduledExecutorService
The ScheduledExecutorService is used to schedule command executions after a given delay or periodically, it must be used as a replacement for Timer and TimerTask.

It uses the method schedule to run the command after a given delay of time,
 scheduleAtFixedRate and scheduleWithFixedDelay are used to execute a task periodically:
 ScheduledExecutorService executor = ...;
Runnable command1 = ...;
Runnable command2 = ...;
Runnable command3 = ...;
// Will start command1 after 50 seconds
executor.schedule(command1, 50L, TimeUnit.SECONDS);
// Will start command 2 after 20 seconds, 25 seconds, 30 seconds ...
executor.scheduleAtFixedRate(command2, 20L, 5L, TimeUnit.SECONDS);
// Will start command 3 after 10 seconds and if command3 takes 2 seconds to be
// executed also after 17, 24, 31, 38 seconds...
executor.scheduleWithFixedDelay(command3, 10L, 5L, TimeUnit.SECONDS);
How to Create an Executor
To create an Executor it is possible to use the factory Executors class.

Most common methods are used to create:

an ExecutorService with a single thread to execute commands with method newSingleThreadExecutor.

a ScheduledExecutorService with a single thread to execute commands with the method newSingleThreadScheduledExecutor.

an ExecutorService that use a fixed length pool of threads to execute commands with the method newFixedThreadPool.

an ExecutorService with a pool of threads that creates a new thread if no thread is available and reuse an existing thread if they are available with newCachedThreadPool.

a ScheduledExecutorService with a fixed length pool of threads to execute scheduled commands with the method newScheduledThreadPool.

Here are examples to creates ExecutorService and ScheduledExecutorService instances:
/ Creates a single thread ExecutorService
ExecutorService singleExecutorService = Executors.newSingleThreadExecutor();
// Creates a single thread ScheduledExecutorService
ScheduledExecutorService singleScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
// Creates an ExecutorService that use a pool of 10 threads
ExecutorService fixedExecutorService = Executors.newFixedThreadPool(10);
// Creates an ExecutorService that use a pool that creates threads on demand
// And that kill them after 60 seconds if they are not used
ExecutorService onDemandExecutorService = Executors.newCachedThreadPool();
// Creates a ScheduledExecutorService that use a pool of 5 threads
ScheduledExecutorService fixedScheduledExecutorService = Executors.newScheduledThreadPool(5);
 */
public class ReadingFileWhileWrite extends Thread {
    boolean running = true;
    BufferedInputStream reader = null;
    public static void main(String[] args) throws FileNotFoundException {
        ReadingFileWhileWrite tw = new ReadingFileWhileWrite();
        tw.reader = new BufferedInputStream(new FileInputStream("TestFile.txt"));
        tw.start();
    }
    public void run() {
        while (running) {
            try {
                if (reader.available() > 0) {
                    System.out.print((char) reader.read());
                } else {
                    try {
                        sleep(500);
                    } catch (InterruptedException ex) {
                        running = false;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
