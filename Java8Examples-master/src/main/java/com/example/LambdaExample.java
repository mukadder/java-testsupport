package com.example;

import com.example.annotations.Fly;
import com.example.annotations.Schedule;
import com.example.ironman.IronMan;
import com.example.superhero.Avenger;
import com.example.superhero.Avengers;
import com.example.superhero.Batman;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * This class is just an example of how to use some of the Java 8 new features.
 * Created by najor on 7/10/15.
 */
public class LambdaExample {

    @Schedule(dayOfMonth = "last")
    @Schedule(dayOfWeek="Fri", hour="23")
    static void doPeriodicCleanup() { }

    static void  processStringList(List<String> stringList) {
        // process stringList
    }

    public static void main(String[] args) {
        methodParameterReflection();
        typeAnnotations();
        typeInference();
        defaultAndStaticInterfaceMethods();
        lambdaListener();
        Avengers avengers = lambdaCustomInterface();
        lambdaMethodReference(avengers);
        lambdaFunctionInterfaces(avengers);
    }

    private static void lambdaFunctionInterfaces(Avengers avengers) {
        Batman batman = avengers.getBatman();

        IronMan ironMan = new IronMan();
        IronMan machineWar = new IronMan();

        // Supplier<T>
        findIronMan(() -> ironMan);
        findIronMan(() -> {
            if (avengers.availableIronMan())
                return avengers.getIronMan();
            return machineWar;
        });

        findIronMan(IronMan::new);

        Person anonymous = new Person("Tony Stark");

        // Function<T,R>
        transformPersonToAvenger(
                anonymous,
                person ->
                        person.getName().equals("Bruce Wayne") ?
                                avengers.getBatman() :
                                avengers.getAquaman());

        // Consumer<T>
        attackAvenger(
                ironMan,
                avenger -> {
                    punch(avenger);
                    kick(avenger);
                });

        ArrayList persons = new ArrayList<Person>();
        persons.add(anonymous);

        // Predicate<T>
        filterSuperHero(persons, person -> isAvenger(person));

        // Runnable
        callBatman(() -> System.out.println("calling " + batman));
    }

    private static void lambdaMethodReference(Avengers avengers) {
        // Method Reference
        revealBatmanIdentity(avengers::getBatman);
        findIronMan(IronMan::new);
    }

    private static Avengers lambdaCustomInterface() {
        // Custom Interface
        Person bruceWayne = new Person("Bruce");
        destroyBatman(new Batman() {
            @Override
            public Person getIdentity() {
                return bruceWayne;
            }
        });

        destroyBatman(() -> bruceWayne);

        return new Avengers(() -> bruceWayne);
    }

    private static void lambdaListener() {
        // Listener in GUI
        TextField field = new TextField();

        field.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("myAction".equals(e.getActionCommand())) {
                    System.out.println(e.getActionCommand());
                }
            }
        });

        field.addActionListener(e -> {
            if ("myAction".equals(e.getActionCommand())) {
                System.out.println(e.getActionCommand());
            }
        });
    }

    private static void defaultAndStaticInterfaceMethods() {
        FightSkill example = null;
        example.punch();

        FightSkill.launchPunch(example);

        Kungfu.launchPunch(example);
        /*Does not work. Static method only in the scope
        of the interface that defines it.
        */
        example.fightStyle();
        KungFu kungFu = new KungFu();
        kungFu.fightStyle(); // print: "Kung Fu"
        kungFu.punch();      // print: "Kung Fu punch"

        kungFu.spitFire();
    }

    private static void typeInference() {
        // Java 6: declare the type
        List<String> stringsJava6 = new ArrayList<String>();

        // Java 7: Type Inference
        List<String> stringsJava7 = new ArrayList<>();

        // Java 7
        processStringList(Collections.<String>emptyList());

        // Java 7 failed, Java 8 works. Method argument infer the type of data
        processStringList(Collections.emptyList());

        doPeriodicCleanup();

        stringsJava6.add("");
        stringsJava7.add("");

        System.out.printf(stringsJava6.toString());
        System.out.printf(stringsJava7.toString());
    }

    private static void typeAnnotations() {
        String one;
        Object two = null;
        two = "yeah";

        // Type Annotations
        new @Fly Integer(23);
        one = (@Fly String) two;
        new ArrayList<@Fly String>();

        System.out.printf(one);
    }

    private static void methodParameterReflection() {
        Person.class.getMethods()[0].getParameters();
    }

    private static void callBatman(Runnable call) {
        call.run();
    }

    private static boolean isAvenger(Person person) {
        return true;
    }

    private static List<Person> filterSuperHero(List<Person> person, Predicate<Person> check) {
        return person.stream().filter(p -> check.test(p)).collect(Collectors.toList());
    }

    private static void kick(Avenger avenger) {
        System.out.println("Kicking " + avenger);
    }

    private static void punch(Avenger avenger) {
        System.out.println("Punching " + avenger);
    }

    private static void attackAvenger(Avenger avenger, Consumer<Avenger> attack) {
        attack.accept(avenger);
    }

    private static void transformPersonToAvenger(Person person, Function<Person, Avenger> transformation) {
        System.out.println(person.toString() + " - transform to -" + transformation.apply(person));
    }

    private static void revealBatmanIdentity(Supplier<Batman> supplier) {
        System.out.println("Batman is: " + supplier.get().getIdentity());
    }

    public static void destroyBatman(Batman batman) {
        System.out.println("You are dead " + batman.getIdentity() + "¡¡¡¡");
    }

    public static void findIronMan(Supplier<IronMan> ironManFinder) {
        System.out.println(" I found you IronMan - " + ironManFinder.get().toString());
    }
}
