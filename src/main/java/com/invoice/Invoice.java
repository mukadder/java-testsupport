package com.invoice;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Invoice {
   private String name,amount,category;
   public Invoice(String name,String amount,String category) {
     super();
     this.name=name;
     this.amount=amount;
     this.category=category;
   }
   public String getName() {
     return this.name;
   }
   public String getAmount() {
     return this.amount;
   }
   public String getCategory() {
     return this.category;
   }
   public static List<Invoice> generateInvoices()  {
	   List<Invoice> list = new ArrayList<Invoice>();
	   list.add(new Invoice("Oracle","1000","SOFTWARE"));
	   list.add(new Invoice("Microsof","30000","HARDWARE"));
	   list.add(new Invoice("Apple","5000","SOFTWARE"));
	return list;
	}
   public static Consumer<Invoice> printName() {
	    return new Consumer<Invoice>() {
	         public void accept(Invoice invoice) {
	           System.out.println(invoice.getName());
	         }
	    };
	}
   public static Consumer<Invoice> printAmount() {
	    return new Consumer<Invoice>() {
	         public void accept(Invoice invoice) {
	           System.out.println(invoice.getAmount());
	         }
	    };
}
   public static Predicate<Invoice> isHardware() {
	     return i -> i.getCategory().equals("HARDWARE");
	}
	 
}