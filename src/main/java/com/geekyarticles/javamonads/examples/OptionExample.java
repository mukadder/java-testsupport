
package com.geekyarticles.javamonads.examples;

import com.geekyarticles.javamonads.Option;

public class OptionExample{
    public static class Userdetails{
        public Option<Address> address = new Option<>(null);
        public Option<Name> name = new Option<>(null);
        
    }
    
    public static class Name{
        public Option<String> firstName = new Option<String>(null);
        public Option<String> lastName = new Option<String>(null);        
    }
    
    public static class Address{
        public Option<String> houseNumber;
        public Option<Street> street;
        public Option<City> city;
        
    }
    
    public static class Street{
        public Option<String> name;        
    }
    
    public static class City{
        public Option<String> name;        
    }
        
    public static void main(String [] args){
        Option<Userdetails> userOpt =  new Option<>(new Userdetails());
        
        //And look how simple it is now
        String streetName = userOpt.flatMap(user -> user.address).map(address -> address.street).map(street -> street.name).get();
        System.out.println(streetName);
        
    }
    
}