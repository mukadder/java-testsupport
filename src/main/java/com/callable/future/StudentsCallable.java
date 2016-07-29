package com.callable.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;

/**
 * This class creates a list of students and each student is set to StudentService class. Each StudentService
 * class is submitted to ExecutorService class. ExecutorService class internally calls the call() method and 
 * returns the result to the resultlist.
 * The Executor
 * @author Sony Thomas
 *
 */
public class StudentsCallable {

 static Logger log = Logger.getLogger(StudentsCallable.class.getName());
 /**
  * @param args
  
  */
 public static void main(String[] args)  {
  
  /**  Populate a list of Students **/
  List<Student> students = new ArrayList<>();
  students.add(new Student("Dim"));
  students.add(new Student("Kim"));
  students.add(new Student(null));
  students.add(new Student("Neo"));
  
  List<Future<Object>> resultList = new ArrayList<Future<Object>>();
  ExecutorService executor = Executors.newFixedThreadPool(3);
  try{    
   for(Student student : students){
    StudentService studentService = new StudentService(student);
    resultList.add(executor.submit(studentService)) ;
   }
   log.info("Main thread continued with its execution");
   for(Future<Object> obj : resultList){
    if(obj.get() instanceof Exception){
     log.info(((Exception)obj.get()).getMessage());
    }else{
     log.info("Student name in result is " +  obj.get());
    }
   }
  }catch(InterruptedException | ExecutionException ex){
   log.debug(ex.getMessage());
  }
  executor.shutdown();
 }
}
