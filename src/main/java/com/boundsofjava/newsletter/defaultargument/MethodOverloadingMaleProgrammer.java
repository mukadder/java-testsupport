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
