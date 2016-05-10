package roni;

import java.util.Map;

class Account {
  private Strategy strategy;
  private Map<String, Double> investments;

  boolean addInvestment(String symbol, double fraction, double riskiness) {
    if (strategy.isRiskAcceptable(fraction, riskiness)) {
      investments.put(symbol, fraction);
      return true;
    }
    return false;
  }
}