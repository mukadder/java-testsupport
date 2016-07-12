package com.javarticles.java8.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlatMapExample {
    public static void main(String[] args) {
        /* Create an object of `Company` and set values 
         * Explanation: .flatMap(key -> companies.get(key).stream())
In this operation we are taking stream of List<Company> from each key of Map<String, List<Company>>. 
That returns the Stream of Company objects from stream of List<Company>.*/
        Company companyApple = new Company();
        companyApple.setId(1L);
        companyApple.setCompanyName("Apple");
        
        Company companySamsung = new Company();
        companySamsung.setId(2L);
        companySamsung.setCompanyName("Samsung");
       
        /**
         * - Create `List` of `Company`
         * - Add `companyApple` and `companySamsung` to List.
         */
        List<Company> mobileCompanies=  new ArrayList<>();
        mobileCompanies.add(companyApple);
        mobileCompanies.add(companySamsung);
        
        /* Create an object of `Company` and set values */
        Company companyFacebook = new Company();
        companyFacebook.setId(3L);
        companyFacebook.setCompanyName("Facebook");
        
        Company companyTwitter = new Company();
        companyTwitter.setId(4L);
        companyTwitter.setCompanyName("Twitter");
        
        /**
         * - Create `List` of `Company`
         * - Add `companyFacebook` and `companyTwitter` to List.
         */
        List<Company> socialNetworkingCompanies = new ArrayList<>();
        socialNetworkingCompanies.add(companyFacebook);
        socialNetworkingCompanies.add(companyTwitter);
                
        /* Add `List` of `Companies` to Map */
        Map<String, List<Company>> companies = new HashMap<>();
        companies.put("MobileCompanies", mobileCompanies);
        companies.put("SocialNetworkingCompanies", socialNetworkingCompanies);
        
        /**
         * JavaDoc: `map` method
         * Returns a stream consisting of the results of replacing each element of this stream with the contents of a mapped stream
         * produced by applying the provided mapping function to each element. Each mapped stream is closed after its contents have 
         * been placed into this stream. (If a mapped stream is null an empty stream is used, instead.)
         * 
         * Explanation: `.flatMap(key -> companies.get(key).stream())`
         * In this operation we are taking `stream` of `List<Company>` from each key of `Map<String, List<Company>>`.
         * That returns the `Stream` of `Company` objects from `stream` of `List<Company>`.
         */
        companies.keySet()
                 .stream()
                 .flatMap(key -> companies.get(key).stream())
                 .forEach(com -> {
                     System.out.println(com.getCompanyName());
                 });
    }
}