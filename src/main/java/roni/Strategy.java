package roni;
public enum Strategy {
	  AGGRESIVE {
	    @Override
	   public boolean isRiskAcceptable(double percentOfAssets, double riskiness) {
	      return percentOfAssets * riskiness < 0.25;
	    }
	  },
	  CONSERVATIVE {
	    @Override
	  public  boolean isRiskAcceptable(double percentOfAssets, double riskiness) {
	      return riskiness < 0.25;
	    }
	  };
	 public abstract boolean isRiskAcceptable(double percentOfAssets, double riskiness);
	}
