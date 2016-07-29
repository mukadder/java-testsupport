package com.edu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * Author: Ashwin Jayaprakash
 * Email: ashwin.jayaprakash@gmail.com
 * Web: http://www.ashwinjayaprakash.com
 */
public class FjTest5 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //Wrap FJ as an ECS.
        ExecutorCompletionService<ConcurrentLinkedQueue<String>> ecs =
                new ExecutorCompletionService<>(ForkJoinPool.commonPool());

        //Submit 5 tasks.
        for (int i = 0; i < 5; i++) {
            ConcurrentLinkedQueue<String> resultCollector = new ConcurrentLinkedQueue<>();
            //ECS only takes Runnable or Callable. So, create a Runnable as a Lambda expression.
            //Let the Runnable in turn call invoke() when it gets scheduled in the FJ pool.
            ecs.submit(() -> makeNew(resultCollector, 23, 145).invoke(), resultCollector);
        }

        //Wait for them to complete and pick up results one by one.
        for (int i = 0; i < 5; i++) {
            ConcurrentLinkedQueue<String> resultCollector = ecs.take().get();

            System.out.println(" Got result [" + (i + 1) + "] with [" + resultCollector.size() + " ] items");
        }
    }

    private static RecursiveNumLister5 makeNew(ConcurrentLinkedQueue<String> resultCollector, int startInc, int endEx) {
        return new RecursiveNumLister5Root(resultCollector, startInc, endEx,
                (collector) -> {
                    ArrayList<String> list = new ArrayList<>(collector);
                    Collections.sort(list);

                    System.out.printf("Listed %d items%n", list.size());
                    for (String s : list) {
                        System.out.println("  " + s);
                    }
                });
    }

    static class RecursiveNumLister5 extends CountedCompleter<Void> {
        final ConcurrentLinkedQueue<String> collector;

        final int start;

        final int end;

        RecursiveNumLister5(ConcurrentLinkedQueue<String> collector, int startInc, int endEx,
                            RecursiveNumLister5 parent) {
            //Completions will bubble up fom sub-tasks because of this link from parent to child.
            super(parent);

            this.collector = collector;
            this.start = startInc;
            this.end = endEx;
        }

        @Override
        public void compute() {
            if (end - start < 5) {
                String s = Thread.currentThread().getName();

                for (int i = start; i < end; i++) {
                    collector.add(String.format("%5d_%s", i, s));
                }

                //Signal that this is now complete. The completions will bubble up automatically.
                tryComplete();
            }
            else {
                int m = (end + start) / 2;

                RecursiveNumLister5 left = new RecursiveNumLister5(collector, start, m, this);
                RecursiveNumLister5 right = new RecursiveNumLister5(collector, m, end, this);

                //Only the left sub-task is forked, so set the pending count to 1.
                setPendingCount(1);
                left.fork();
                //Right sub-task is executed synchronously.
                right.compute();
            }

            //propagateCompletion(); <-- Not here. This would prematurely complete this task while children are still running.
        }
    }

    static class RecursiveNumLister5Root extends RecursiveNumLister5 {
        final Consumer<ConcurrentLinkedQueue<String>> completionListener;

        RecursiveNumLister5Root(ConcurrentLinkedQueue<String> collector, int startInc, int endEx,
                                Consumer<ConcurrentLinkedQueue<String>> completionListener) {
            super(collector, startInc, endEx, null);

            this.completionListener = completionListener;
        }

        @Override
        public void onCompletion(CountedCompleter<?> caller) {
            completionListener.accept(collector);
        }
    }
}