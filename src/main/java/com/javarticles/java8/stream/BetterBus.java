package com.javarticles.java8.stream;


import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by beders on 7/7/15.
 */
public class BetterBus {
    public static class Event {}
    public static class Header {}
    public interface BaseHandler {
        default void dispatch(Event evt, Header header) {}
    }
    public interface EventHandler extends BaseHandler {
        void handle(Event evt);
        default void dispatch(Event evt, Header header) {
            handle(evt); // ignore header
        }
    }
    public interface EventAndHeaderHandler extends BaseHandler {
        void handle(Event evt, Header header);
        default void dispatch(Event evt, Header header) {
            handle(evt, header);
        }
    }
    List<BaseHandler> handlers = new ArrayList();

    public void register(EventHandler handler) {
        handlers.add(handler);
    }

    public void register(EventAndHeaderHandler handler) { handlers.add(handler); }

    public void dispatch(Event event, Header header) {
        handlers.forEach(handler -> handler.dispatch(event, header));
    }

    public static void main(String... args) {
        BetterBus bus = new BetterBus();
        bus.register(event -> System.out.println("I gots an event"));
        bus.register((event,header) -> System.out.println("I gots an event w/ header"));

        bus.dispatch(new Event(), new Header());
    }
}