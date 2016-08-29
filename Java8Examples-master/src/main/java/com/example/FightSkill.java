package com.example;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by najor on 9/11/15.
 */

public interface FightSkill {

    String fightStyle();

    default String punch() {
        return "punch";
    }

    default String spitFire() {
        throw new NotImplementedException();
    }

    static String launchPunch(FightSkill fightSkill) {
        return fightSkill.punch();
    }
}
