package or.exception;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Exceptionhandlin {
	// how to solve this problem 
	public static void main(String[] args) {
		String p = new String("hello");
		Arrays.asList(p.getClass().getFields()).forEach(
		        f -> System.out.println(f.get(p))
		    );
		//try this 
		Arrays.asList(p.getClass().getFields()).forEach(
	            f -> {
	                try {
	                    System.out.println(f.get(p));
	                } catch (IllegalArgumentException | IllegalAccessException ex) {
	                    throw new RuntimeException(ex);
	                }
	            }
	    );
		//However in most cases I just want the exception to be thrown as a RuntimeException and let the program handle, or not, the exception without compilation errors.

//So, I would like to have your opinion about my controversial workaround for checked exceptions annoyance. To that end, I created an auxiliary interface ConsumerCheckException<T> and an utility function rethrow 
		
	}
	@FunctionalInterface
	  public interface ConsumerCheckException<T>{
	      void accept(T elem) throws Exception;
	  }
	public static class Wrappers {
	      public static <T> Consumer<T> rethrow(ConsumerCheckException<T> c) {
	        return elem -> {
	          try {
	            c.accept(elem);
	          } catch (Exception ex) {
	            /**
	             * within sneakyThrow() we cast to the parameterized type T. 
	             * In this case that type is RuntimeException. 
	             * At runtime, however, the generic types have been erased, so 
	             * that there is no T type anymore to cast to, so the cast
	             * disappears.
	             */
	            Wrappers.<RuntimeException>sneakyThrow(ex);
	          }
	        };
	      }

	      /**
	       * Reinier Zwitserloot who, as far as I know, had the first mention of this
	       * technique in 2009 on the java posse mailing list.
	       * http://www.mail-archive.com/javaposse@googlegroups.com/msg05984.html
	       */
	      public static <T extends Throwable> T sneakyThrow(Throwable t) {
	          throw (T) t;
	      }
	  }
	public static void doInOrder(Runnable first, Runnable second) {
		   first.run();
		   second.run();
		}
	/*
	 * f first.run() throws an exception, then the doInOrder method is terminated, second is never run, and the caller gets to deal with the exception.

But now suppose we execute the tasks asynchronously.
*/

public static void doInOrderAsync(Runnable first, Runnable second) {
   Thread t = new Thread() {
      public void run() {
         first.run();
         second.run();
      }
   };
   t.start();  
}
	 /*
	  * If first.run() throws an exception, the thread is terminated, and second is never run. However, the doInOrderAsync returns right away and does the work in a separate thread, so it is not possible to have the method rethrow the exception. In this situation, it is a good idea to supply a handler:


	  */
public static void doInOrderAsync(Runnable first, Runnable second,
	      Consumer<Throwable> handler) {
	   Thread t = new Thread() {
	      public void run() {
	         try {
	            first.run();
	            second.run();
	         } catch (Throwable t) {
	            handler.accept(t);
	         }
	      }
	   };
	   t.start();  
	}
/*
 * Now suppose that first produces a result that is consumed by second. We can still use the handler.


 */
public static <T> void doInOrderAsync(Supplier<T> first, Consumer<T> second,
	      Consumer<Throwable> handler) {
	   Thread t = new Thread() {
	      public void run() {
	         try {
	            T result = first.get();
	            second.accept(result);
	         } catch (Throwable t) {
	            handler.accept(t);
	         }
	      }
	   };
	   t.start();  
	}
public void createTempFileForKey(String key) {
	  Map<String, File> tempFiles = new ConcurrentHashMap<>();
	  //does not compile because it throws an IOException!!
	  tempFiles.computeIfAbsent(key, k -> File.createTempFile(key, ".tmp"));
	}
public void createTempFileForKey(String key) {
    Map<String, File> tempFiles = new ConcurrentHashMap<>();
    tempFiles.computeIfAbsent(key, k -> {
        try {
            return File.createTempFile(key, ".tmp");
        }catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    });
}
//o address this you could wrap the IOException in a generic RuntimeException as below:

public void createTempFileForKey(String key) throws RuntimeException {
    Map<String, File> tempFiles = new ConcurrentHashMap<>();
    tempFiles.computeIfAbsent(key, k -> {
        try {
            return File.createTempFile(key, ".tmp");
        }catch(IOException e) {
            throw new RuntimeException(e);
        }
    });
}/*
This code does throw an Exception but not the actual IOException which was intended to be thrown by the code. It's possible that those in favour of RuntimeExceptions only would be happy with this code especially if the solution could be refined to created a customised IORuntimeException.  Nevertheless the way most people code, they would expect their method to be able to throw the checked IOException from the File.createTempFile method.   

The natural way to do this is a little convoluted and looks like this:

}*/

public void createTempFileForKey(String key) throws IOException{
        Map<String, File> tempFiles = new ConcurrentHashMap<>();
        try {
            tempFiles.computeIfAbsent(key, k -> {
                try {
                    return File.createTempFile(key, ".tmp");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }catch(RuntimeException e){
            if(e.getCause() instanceof IOException){
                throw (IOException)e.getCause();
            }
        }
        private static Unsafe getUnsafe(){
            try {
                Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafe.setAccessible(true);
                return (Unsafe) theUnsafe.get(null);
            } catch (Exception e) {
                throw new AssertionError(e);
            }
        }
}/*
rom inside the lambda, you would have to catch the IOException, wrap it in a RuntimeException and throw that RuntimeException. The lambda would have to catch the RuntimeException unpack and rethrow the IOException. All very ugly indeed!

In an ideal world what we need is to be able to do is to throw the checked exception from within the lambda without having to change the declaration of computeIfAbsent. In other words, to throw a check exception as if it were an runtime exception. But unfortunately Java doesn't let us do that...

That is not unless we cheat! Here are two methods for doing precisely what we want, throwing a checked exception as if it were a runtime exception.


