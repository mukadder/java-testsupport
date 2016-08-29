package com.example.ironman;

import com.example.superhero.Avenger;

/**
 * Created by MHP on 22/07/2015.
 */
public class IronMan implements Avenger {
    private Inner inner;

    public IronMan() {
    }

    public IronMan(Iron iron) {
        this.inner = new Inner(new Source(iron.getPower()), new Strength(iron.getPower()));
    }

    public Inner getInner() {
        return inner;
    }

    public void fireTomahawk() {
        System.out.println("Launch Tomahawk!!!!");
    }

    public boolean isHumanInside() {
        return false;
    }

    public Integer getEnergy() {
        return 5;
    }

    public static String getName(IronMan ironMan) {
        return "Ultron";
    }

    public IronMan addPower(IronMan ironMan) {
        return this;
    }
}
