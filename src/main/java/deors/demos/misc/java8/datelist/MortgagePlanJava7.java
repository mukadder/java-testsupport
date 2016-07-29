package deors.demos.misc.java8.datelist;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MortgagePlanJava7 {

    private BigDecimal totalAmount = BigDecimal.ZERO;
    private List<Date> planDates = new ArrayList<>();

    public MortgagePlanJava7() {
        super();
    }

    public MortgagePlanJava7(BigDecimal totalAmount, List<Date> planDates) {
        this();
        this.totalAmount = totalAmount;
        this.planDates = planDates;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Date> getPlanDates() {
        return planDates;
    }

    public void setPlanDates(List<Date> planDates) {
        this.planDates = planDates;
    }

    public void addPlanDate(Date planDate) {
        this.planDates.add(planDate);
    }
}