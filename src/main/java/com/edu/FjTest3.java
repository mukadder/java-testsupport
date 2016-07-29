package com.edu;



	import java.util.ArrayList;
	import java.util.Collections;
	import java.util.concurrent.ConcurrentLinkedQueue;
	import java.util.concurrent.CountedCompleter;
	import java.util.concurrent.ExecutionException;
	import java.util.concurrent.ForkJoinPool;

	/**
	 * Author: Ashwin Jayaprakash
	 * Email: ashwin.jayaprakash@gmail.com
	 * Web: http://www.ashwinjayaprakash.com
	 */
	public class FjTest3 {
	    public static void main(String[] args) throws ExecutionException, InterruptedException {
	        ConcurrentLinkedQueue<String> collector = new ConcurrentLinkedQueue<>();
	        RecursiveNumLister3 rnl = new RecursiveNumLister3(collector, 23, 145);

	        ForkJoinPool.commonPool().invoke(rnl);

	        ArrayList<String> list = new ArrayList<>(collector);
	        Collections.sort(list);

	        System.out.printf("Listed %d items%n", list.size());
	        for (String s : list) {
	            System.out.println("  " + s);
	        }
	    }

	    static class RecursiveNumLister3 extends CountedCompleter<Void> {
	        final ConcurrentLinkedQueue<String> collector;

	        final int start;

	        final int end;

	        RecursiveNumLister3(ConcurrentLinkedQueue<String> collector, int startInc, int endEx) {
	            this(collector, startInc, endEx, null);
	        }

	        RecursiveNumLister3(ConcurrentLinkedQueue<String> collector, int startInc, int endEx,
	                            RecursiveNumLister3 parent) {
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
	                propagateCompletion();
	            }
	            else {
	                int m = (end + start) / 2;

	                RecursiveNumLister3 left = new RecursiveNumLister3(collector, start, m, this);
	                RecursiveNumLister3 right = new RecursiveNumLister3(collector, m, end, this);

	                //Only the left sub-task is forked, so set the pending count to 1.
	                setPendingCount(1);
	                left.fork();
	                //Right sub-task is executed synchronously.
	                right.compute();
	            }

	            //propagateCompletion(); <-- Not here. This would prematurely complete this task while children are still running.
	        }
	    }
	}
