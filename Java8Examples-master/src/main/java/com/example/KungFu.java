package com.example;

/**
 * Created by najor on 9/11/15.
 */


public class KungFu implements FightSkill {

    @Override
    public String fightStyle() {
        return "Kung Fu";
    }

    @Override
    public String punch() {
        return "Kung Fu punch";
    }
}
