package com.edu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Author: Ashwin Jayaprakash
 * Email: ashwin.jayaprakash@gmail.com 
 * Web: http://www.ashwinjayaprakash.com
 */
public class FjTest2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ConcurrentLinkedQueue<String> collector = new ConcurrentLinkedQueue<>();
        RecursiveNumLister2 rnl = new RecursiveNumLister2(collector, 23, 145);

        //Spawn the root asynchronously but wait for the root to finish.
        //Sub-tasks are programmed to wait for their sub-tasks to finish - bottom-up.
        //This way the entire tree will have completed, when this root finishes.
        ForkJoinPool.commonPool().invoke(rnl);

        ArrayList<String> list = new ArrayList<>(collector);
        Collections.sort(list);

        System.out.printf("Listed %d items%n", list.size());
        for (String s : list) {
            System.out.println("  " + s);
        }
    }

    static class RecursiveNumLister2 extends RecursiveAction {
        final ConcurrentLinkedQueue<String> collector;

        final int start;

        final int end;

        RecursiveNumLister2(ConcurrentLinkedQueue<String> collector, int startInc, int endEx) {
            this.collector = collector;
            this.start = startInc;
            this.end = endEx;
        }

        @Override
        protected void compute() {
            if (end - start < 5) {
                String s = Thread.currentThread().getName();

                for (int i = start; i < end; i++) {
                    collector.add(String.format("%5d_%s", i, s));
                }
            }
            else {
                int m = (end + start) / 2;

                RecursiveNumLister2 left = new RecursiveNumLister2(collector, start, m);
                RecursiveNumLister2 right = new RecursiveNumLister2(collector, m, end);

                left.fork();
                //Do the right part synchronously.
                right.compute();
                //Without waiting for the asynchronous left side, the root will exit prematurely.
                left.join();
            }
        }
    }
}