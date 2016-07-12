package com.javarticles.java8.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by beders on 7/7/15.
 */
public class Bus {
    public static class Event {}
    public static class Header {}
    List<Object> handlers = new ArrayList();

    public void register(Consumer<Event> handler) {
        handlers.add(handler);
    }

    public void register(BiConsumer<Event,Header> handler) {
        handlers.add(handler);
    }

    public void dispatch(Event event, Header header) {
        for (Object obj : handlers) {
            if (obj instanceof Consumer) {
                Consumer<Event> c = (Consumer<Event>) obj;
                c.accept(event);
            } else if (obj instanceof BiConsumer) {
                BiConsumer<Event, Header> b = (BiConsumer<Event, Header>) obj;
                b.accept(event, header);
            }
        }
    }

    public static void main(String... args) {
        Bus bus = new Bus();
        bus.register(event -> System.out.println("I gots an event"));
        bus.register((event,header) -> System.out.println("I gots an event w/ header"));

        bus.dispatch(new Event(), new Header());
    }
}