package application;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Trip implements Comparable<Trip> {
	private Seat[][] seats = new Seat[32][];
	private String tripNum;
	private String from;
	private String to;
	private GregorianCalendar date;

	public Trip(String tripNum, String from, String to, GregorianCalendar date) {
		setSeats();
		this.tripNum = tripNum;
		this.from = from;
		this.to = to;
		this.date = date;
	}

	public Seat[][] getSeats() {
		return seats;
	}

	public String getSeatsFormated() {
		String s = new String();

		for (int i = 0; i < seats.length; i++) {
			for (int j = 0; j < seats[i].length; j++) {
				if (seats[i][j].getPassenger() != null)
					s += seats[i][j] + "\n";
			}
		}

		return s;
	}

	private void setSeats() {
		seats[0] = new Seat[0];
		for (int i = 1; i < 4; i++)
			seats[i] = new Seat[4];

		for (int i = 4; i < seats.length; i++)
			if (i != 13 && i > 5)
				seats[i] = new Seat[6];
			else
				seats[i] = new Seat[0];

		for (int i = 1; i < 4; i++)
			for (int j = 0; j < seats[i].length; j++)
				if (j == 0)
					seats[i][j] = new FirstClass("A" + i);
				else if (j == 1)
					seats[i][j] = new FirstClass("C" + i);
				else if (j == 2)
					seats[i][j] = new FirstClass("D" + i);
				else
					seats[i][j] = new FirstClass("F" + i);

		seats[4] = new Seat[0];
		seats[5] = new Seat[0];

		for (int i = 6; i < seats.length; i++)
			for (int j = 0; j < seats[i].length; j++) {

				seats[i][j] = new EconomyClass(Character.toString(j + 'A') + i);
			}

	}

	public void reserveSeat(String seatNum, Passenger passenger) throws NoSuchFieldException {
		for (int i = 0; i < seats.length; i++)
			for (int j = 0; j < seats[i].length; j++)
				if (seats[i][j].getSeatNum().equals(seatNum) && isSeatEmpty(seatNum)) {
					seats[i][j].setPassenger(passenger);
					break;
				} else if (seats[i][j].getSeatNum().equals(seatNum) && !isSeatEmpty(seatNum))
					throw new NoSuchFieldException("Seat " + seatNum + " is NOT Empty !!");

	}

	public boolean isSeatEmpty(String seatNum) {
		for (int i = 0; i < seats.length; i++) {
			for (int j = 0; j < seats[i].length; j++) {
				if (seats[i][j].getSeatNum().equals(seatNum) && seats[i][j].getPassenger() == null)
					return true;
				else if (seats[i][j].getSeatNum().equals(seatNum))
					return false;
			}
		}
		return false;
	}

	public Seat getSeat(String seatNum) throws IllegalArgumentException {
		for (int i = 0; i < seats.length; i++) {
			for (int j = 0; j < seats[i].length; j++) {
				if (seats[i][j].getSeatNum().equals(seatNum))
					return seats[i][j];
			}
		}
		throw new IllegalArgumentException("The seat " + seatNum + " is NOT define");
	}

	@Override
	public String toString() {
		return "Trip Number: " + tripNum + "\tFrom: " + from + "\tTo: " + to + "\tDate: "
				+ date.get(Calendar.DAY_OF_MONTH) + "/" + date.get(Calendar.MONTH) + "/" + date.get(Calendar.YEAR)
				+ "\nReseved Seats:\n" + getSeatsFormated()
				+ "*********************************************************************************************************************";
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public GregorianCalendar getDate() {
		return date;
	}

	public void setDate(GregorianCalendar date) {
		this.date = date;
	}

	public String getTripNum() {
		return tripNum;
	}

	@Override
	public int compareTo(Trip o) {
		return this.tripNum.compareTo(o.getTripNum());
	}

}
