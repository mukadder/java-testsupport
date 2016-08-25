package com.geekyarticles.javamonads;

import com.geekyarticles.lambda.SingleArgExpression;

public class dd<T> {
    T value;
    
    public dd(T value){
        this.value = value;
    }
    public <E> dd<E> map(SingleArgExpression<T,E> mapper){
        if(value == null){
            return new dd<E>(null);
        }else{
            return new dd<E>(mapper.function(value));
        }
        
    }
    public <E> dd<E> flatMap(SingleArgExpression<T, dd<E>> mapper){
        if(value == null){
            return new dd<E>(null);
        }
        return  mapper.function(value);
        
    }
    public dd<T> filter(SingleArgExpression<T, Boolean> filter){
        if(value == null){
            return new dd<T>(null);
        }else if(filter.function(value)){
            return this;
        }else{
            return new dd<T>(null);
        }
        
    }
    
    @Override
    public boolean equals(Object rhs){
        if(rhs instanceof dd){
            dd o = (dd)rhs;
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