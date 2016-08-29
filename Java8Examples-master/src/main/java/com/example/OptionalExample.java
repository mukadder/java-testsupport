package com.example;

import com.example.ironman.*;

import java.util.Objects;

public class OptionalExample {

    public static void main(String[] args) {
        IronMan tonyStark = new IronMan(Iron.ADAMANTIUM);
        IronMan warMachine = new IronMan();
        IronMan najor = null;

        System.out.println(tonyStark.getInner().getSource().getPower());

        // NullPointerException
        System.out.println(warMachine.getInner().getSource().getPower());
        if (warMachine.getInner() != null && warMachine.getInner().getSource() != null &&
                warMachine.getInner().getSource().getPower() != null) {
            System.out.println(warMachine.getInner().getSource().getPower());
        }

        // isPresent
        if (!java.util.Optional.of(najor).isPresent()) {
            System.out.println("Fake");
        }

        // of lambada
        java.util.Optional<IronMan> tonyStarkOptional = java.util.Optional.of(tonyStark);
        tonyStarkOptional.map(ironMan -> ironMan.getInner())
                .map(inner -> inner.getSource())
                .map(source -> source.getPower())
                .ifPresent(power -> System.out.println(power));

        // of Method Reference
        tonyStarkOptional.map(IronMan::getInner)
                .map(Inner::getSource)
                .map(Source::getPower)
                .ifPresent(System.out::println);

        //ofNullable
        java.util.Optional.ofNullable(null).orElse("This is nothing");

        // map
        java.util.Optional.of(tonyStark)
                .map(IronMan::getInner)
                .map(Inner::getSource)
                .map(Source::getPower)
                .orElse("No more");

        // flatMap(::Optional)
        java.util.Optional.of(warMachine)
                .map(IronMan::getInner)
                .map(Inner::getStrength)
                .flatMap(Strength::getLevel)
                .ifPresent(System.out::println);

        // orElse
        java.util.Optional.of(warMachine)
                .map(IronMan::getInner)
                .map(Inner::getSource)
                .map(Source::getPower)
                .orElse("No inner: YOU ARE A FAKE");

        // ofNullable
        java.util.Optional.ofNullable(najor)
                .map(IronMan::getInner)
                .map(Inner::getSource)
                .map(Source::getPower)
                .orElse("No inner: YOU ARE A FAKE");

        // ifPresent: lambda, functional programming
        java.util.Optional.of(tonyStark)
                .map(IronMan::getInner)
                .map(Inner::getSource)
                .map(Source::getPower)
                .ifPresent(System.out::println);

        // orElseGet
        java.util.Optional.ofNullable(najor)
                .map(IronMan::getInner)
                .orElseGet(tonyStark::getInner);

        // Throws a NullPointerException
        Objects.requireNonNull(najor, "Ironman cannot be null");

    }
}
