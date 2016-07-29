package deors.demos.misc.java8.datelist;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MortgagePlanJava8 {

    private BigDecimal totalAmount = BigDecimal.ZERO;
    private List<LocalDate> planDates = new ArrayList<>();

    public MortgagePlanJava8() {
        super();
    }

    public MortgagePlanJava8(BigDecimal totalAmount, List<LocalDate> planDates) {
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

    public List<LocalDate> getPlanDates() {
        return planDates;
    }

    public void setPlanDates(List<LocalDate> planDates) {
        this.planDates = planDates;
    }

    public void addPlanDate(LocalDate planDate) {
        this.planDates.add(planDate);
    }
}