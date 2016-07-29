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
public class FjTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ConcurrentLinkedQueue<String> collector = new ConcurrentLinkedQueue<>();
        RecursiveNumLister rnl = new RecursiveNumLister(collector, 23, 145);

        //Sub-tasks are just fired off asynchronously.
        //Tasks are not programmed to wait until the sub-tasks finish.
        //So, the root of the tree will exit before the entire tree of tasks complete.
        ForkJoinPool.commonPool().invoke(rnl);

        //This is why we have to resort to a crude mechanism - thread sleep.
        System.out.println("Thread going to sleep and hoping children tasks complete in 5s");
        Thread.sleep(5_000);
        System.out.println("Thread is now awake");

        ArrayList<String> list = new ArrayList<>(collector);
        Collections.sort(list);

        System.out.printf("Listed %d items%n", list.size());
        for (String s : list) {
            System.out.println("  " + s);
        }
    }

    static class RecursiveNumLister extends RecursiveAction {
        final ConcurrentLinkedQueue<String> collector;

        final int start;

        final int end;

        RecursiveNumLister(ConcurrentLinkedQueue<String> collector, int startInc, int endEx) {
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

                RecursiveNumLister left = new RecursiveNumLister(collector, start, m);
                RecursiveNumLister right = new RecursiveNumLister(collector, m, end);

                //Spawn both off, asynchronously.
                left.fork();
                right.fork();
            }
        }
    }
}