package com.example;

import com.example.annotations.Fly;

public class Person<@Fly T> implements @Fly FightSkill{

    private String name;

    public Person(@Fly String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String fightStyle() {
        return null;
    }

    @Override
    public String punch() {
        return null;
    }

    @Override
    public String spitFire() {
        return null;
    }
}
