# The Bounds of Java Newsletter
## #2 &ndash; Functional Default Arguments

### Outline
Java lacks a built-in way to define default arguments for methods and constructors. Over the years, several approaches have been proposed, each with its pros and cons. The most widely-known one uses *method overloading*, though varargs, null values, the builder pattern and even maps have been used as well. Here we propose a new approach based on functional constructs.

Please visit [http://boundsofjava.com/newsletter/002-functional-default-arguments](http://boundsofjava.com/newsletter/002-functional-default-arguments) to read this newsletter.

Feel free to report issues, suggest improvements or leave any comment.

License is [MIT](LICENSE).
Default Arguments and Functional Programming
Let me introduce you to the MaleProgrammer class, which I'll use as an example for the rest of this newsletter:

package com.boundsofjava.newsletter.defaultargument;
import java.util.stream.DoubleStream;
/**
 * Represents a typical male programmer.
 */
public class MaleProgrammer {
    /**
     * Puts this male programmer to perform the given task for the specified duration
     * and prints how profitable it was to hire him.
     * Total profit is lowered if there are women nearby.
     *
     * @param task          Task to be performed by this male programmer
     * @param durationHours Duration of the task
     * @param womenNearby   Number of women nearby
     */
    public void performTask(Task task, int durationHours, int womenNearby) {
        String output = "- I've been %s for %d hours.%n";
        output += "  %d women nearby.%n";
        output += "  I'm as valuable as $ %.2f.%n";
        // Calculate initial profit that matches the whole task duration
        double profit = task.getProfitPerHour() * durationHours;
        // Now introduce negative factors (each women nearby
        // implies a cumulative 10% profit reduction)
        double totalProfit = DoubleStream.iterate(profit, p -> p * 0.90)
                .limit(womenNearby + 1)
                .min()
                .orElse(profit);
        System.out.printf(output, task.getAction(), durationHours, womenNearby, totalProfit);
    }
}


The performTask method performs the given task for the given duration, taking the number of women nearby into account. These three arguments are used to calculate how profitable it was to hire this MaleProgrammer instance.

Task is an enum that represents all the possible tasks a male programmer can do:

package com.boundsofjava.newsletter.defaultargument;
/**
 * Represents all possible tasks a typical male programmer can accomplish.
 */
public enum Task {
    DEVELOP_SOFTWARE("developing software", 100.0),
    GO_TO_TOILET("in the toilet", 10.0),
    TALK_ABOUT_SUPERHEROS("talking about superheros", 1.0),
    FALL_IN_LOVE("in love", -50.0);
    private final String action;
    private final double profitPerHour;
    Task(String action, double profitPerHour) {
        this.action = action;
        this.profitPerHour = profitPerHour;
    }
    public String getAction() {
        return action;
    }
    public double getProfitPerHour() {
        return profitPerHour;
    }
}


We'll use the Task enum to make our male programmer do different things. If he develops software, he'll be as productive as $100.00 per hour. If he goes to the toilet, he might still be thinking about software, so he'll be as productive as $10.00 per hour. If he talks about superheros, at least he'll be using his brain, that's why we assume he'll be as productive as $1.00 per hour. On the other hand, whenever he falls in love, his mind will be completely useless. He'll introduce bugs, will forget to test new functionalities or will carry out needless refactors, so his employer will surely lose $50.00 per hour.

Now, we'd like to define default values for the arguments of the performTask method of the MaleProgrammer class. Let's see first how we could do it with method overloading:

The Traditional, Common Way
package com.boundsofjava.newsletter.defaultargument;
/**
 * Overloads for MaleProgrammer.
 */
public class MethodOverloadingMaleProgrammer extends MaleProgrammer {
    /**
     * Puts this male programmer to perform the given task for the specified duration
     * and prints how profitable it was to hire him.
     * It is assumed that there are no women nearby.
     *
     * @param task          Task to be performed by this male programmer
     * @param durationHours Duration of the task
     */
    public void performTask(Task task, int durationHours) {
        // Method overloading approach weakness: we choose to default womenNearby to 0.
        // As durationsHours is also an int, we cannot overload this method with a default 
        // value for it, while also allowing womenNearby as an argument. We have to choose
        // which argument to give a default value and which argument to expose to the user.
        super.performTask(task, durationHours, 0);
    }
    /**
     * Puts this male programmer to develop software for the specified duration
     * and prints how profitable it was to hire him.
     * It is assumed that there are no women nearby.
     *
     * @param durationHours Duration of software development task
     */
    public void performTask(int durationHours) {
        // Same weakness here: we default task to Task.DEVELOP_SOFTWARE and choose to expose 
        // durationHours to the user, while womenNearby defaults to 0 by means of delegation.
        this.performTask(Task.DEVELOP_SOFTWARE, durationHours);
        // or this.performTask(durationHours, 0)
    }
    /**
     * Puts this male programmer to develop software for the specified duration
     * and prints how profitable it was to hire him.
     * Total profit is lowered if there are women nearby.
     *
     * @param durationHours Duration of the task
     * @param womenNearby   Number of women nearby
     */
    public void performTask(int durationHours, int womenNearby) {
        super.performTask(Task.DEVELOP_SOFTWARE, durationHours, womenNearby);
    }
    /**
     * Puts this male programmer to perform the given task for a default duration of 8 hours
     * and prints how profitable it was to hire him.
     * It is assumed that there are no women nearby.
     *
     * @param task Task to be performed by this male programmer
     */
    public void performTask(Task task) {
        this.performTask(task, 8);
    }
    /**
     * Puts this male programmer to develop software for a default duration of 8 hours
     * and prints how profitable it was to hire him.
     * It is assumed that there are no women nearby.
     */
    public void performTask() {
        this.performTask(Task.DEVELOP_SOFTWARE);
        // or this.performTask(8)
    }
}


Overloads and defaults are explained in the comments. The only thing I would like to note is that instead of placing the overloaded methods in the MaleProgrammer class, I've decided to place them in a subclass. This is to keep our MaleProgrammer class clean.

As commented above, this approach has some drawbacks:

The performTask method has 5 overloads.
We cannot have default values for every possible combination of arguments. For example, we have defined that the performTask(Task, int) overloaded method receives durationHours in its second argument. As a result, we cannot define a default value for womenNearby while also receiving a Task. (In this special case, we could swap the arguments, i.e. performTask(int, Task), but this isn't a final solution and we might not be able to always do it).
Again, default values for arguments are defined inside the overloaded methods, so they are global to every caller. It would be nice to take the method invocation's context into account.
I have always disliked this way to handle default arguments, it feels so hacky... We could do it much better with a little help from functional programming...

The Functional Way
Since Java 8, any method can be referenced by means of a functional interface, no matter its return type or the number and type of its arguments.

For example, given the following method:

class SomeClass {
    B someMethod(A a) {
        // Create an instance of B that
        // depends on a and return it
        return b;
    }
}


We can get a reference to it using the Function interface:

SomeClass someClass = new SomeClass();
Function<A, B> reference = a -> someClass.someMethod(a);


Then, we can invoke the someMethod method through our reference function, as follows:

B b = reference.apply(a);


If the return type of our method is void:

class SomeClass {
    void otherMethod(A a) {
        // Do something with a
    }
}


We still can get a reference to it, via the Consumer interface:

SomeClass someClass = new SomeClass();
Consumer<A> reference = a -> someClass.otherMethod(a);


And then, we can invoke it through our consumer:

reference.accept(a);
If our method takes no arguments and returns an instance of B:

class SomeClass {
    B noArgsMethod() {
        // Create and return an instance of B
        return b;
    }
}


We should use the Supplier interface:

SomeClass someClass = new SomeClass();
Supplier<B> reference = () -> someClass.noArgsMethod();


Invocation would be as follows:

B b = reference.get();


If our method is just an action, i.e. it receives no arguments and its return type is void:

class SomeClass {
    void actionMethod() {
        // Just do something
    }
}


One possibility is to use the Runnable interface:

SomeClass someClass = new SomeClass();
Runnable reference = () -> someClass.actionMethod();


We then can invoke the action method this way:

reference.run();


Of course, our method might receive multiple arguments and either be void or return a value of some type.

Back to our MaleProgrammer class:

public class MaleProgrammer {
    public void performTask(Task task, int durationHours, int womenNearby) {
        // Perform given task, calculate and print profit
    }
}


We'd like to get a reference to the void performTask(Task, int, int) method. But in Java, there's no built-in functional interface with a single abstract method (SAM) that takes three arguments and returns void.

Nonetheless, we could combine the Functional and Consumer interfaces seen above, by applying a functional technique known as currying:

MaleProgrammer maleProgrammer = new MaleProgrammer();
Function<Task, Function<Integer, Consumer<Integer>>> reference = 
    task -> duration -> women -> maleProgrammer.performTask(task, duration, women);


From the link above, «currying is the technique of translating the evaluation of a function that takes multiple arguments (...) into evaluating a sequence of functions, each with a single argument.»

Then, we could invoke the void performTask(Task, int, int) method through the reference function as follows:

reference.apply(Task.DEVELOP_SOFTWARE).apply(8).apply(1);


Here reference is a currified function that allows us to apply arguments one by one. If you're wondering what all this has to do with default arguments, the answer is that it will give us the opportunity to intercept the method invocation and play with the arguments.

But first, let's declare a functional interface that will help us with the extremely long generic type of the currified function:

package com.boundsofjava.newsletter.defaultargument;
import java.util.function.Consumer;
import java.util.function.Function;
@FunctionalInterface
public interface DefaultArguments<A, B, C> extends Function<A, Function<B, Consumer<C>>> {
    default void invoke(A a, B b, C c) {
        this.apply(a).apply(b).accept(c);
    }
}


If we replace the Function<Task, Function<Integer, Consumer<Integer>>>type with DefaultArguments<Task, Integer, Integer>, the declaration ofreference given above would now become:

DefaultArguments<Task, Integer, Integer> reference = 
    task -> duration -> women -> maleProgrammer.performTask(task, duration, women);


And the invocation would be as simple as:

reference.invoke(Task.DEVELOP_SOFTWARE, 8, 1);


Now, we have all the pieces in place to tackle our problem. Let's add three default methods to our DefaultArguments functional interface:

package com.boundsofjava.newsletter.defaultargument;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
@FunctionalInterface
public interface DefaultArguments<A, B, C> extends Function<A, Function<B, Consumer<C>>> {
    default void invoke(A a, B b, C c) {
        this.apply(a).apply(b).accept(c);
    }
    default DefaultArguments<A, B, C> defaultingFirst(A defaultFirst) {
        return a -> b -> c -> this.invoke(Optional.ofNullable(a).orElse(defaultFirst), b, c);
    }
    default DefaultArguments<A, B, C> defaultingSecond(B defaultSecond) {
        return a -> b -> c -> this.invoke(a, Optional.ofNullable(b).orElse(defaultSecond), c);
    }
    default DefaultArguments<A, B, C> defaultingThird(C defaultThird) {
        return a -> b -> c -> this.invoke(a, b, Optional.ofNullable(c).orElse(defaultThird));
    }
}


The defaultingFirst, defaultingSecond and defaultingThird methods return a new currified function that decorates the enclosing DefaultArguments instance by replacing the corresponding argument with the given default value if the actual, explicit argument is null.

This approach has the following drawbacks:

As null is to be used to specify a default value, it cannot be used as an argument's valid explicit value.
Primitives are not allowed, since only references can be null.
However, now we can have different sets of default arguments for the same method, and use them in different contexts. This is even better than Scala's built-in functionality to define default arguments.

Let's see the DefaultArguments functional interface in action:

package com.boundsofjava.newsletter.defaultargument;
public class DefaultArgumentsExample {
    public void demo() {
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("DEFAULT ARGUMENTS");
        System.out.println("-----------------------------------------");
        // Create a common male programmer
        MaleProgrammer maleProgrammer = new MaleProgrammer();
        // Create reference pointing to performTask method of maleProgrammer instance
        DefaultArguments<Task, Integer, Integer> reference = task -> duration -> women -> 
            maleProgrammer.performTask(task, duration, women);
        // Create new reference with default arguments
        // As our male programmer has just seen a superhero movie, his defaults will be:
        // - 1st argument:          task = talk about superheros
        // - 2nd argument: durationHours = 4
        // - 3rd argument:   womenNearby = 0
        DefaultArguments<Task, Integer, Integer> referenceAfterMovie = reference
            .defaultingFirst(Task.TALK_ABOUT_SUPERHEROS)
            .defaultingSecond(4)
            .defaultingThird(0);
        // Invoke referenceAfterMovie, overriding 1st argument only
        referenceAfterMovie.invoke(Task.DEVELOP_SOFTWARE, null, null);
        // Invoke referenceAfterMovie, overriding 2nd and 3rd arguments
        referenceAfterMovie.invoke(null, 2, 3);
        // Invoke referenceAfterMovie, defaulting all arguments
        referenceAfterMovie.invoke(null, null, null);
        System.out.println();
        // Create another reference with default arguments
        // Our male programmer is working hard now, but he sits next to a woman,
        // so his defaults will be:
        // - 1st argument:          task = develop software
        // - 2nd argument: durationHours = 8
        // - 3rd argument:   womenNearby = 1
        DefaultArguments<Task, Integer, Integer> hardWorkReference = reference
                .defaultingFirst(Task.DEVELOP_SOFTWARE)
                .defaultingSecond(8)
                .defaultingThird(1);
        // Invoke hardWorkReference, overriding 1st and 3rd arguments
        hardWorkReference.invoke(Task.FALL_IN_LOVE, null, 0);
        // Invoke hardWorkReference, overriding 2nd argument only
        hardWorkReference.invoke(null, 12, null);
        // Invoke hardWorkReference, defaulting all arguments
        hardWorkReference.invoke(null, null, null);
    }
}


Executing the demo() method above produces the following output in my laptop:

-----------------------------------------
DEFAULT ARGUMENTS
-----------------------------------------
- I've been developing software for 4 hours.
  0 women nearby.
  I'm as valuable as $ 400.00.
- I've been talking about superheros for 2 hours.
  3 women nearby.
  I'm as valuable as $ 1.46.
- I've been talking about superheros for 4 hours.
  0 women nearby.
  I'm as valuable as $ 4.00.
- I've been in love for 8 hours.
  0 women nearby.
  I'm as valuable as $ -400.00.
- I've been developing software for 12 hours.
  1 women nearby.
  I'm as valuable as $ 1080.00.
- I've been developing software for 8 hours.
  1 women nearby.
  I'm as valuable as $ 720.00.


All the code shown here is available in a GitHub repo. Please contact me if you've found an issue.

I hope you've enjoyed this (again) long newsletter. I'll do my best to keep the next one shorter.

Regards,
fps.-

Sign up for the Newsletter
