package com.example.annotations;

import java.lang.annotation.Repeatable;

@Repeatable(Schedules.class)

public @interface Schedule {
    String dayOfMonth() default "01";

    String dayOfWeek() default "Monday";

    String hour() default "00:00";
}
