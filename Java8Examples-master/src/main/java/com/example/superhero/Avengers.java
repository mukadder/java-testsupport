package com.example.superhero;

import com.example.ironman.IronMan;

/**
 * Created by najor on 14/10/15.
 */
public class Avengers {

    private Batman batman;
    private IronMan ironMan;
    private Avenger aquaman;

    public Avengers(Batman batman) {
        this.batman = batman;
    }

    public Batman getBatman() {
        return batman;
    }
    public IronMan getIronMan() {
        return ironMan;
    }

    public void setBatman(Batman batman) {
        this.batman = batman;
    }

    public boolean availableIronMan() {
        return ironMan != null;
    }

    public Avenger getAquaman() {
        return aquaman;
    }
}
