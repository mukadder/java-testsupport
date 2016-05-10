package roni;
/** Enums have behavior! */
public enum Gesture {
	ROCK() {
		//nitializing specific values to the enum constants

//The enum constants have initial value that starts from 0, 1, 2, 3 and so on. 
		//But we can initialize the specific value to the enum constants by defining fields and constructors. As specified earlier, Enum can have fields, constructors and methods.
		// Enums are polymorphic, that's really handy!
		@Override
		public boolean beats(Gesture other) {
			return other == SCISSORS;
		}
	},
	PAPER, SCISSORS;

	// we can implement with the integer representation
	public boolean beats(Gesture other) {
		return ordinal() - other.ordinal() == 1;
	}
}