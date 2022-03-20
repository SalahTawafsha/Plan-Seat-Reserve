package application;

public class FirstClass extends Seat {

	public FirstClass(String seatNum) {
		super(seatNum);
	}

	@Override
	public String toString() {
		return "First Class: " + super.toString();
	}

}
