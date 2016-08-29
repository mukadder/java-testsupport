package com.example.ironman;

/**
 * Created by MHP on 22/07/2015.
 */
public class Inner {

    private Source source;
    private Strength strength;

    public Inner(Source source, Strength strength) {
        this.source = source;
        this.strength = strength;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Strength getStrength() {
        return strength;
    }

    public void setStrength(Strength strength) {
        this.strength = strength;
    }
}
