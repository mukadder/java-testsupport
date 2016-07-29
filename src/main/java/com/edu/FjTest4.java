package com.edu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;

/**
 * Author: Ashwin Jayaprakash
 * Email: ashwin.jayaprakash@gmail.com
 * Web: http://www.ashwinjayaprakash.com
 */
public class FjTest4 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RecursiveNumLister4 rnl = new RecursiveNumLister4Root(new ConcurrentLinkedQueue<>(), 23, 145,
                (collector) -> {
                    ArrayList<String> list = new ArrayList<>(collector);
                    Collections.sort(list);

                    System.out.printf("Listed %d items%n", list.size());
                    for (String s : list) {
                        System.out.println("  " + s);
                    }
                });

        ForkJoinPool.commonPool().invoke(rnl);
    }

    static class RecursiveNumLister4 extends CountedCompleter<Void> {
        final ConcurrentLinkedQueue<String> collector;

        final int start;

        final int end;

        RecursiveNumLister4(ConcurrentLinkedQueue<String> collector, int startInc, int endEx,
                            RecursiveNumLister4 parent) {
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

                RecursiveNumLister4 left = new RecursiveNumLister4(collector, start, m, this);
                RecursiveNumLister4 right = new RecursiveNumLister4(collector, m, end, this);

                //Only the left sub-task is forked, so set the pending count to 1.
                setPendingCount(1);
                left.fork();
                //Right sub-task is executed synchronously.
                right.compute();
            }

            //propagateCompletion(); <-- Not here. This would prematurely complete this task while children are still running.
        }
    }

    static class RecursiveNumLister4Root extends RecursiveNumLister4 {
        final Consumer<ConcurrentLinkedQueue<String>> completionListener;

        RecursiveNumLister4Root(ConcurrentLinkedQueue<String> collector, int startInc, int endEx,
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