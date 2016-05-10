package com.smalaca.enumeration.designpatterns;
 
public enum Creational {
 
    ABSTRACT_FACTORY {
        @Override
        public String describe() {
            return "Provide an interface for creating families of related or dependent objects without specifying their concrete classes.";
        }
    },
  
    BUILDER {
        @Override
        public String describe() {
            return "Separate the construction of a complex object from its representation allowing the same construction process to create various representations.";
        }
    },
  
    FACTORY_METHOD {
        @Override
        public String describe() {
            return "Define an interface for creating a single object, but let subclasses decide which class to instantiate.";
        }
    };
  
    abstract public String describe();
}