package futures;  
  
import java.io.InputStreamReader;  
import java.io.LineNumberReader;  
import java.net.URL;  
import java.util.concurrent.Callable;  
  
class CallableExample implements Callable<Integer> {  
 private final String currentUrl;  
  
 CallableExample(String currentUrl) {  
  this.currentUrl = currentUrl;  
 }  
   
 public Integer call() throws Exception {  
  int result = 0;  
  URL url = new URL(currentUrl);  
  LineNumberReader in =   
    new LineNumberReader(  
      new InputStreamReader(url.openStream()));  
  
  try {  
   String line = null;  
   while ((line = in.readLine()) != null) {  
    result += line.length();  
   }  
  } finally {  
   in.close();  
  }  
  
  System.out.println(currentUrl + " has " + result + " bytes");  
  return result;  
 }  
}  
