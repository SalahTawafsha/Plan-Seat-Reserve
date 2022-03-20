package application;
public class EconomyClass extends Seat {
	public EconomyClass(String seatNum) {
		super(seatNum);
	}

	@Override
	public String toString() {
		return "Economy Class: " + super.toString();
	}

}
