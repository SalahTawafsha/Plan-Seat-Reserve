package application;

public abstract class Seat implements Comparable<Seat> {
	private int rowNum;
	private int columnNum;
	private String seatNum;
	private Passenger passenger;

	protected Seat(String seatNum) {
		super();
		setSeatNum(seatNum);
		setRowNum();
		setColumnNum();
	}

	private void setRowNum() {
		rowNum = seatNum.indexOf(1, seatNum.length() - 1);
	}

	private void setColumnNum() {
		columnNum = seatNum.charAt(0) - 64;
	}

	@Override
	public String toString() {
		return "Seat Number: " + seatNum + "\tPassenger: " + passenger;
	}

	public String getSeatNum() {
		return seatNum;
	}

	public void setSeatNum(String seatNum) throws IllegalArgumentException {
		char c = seatNum.charAt(0);
		int x = Integer.parseInt(seatNum.substring(1));

		if (x > 0 && x <= 31 && c < 'G' && c >= 'A' && x != 4 && x != 5 && x != 13) {
			if (this instanceof FirstClass && (c == 'A' || c == 'C' || c == 'D' || c == 'F'))
				this.seatNum = seatNum;
			else if (this instanceof EconomyClass && x > 5)
				this.seatNum = seatNum;
			else if (this instanceof FirstClass)
				throw new IllegalArgumentException("Seat " + seatNum + " is NOT valid as First class");
			else
				throw new IllegalArgumentException("Seat " + seatNum + " is NOT valid as Economy class");

		} else
			throw new IllegalArgumentException("Seat " + seatNum + " is NOT valid");

	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public int getRowNum() {
		return rowNum;
	}

	public int getColumnNum() {
		return columnNum;
	}

	@Override
	public int compareTo(Seat o) {
		char c = seatNum.charAt(0);
		char c1 = o.getSeatNum().charAt(0);
		int x = Integer.parseInt(seatNum.substring(1, seatNum.length() - 1));
		int x1 = Integer.parseInt(o.getSeatNum().substring(1, seatNum.length() - 1));
		if (x < x1)
			return -1;
		else if (x > x1)
			return 1;
		else if (c < c1)
			return -1;
		else if (c > c1)
			return 1;

		return 0;
	}

}
