package application;

public class Passenger extends Person {
	private long passport;
	public static final int WINDOW = 0;
	public static final int AISLE = 1;
	public static final int NONE = 2;
	private int seatPrif = NONE;

	public Passenger(String name, long passport, int seatPrif) {
		super(name);
		this.passport = passport;
		this.seatPrif = seatPrif;
	}

	@Override
	public String toString() {
		if (seatPrif == WINDOW)
			return super.toString() + "\tPassport: " + passport + "\tSeat Prifrence: Window";
		else if (seatPrif == AISLE)
			return super.toString() + "\tPassport: " + passport + "\tSeat Prifrence: Aisle";
		else
			return super.toString() + "\tPassport: " + passport + "\tSeat Prifrence: None";

	}

	public int getSeatPrif() {
		return seatPrif;
	}

	public void setSeatPrif(int seatPrif) {
		this.seatPrif = seatPrif;
	}

	public long getPassport() {
		return passport;
	}

}
