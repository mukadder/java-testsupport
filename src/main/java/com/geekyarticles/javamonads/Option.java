package com.geekyarticles.javamonads;

import com.geekyarticles.lambda.SingleArgExpression;

public class Option<T> {
    T value;
    
    public Option(T value){
        this.value = value;
    }
    public <E> Option<E> map(SingleArgExpression<T,E> mapper){
        if(value == null){
            return new Option<E>(null);
        }else{
            return new Option<E>(mapper.function(value));
        }
        
    }    
    
    @Override
    public boolean equals(Object rhs){
        if(rhs instanceof Option){
            Option o = (Option)rhs;
            if(value == null) 
                return (o.value==null);
            else{
                return value.equals(o.value);
            }
        }else{
            return false;
        }
        
    }
    
    @Override
    public int hashCode(){
        return value==null? 0 : value.hashCode();
    }
    
    public T get(){
        return value;
    }
}

