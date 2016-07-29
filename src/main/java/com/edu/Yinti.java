package com.edu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
 
/**
 *
 * @author Hitesh Tara
 */
class RowConverter extends RecursiveTask<List<Entity>> {
 
    //if more than 5000 we will use the parallel processing
    static final int SINGLE_TREAD_TOP = 5000;
    int begin;
    int end;
    List<Row> rows;
 
    public RowConverter(int begin, int end, List<Row> rows) {
        this.begin = begin;
        this.end = end;
        this.rows = rows;
    }
 
    @Override
    protected List<Entity> compute() {
 
        if (end - begin <= SINGLE_TREAD_TOP) {
            //actual processing happens here
            List<Entity> preparedEntities = new ArrayList<Entity>(end - begin);
            System.out.println("  beging: " + begin + " end: " + end);
            for (int i = begin; i < end; ++i) {
                preparedEntities.add(convertRow(rows.get(i)));
            }
            return preparedEntities;
        } else {
            //here we do the dividing the work and combining the results
            // specifies the number of chunks you want to break the data to
            int divider = 5000;
            // one can calculate the divider based on the list size and the number of processor available 
            // using the http://download.oracle.com/javase/7/docs/api/java/lang/Runtime.html#availableProcessors()
            // decrease the divider number and examine the changes.
 
            RowConverter curLeft = new RowConverter(begin, divider, rows);
            RowConverter curRight = new RowConverter(divider, end, rows);
            curLeft.fork();
            List<Entity> leftReslt = curRight.compute();
            List<Entity> rightRes = curLeft.join();
            leftReslt.addAll(rightRes);
            return leftReslt;
        }
    }
 
    //dummy converted method converting one DTO to another
    private Entity convertRow(Row row) {
 
        return new Entity(row.getId());
    }
}
 
// the driver class which own the pool 
public class Yinti {
 
    public static void main(String[] args) {
 
        List<Row> rawData = initDummyList(10000);
        ForkJoinPool pool = new ForkJoinPool();
        System.out.println("number of worker threads: " + pool.getParallelism());
 
 
        List<Entity> res = pool.invoke(new RowConverter(0, rawData.size(), rawData));
 
        // add a breakpoint here and examine the pool object. 
        //check how the stealCount, which shows number of subtasks taken on by available workers, 
        //changes when you use an smaller divider and thus produce more tasks
        System.out.println("processed list: " + res.size());
 
    }
 
    /**
     * creates a dummy list of rows
     * 
     * @param size number of rows int he list
     * @return the list of @see Row objects
     */
    private static List<Row> initDummyList(int size) {
 
        List<Row> rows = new ArrayList<Row>(size);
 
        for (int i = 0; i < size; i++) {
            rows.add(new Row(i));
        }
        return rows;
    }
}
 
//dummy classes which should be converted from one form to another
class Row {
 
    int id;
 
    public Row(int id) {
        this.id = id;
    }
 
    public int getId() {
        return id;
    }
}
 
class Entity {
 
    int id;
 
    public Entity(int id) {
        this.id = id;
    }
 
    public int getId() {
        return id;
    }
}