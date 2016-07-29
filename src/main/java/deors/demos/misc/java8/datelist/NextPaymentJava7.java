package deors.demos.misc.java8.datelist;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public final class NextPaymentJava7 {

    static String result;

    private NextPaymentJava7() {
        super();
    }

    public static void main(String[] args) {

        MortgagePlanJava7 plan = new MortgagePlanJava7();
        plan.setTotalAmount(new BigDecimal(125_000));
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2014);
        c.set(Calendar.DAY_OF_MONTH, 15);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        for (int i = 0; i < 12; i++) {
            c.set(Calendar.MONTH, i);
            plan.addPlanDate(c.getTime());
        }

        // find out the next payment
        List<Date> futures = new ArrayList<>();
        Calendar today = Calendar.getInstance();
//        today.set(Calendar.YEAR, 2020);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        today.add(Calendar.DAY_OF_MONTH, 1);
        today.add(Calendar.MILLISECOND, -1);
        for (Date d : plan.getPlanDates()) {
            if (d.after(today.getTime())) {
                futures.add(d);
            }
        }

        // sort ascending order
        Collections.sort(futures);

        if (futures.isEmpty()) {
            result = "No more payments expected";
        } else {
            Date nextPayment = futures.get(0);
            result = "The next payment is due for: " + nextPayment;
        }
        System.out.println(result);
    }
}