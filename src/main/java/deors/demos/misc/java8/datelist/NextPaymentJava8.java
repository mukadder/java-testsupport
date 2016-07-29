package deors.demos.misc.java8.datelist;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public final class NextPaymentJava8 {

    static String result;

    private NextPaymentJava8() {
        super();
    }

    public static void main(String[] args) {

        MortgagePlanJava8 plan = new MortgagePlanJava8();
        plan.setTotalAmount(new BigDecimal(125_000));
        for (int i = 1; i < 13; i++) {
            plan.addPlanDate(LocalDate.of(2014, i, 15));
        }

        // find out the next payment
        LocalDate today = LocalDate.now();
//        LocalDate today = LocalDate.of(2020, 1, 1);
        Optional<LocalDate> nextPayment = plan.getPlanDates().stream().
            filter(d -> d.isAfter(today)).sorted().findFirst();

        if (nextPayment.isPresent()) {
            result = "The next payment is due for: " + nextPayment.get();
        } else {
            result = "No more payments expected";
        }
        System.out.println(result);
    }
}