package com.geekyarticles.lambda;


public interface SingleArgExpression<P, R> {
    
    public R function(P param);
}