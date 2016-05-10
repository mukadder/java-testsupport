package roni;
public enum Multiplier {
    DOUBLE(2.0),
    TRIPLE(3.0);

    private final double multiplier;

    Multiplier(double multiplier) {
        this.multiplier = multiplier;
    }

   public Double applyMultiplier(Double value) {
        return multiplier * value;
    }

}