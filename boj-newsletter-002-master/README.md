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
In this article, we will discuss how to design a fluent API in Java. The term Fluent interface was coined by Martin Fowler and Eric Evans. Fluent API means to build an API in such way so that it meets the following criteria:

The API user can understand the API very easily.
The API can perform a series of actions in order to finish a task. In Java, we can do it with a series of method calls (chaining of methods).
Each method's name should be domain-specific terminology.
The API should be suggestive enough to guide API users on what to do next and what possible operations users can take at a particular moment.
Suppose you want to design an API for a domain, say Retail, so there should be some common terminology that exists in the Retail domain, and for a certain context (Task), it will take a series of actions to finish this task. Say for an invoice generation, it has to follow certain steps.

Now, when you design an API, you should design it such a way that when API users call Billing Service for invoice generation, the users can fluently perform each step in order to complete said generation, and the API will assist users to perform those steps upon invoking Billing Service.

When an API method is invoked by a user, the method will perform its task and returns a Domain Object, which will assist in what to do next — until all steps are executed. Unlike a standard API, it is the API user's job to call API methods in a sequential way to successfully performs a task. So the API users have to know about the service steps very well.

Design A Fluent API:

Image title



Example: Suppose we want to design an API for a restaurant.

As a customer of this restaurant, one should follow these steps:

Image title

In a standard API design, we will do the following:

Create a "Restaurant" interface.
Create an implementation class of Restaurant. Compose the Menucard class into it.
Create getters and setters for restaurant properties like name, address, etc.
In MenuCard, maintain a list of Items. Expose some methods, like showmenu(), Ordermenu(), etc.
Each Item has name and cost properties and corresponding getters/setters.
When the API user calls this API he/she will call a sequence of methods(Enter Restaurant, call showMenu(), then Ordermenu(), etc.) to perform the above steps shown in the picture.
So it is not fluent. A lot of sequential statements need to be performed to complete the task, and the API user has to know the sequence.



Now I will show you how we will design a fluent API.

1. Create an interface IResturant which has two methods

a. Print name of the resturant,notice the return type  it returns itself , because after displaying name user wants to see menu card.

b. show() method returns menucard .

Thing to notice : Resturent Implementation is suggestive as it has two methods one is name and another show (next operation user wants to perform)

2.  IMenu Implementation has 4 important methods showmenu(),order(),eat(),pay() -all methods return MenuHandler implementation  so we can perform one of these action. Again this is Suggestive.

Code Implementation :

Java
package com.example.fluentapi.contract;
public interface IResturant {
       public IResturant name(String name);
       public IMenu show();
}
package com.example.fluentapi.contract;
public interface IMenu{
    public IMenu order(int index);
    public IMenu eat();
    public IMenu pay();
    public IItem get(int index);
}
package com.example.fluentapi.contract;
public interface IItem {
    public IItem name();
    public Integer cost();
}


Implementation
package com.example.fluentapi.impl;
import com.example.fluentapi.contract.IMenu;
import com.example.fluentapi.contract.IResturant;
public class Arsalan implements IResturant{
    String name;
    String IMenu;
    public IResturant name(String name) {
		this.name=name;
		System.out.println("Enter to hotel :: " + name);
		return this;
    }
    public IMenu show() {
        // TODO Auto-generated method stub
        ArsalanMenuHandler handler = new ArsalanMenuHandler();
        handler.showMenu();
        return handler;
    }
}
package com.example.fluentapi.impl;
import java.util.ArrayList;
import java.util.List;
import com.example.fluentapi.contract.IItem;
import com.example.fluentapi.contract.IMenu;
public class ArsalanMenuHandler implements IMenu{
    List<IItem> menuList = new ArrayList<IItem>();
    List<IItem> selectedList = new ArrayList<IItem>();
    public ArsalanMenuHandler()
    {
        IItem biriyani = new IItem(){
            public IItem name()
            {
                System.out.println("Mutton Biriyani");
                return this;
            }
            public Integer cost()
            {
                return 180;
            }
        };
        IItem muttonChap = new IItem(){
            public IItem name()
            {
                System.out.println("Mutton Chap");
                return this;
            }
            public Integer cost()
            {
                return 160;
            }
        };
        IItem firni = new IItem(){
            public IItem name()
            {
                System.out.println("Firni");
                return this;
            }
            public Integer cost()
                {
                    return 100;
                }                                                                                            
            };
            menuList.add(biriyani);
            menuList.add(muttonChap);
            menuList.add(firni);
        }
        public IMenu order(int index) {
            // TODO Auto-generated method stub
            IItem item (index);
            selectedList.add(item);
            System.out.println("Order given ::");
            item.name();
            return this;
        }
        public IMenu eat() {
            for(IItem item : selectedList)
            {
                System.out.println("eating ");
                item.name();
            }
            return this;
        }
        public IMenu pay() {
            int cost=0;
            for(IItem item : selectedList)
            {
                cost = cost + item.cost();
            }
        System.out.println("Paying Rupees" + cost);
        return this;
    }
    @Override
    public IItem get(int index) {
        // TODO Auto-generated method stub
        if(index <3)
        {
            return menuList.get(index);
        }
        return null;
    }   
    public void showMenu(){
        System.out.println("MENU IN ARSALAN");
        for(IItem item : menuList)
        {                                          
            item.name();                                             
        }                             
    }
}


Test Fluent API
package com.example.fluentapi.impl;
public class FluentApiTest {
    publicstaticvoid main(String[] args) {
        new Arsalan().name("ARSALAN").show().order(0).order(1).eat().pay();
    }
}


Output:
Enter to hotel :: ARSALAN
MENU IN ARSALAN
Mutton Biriyani
Mutton Chap
Firni
Order given ::
Mutton Biriyani
Order given ::
Mutton Chap
eating
Mutton Biriyani
eating
Mutton Chap
Paying Ruppes340


Look How we perform the steps fluently by calling a series of methods.

new Arsalan().name("ARSALAN").show().order(0).order(1).eat().pay();

This is a test example. To make it more polished we need to work on the following:


