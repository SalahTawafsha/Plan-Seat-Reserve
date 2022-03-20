package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

	private static ArrayList<Trip> trips = new ArrayList<>();

	private Alert error = new Alert(AlertType.ERROR);
	private Alert success = new Alert(AlertType.INFORMATION);

	@Override
	public void start(Stage primaryStage) {
		readTripFromFile();
		readPassengerFromFile();

		primaryStage.setTitle("Air Plane System");
		primaryStage.getIcons().add(new Image("https://img.icons8.com/color/344/airport-building.png"));
		primaryStage.setResizable(false);

		mainInterface(primaryStage);
	}

	private void mainInterface(Stage primaryStage) {
		Button addTrip = new Button("Add new trip");
		addTrip.setMinHeight(70);
		addTrip.setMinWidth(210);
		addTrip.setStyle("-fx-background-radius: 50");

		Button reserve = new Button("Reserve a seat");
		reserve.setMinHeight(70);
		reserve.setMinWidth(210);
		reserve.setStyle("-fx-background-radius: 50");

		Button find = new Button("Display info by seat num");
		find.setMinHeight(70);
		find.setMinWidth(210);
		find.setStyle("-fx-background-radius: 50");

		Button display = new Button("Print all information");
		display.setMinHeight(70);
		display.setMinWidth(210);
		display.setStyle("-fx-background-radius: 50");

		VBox box = new VBox(20, addTrip, reserve, find, display);
		box.setAlignment(Pos.CENTER);
		box.setBackground(new Background(
				new BackgroundImage(new Image("https://www.collinsdictionary.com/images/full/airport_324754607.jpg"),
						null, null, null, null)));

		addTrip.setOnAction(e -> addTrip(primaryStage));
		reserve.setOnAction(e -> reserve(primaryStage));
		find.setOnAction(e -> findPass(primaryStage));
		display.setOnAction(e -> display(primaryStage));

		Scene s = new Scene(box, 1000, 600);
		primaryStage.setScene(s);
		primaryStage.show();

	}

	private void findPass(Stage primaryStage) {
		Label tripNumLabel = new Label("Trip Number: ");
		TextField tripNumField = new TextField();

		Label seatNumLabel = new Label("Seat Number: ");
		TextField seatNumField = new TextField();

		Label infoLabel = new Label("Information: ");
		TextArea infoArea = new TextArea();
		infoArea.setEditable(false);
		infoArea.setMaxWidth(500);

		GridPane pane = new GridPane();
		pane.addColumn(0, tripNumLabel, seatNumLabel, infoLabel);
		pane.addColumn(1, tripNumField, seatNumField, infoArea);
		pane.setAlignment(Pos.CENTER);
		pane.setVgap(20);
		pane.setHgap(15);

		Button back = new Button("Back");
		back.setOnAction(e -> mainInterface(primaryStage));
		back.setMinHeight(50);
		back.setMinWidth(75);
		back.setStyle("-fx-background-radius: 50");

		Button find = new Button("Find");
		find.setMinHeight(50);
		find.setMinWidth(75);
		find.setStyle("-fx-background-radius: 50");

		find.setOnAction(e -> {
			try {
				if (!tripNumField.getText().equals("")) {
					int x = Collections.binarySearch(trips, new Trip(tripNumField.getText(), "", "", null));
					if (x >= 0)
						if (!seatNumField.getText().equals(""))
							if (trips.get(x).getSeat(seatNumField.getText()).getPassenger() != null)
								infoArea.setText(
										trips.get(x).getSeat(seatNumField.getText()).getPassenger().toString());
							else
								infoArea.setText("The seat is Empty");
						else {
							error.setContentText("You should Fill all fields");
							error.show();
						}
					else {
						infoArea.setText("This trip is NOT exist");
					}
				} else {
					error.setContentText("You should Fill all fields");
					error.show();
				}
			} catch (IllegalArgumentException e2) {
				error.setContentText(e2.getMessage());
				error.show();
			}

		});

		HBox control = new HBox(10, find, back);
		control.setAlignment(Pos.CENTER);

		VBox all = new VBox(20, pane, control);
		all.setAlignment(Pos.CENTER);
		all.setBackground(new Background(
				new BackgroundImage(new Image("https://www.collinsdictionary.com/images/full/airport_324754607.jpg"),
						null, null, null, null)));

		Scene s = new Scene(all, 1000, 600);
		primaryStage.setScene(s);

	}

	private void display(Stage primaryStage) {
		TextArea infoArea = new TextArea(print());
		infoArea.setEditable(false);
		infoArea.setMaxWidth(650);

		Button back = new Button("Back");
		back.setOnAction(e -> mainInterface(primaryStage));
		back.setMinHeight(50);
		back.setMinWidth(75);
		back.setStyle("-fx-background-radius: 50");

		Button saveToFile = new Button("Save to file");
		saveToFile.setMinHeight(50);
		saveToFile.setMinWidth(75);
		saveToFile.setStyle("-fx-background-radius: 50");

		saveToFile.setOnAction(e -> {
			printToFiles();
		});

		HBox control = new HBox(20, saveToFile, back);
		control.setAlignment(Pos.CENTER);

		VBox box = new VBox(10, infoArea, control);
		box.setAlignment(Pos.CENTER);
		box.setBackground(new Background(
				new BackgroundImage(new Image("https://www.collinsdictionary.com/images/full/airport_324754607.jpg"),
						null, null, null, null)));

		Scene s = new Scene(box, 1000, 600);
		primaryStage.setScene(s);
		primaryStage.show();
	}

	private void printToFiles() {

		for (int i = 0; i < trips.size(); i++) {
			if (trips.get(i) != null) {
				try {
					PrintWriter f = new PrintWriter(trips.get(i).getTripNum() + ".txt");
					f.print(trips.get(i).toString());
					f.close();
				} catch (FileNotFoundException e) {
					error.setContentText(e.getMessage());
					error.show();
				}
			}
		}

	}

	private String print() {
		String s = new String();
		for (int i = 0; i < trips.size(); i++) {
			if (trips.get(i) != null)
				s += trips.get(i).toString() + "\n";
		}
		return s;
	}

	private void reserve(Stage primaryStage) {

		GridPane contentPane = new GridPane();
		Label tripNumLabel = new Label("Trip Number: ");
		TextField tripNumField = new TextField();

		Label nameLabel = new Label("Passenger Name: ");
		TextField nameField = new TextField();

		Label passportNumLabel = new Label("Passport Number: ");
		TextField passportField = new TextField();

		Label seatPrefLabel = new Label("Seat Preference: ");
		seatPrefLabel.setTextFill(Color.WHITE);

		RadioButton window = new RadioButton("Window");
		window.setTextFill(Color.WHITE);

		RadioButton aisle = new RadioButton("Aisle");
		aisle.setTextFill(Color.WHITE);

		RadioButton none = new RadioButton("None");
		none.setTextFill(Color.WHITE);

		HBox seatPrefBox = new HBox(5, window, aisle, none);

		ToggleGroup seatPrefGroup = new ToggleGroup();
		window.setToggleGroup(seatPrefGroup);
		aisle.setToggleGroup(seatPrefGroup);
		none.setToggleGroup(seatPrefGroup);

		Label seatPlaceLabel = new Label("Seat Place: ");
		RadioButton firstClass = new RadioButton("First Class");
		RadioButton economyClass = new RadioButton("Economy Class");
		HBox seatPlaceBox = new HBox(5, firstClass, economyClass);

		ToggleGroup seatPlaceGroup = new ToggleGroup();
		firstClass.setToggleGroup(seatPlaceGroup);
		economyClass.setToggleGroup(seatPlaceGroup);

		contentPane.addColumn(0, tripNumLabel, nameLabel, passportNumLabel, seatPlaceLabel, seatPrefLabel);
		contentPane.addColumn(1, tripNumField, nameField, passportField, seatPlaceBox, seatPrefBox);

		contentPane.setHgap(20);
		contentPane.setVgap(20);
		contentPane.setAlignment(Pos.CENTER);

		HBox controlBox = new HBox();
		Button reserveButton = new Button("Reserve");
		reserveButton.setStyle("-fx-background-radius: 50");

		Button back = new Button("Back");
		back.setStyle("-fx-background-radius: 50");

		reserveButton.setMinHeight(50);
		reserveButton.setMinWidth(75);
		back.setMinHeight(50);
		back.setMinWidth(75);

		controlBox.setSpacing(50);
		controlBox.setAlignment(Pos.CENTER);
		controlBox.getChildren().addAll(reserveButton, back);

		VBox all = new VBox(20, contentPane, controlBox);
		all.setAlignment(Pos.CENTER);
		all.setBackground(new Background(
				new BackgroundImage(new Image("https://www.collinsdictionary.com/images/full/airport_324754607.jpg"),
						null, null, null, null)));

		Scene s = new Scene(all, 1000, 600);
		primaryStage.setScene(s);
		primaryStage.show();

		back.setOnAction(e -> mainInterface(primaryStage));

		reserveButton.setOnAction(e -> {
			try {
				if (!tripNumField.getText().equals("") && !nameField.getText().equals("")
						&& !passportField.getText().equals(""))
					reserve(tripNumField.getText(), nameField.getText(), passportField.getText(),
							((RadioButton) (seatPlaceGroup.getSelectedToggle())).getText(),
							((RadioButton) (seatPrefGroup.getSelectedToggle())).getText());
				else {
					error.setContentText("You shoulf fill all fields");
					error.show();
				}
			} catch (NullPointerException e1) {
				error.setContentText("You Should fill all fields ");
				error.show();
			}

		});

	}

	private void reserve(String tripNum, String name, String passport, String seatPlace, String seatPref) {

		boolean flag = false;

		int tripIndex = Collections.binarySearch(trips, new Trip(tripNum, "", "", null));

		String seat = new String();
		if (tripIndex >= 0)
			try {
				Trip trip = trips.get(tripIndex);
				if (seatPlace.equals("First Class")) {
					if (seatPref.equals("Window")) {
						for (int i = 1; i < 4; i++) {
							if (trip.getSeats()[i][0].getPassenger() == null) {
								seat = Character.toString('A') + i;
								trip.reserveSeat(seat, new Passenger(name, Long.parseLong(passport), 0));
								flag = true;
								break;
							} else if (trip.getSeats()[i][3].getPassenger() == null) {
								seat = Character.toString('F') + i;
								trip.reserveSeat(seat, new Passenger(name, Long.parseLong(passport), 0));
								flag = true;
								break;
							}

						}
					} else if (seatPref.equals("Aisle")) {
						for (int i = 1; i < 4; i++) {
							if (trip.getSeats()[i][1].getPassenger() == null) {
								seat = Character.toString('B') + i;
								trip.reserveSeat(seat, new Passenger(name, Long.parseLong(passport), 1));
								flag = true;
								break;
							} else if (trip.getSeats()[i][2].getPassenger() == null) {
								seat = Character.toString('C') + i;
								trip.reserveSeat(seat, new Passenger(name, Long.parseLong(passport), 1));
								flag = true;
								break;
							}

						}
					} else {
						for (int i = 1; i < 4; i++) {
							for (int j = 0; j < trip.getSeats()[i].length; j++)
								if (trip.getSeats()[i][j].getPassenger() == null) {
									seat = Character.toString(j + 'A') + i;
									trip.reserveSeat(seat, new Passenger(name, Long.parseLong(passport), 2));
									flag = true;
									break;
								}

						}
					}
					if (flag) {
						success.setContentText("Receved and your seat is " + seat);
						success.show();
					} else {
						error.setContentText("There are NOT seat exist as you want");
						error.show();
					}

				} else {
					if (seatPref.equals("Window")) {
						for (int i = 6; i < trip.getSeats().length; i++) {
							if (trip.getSeats()[i][0].getPassenger() == null) {
								seat = Character.toString('A') + i;
								trip.reserveSeat(seat, new Passenger(name, Long.parseLong(passport), 0));
								flag = true;
								break;
							} else if (trip.getSeats()[i][5].getPassenger() == null) {
								seat = Character.toString('F') + i;
								trip.reserveSeat(seat, new Passenger(name, Long.parseLong(passport), 0));
								flag = true;
								break;
							}

						}
						if (flag) {
							success.setContentText("Receved and your seat is " + seat);
							success.show();
						} else {
							error.setContentText("There are NOT seat exist as you want");
							error.show();
						}
					} else if (seatPref.equals("Aisle")) {
						for (int i = 6; i < trip.getSeats().length; i++) {
							if (trip.getSeats()[i][2].getPassenger() == null) {
								seat = Character.toString('C') + i;
								trip.reserveSeat(seat, new Passenger(name, Long.parseLong(passport), 1));
								flag = true;
								break;
							} else if (trip.getSeats()[i][3].getPassenger() == null) {
								seat = Character.toString('D') + i;
								trip.reserveSeat(seat, new Passenger(name, Long.parseLong(passport), 1));
								flag = true;
								break;
							}

						}
						if (flag) {
							success.setContentText("Receved and your seat is " + seat);
							success.show();
						} else {
							error.setContentText("There are NOT seat exist as you want");
							error.show();
						}
					} else {
						for (int i = 6; i < trip.getSeats().length; i++) {
							for (int j = 0; j < trip.getSeats()[i].length; j++)
								if (trip.getSeats()[i][j].getPassenger() == null) {
									seat = Character.toString(j + 'A') + i;
									trip.reserveSeat(seat, new Passenger(name, Long.parseLong(passport), 2));
									flag = true;
									break;
								}

						}
						if (flag) {
							success.setContentText("Receved and your seat is " + seat);
							success.show();
						} else {
							error.setContentText("There are NOT seat exist as you want");
							error.show();
						}
					}

				}
			} catch (NumberFormatException e) {
				error.setContentText("The passport should be just numbers");
				error.show();
			} catch (NoSuchFieldException e) {
				error.setContentText(e.getMessage());
				error.show();
			}
		else {
			error.setContentText("Trip " + tripNum + " is NOT exist");
			error.show();
		}
	}

	private void addTrip(Stage primaryStage) {
		Label numLabel = new Label("Trip Number: ");
		TextField numField = new TextField();

		Label fromabel = new Label("From:: ");
		TextField fromField = new TextField();

		Label toLabel = new Label("To: ");
		TextField toField = new TextField();

		Label dateLabel = new Label("Date: ");
		ComboBox<Integer> day = new ComboBox<>();
		ComboBox<Integer> month = new ComboBox<>();
		ComboBox<Integer> year = new ComboBox<>();
		day.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
				26, 27, 28, 29, 30, 31);
		month.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		year.getItems().addAll(2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030);

		HBox dateBox = new HBox(2, day, month, year);

		GridPane pane = new GridPane();
		pane.addColumn(0, numLabel, fromabel, toLabel, dateLabel);
		pane.addColumn(1, numField, fromField, toField, dateBox);
		pane.setVgap(20);
		pane.setHgap(25);

		pane.setAlignment(Pos.CENTER);

		Button backButton = new Button("Back");
		backButton.setMinHeight(50);
		backButton.setMinWidth(75);
		backButton.setStyle("-fx-background-radius: 50");

		Button addButton = new Button("Add");
		addButton.setMinHeight(50);
		addButton.setMinWidth(75);
		addButton.setStyle("-fx-background-radius: 50");

		backButton.setOnAction(e -> {
			mainInterface(primaryStage);
		});

		addButton.setOnAction(e -> {
			try {
				if (!numField.getText().equals("") && !fromField.getText().equals("")
						&& !toField.getText().equals("")) {
					Trip x = new Trip(numField.getText(), fromField.getText(), toField.getText(),
							new GregorianCalendar(year.getSelectionModel().getSelectedItem(),
									month.getSelectionModel().getSelectedItem(),
									day.getSelectionModel().getSelectedItem()));
					int i = Collections.binarySearch(trips, x);
					if (++i <= 0) {
						trips.add(-i, x);
						success.setContentText("Added !");
						success.show();
					} else {
						error.setContentText("This trip numnber is Already exist");
						error.show();
					}
				} else {
					error.setContentText("You should fill all fields");
					error.show();
				}
			} catch (NullPointerException e1) {
				error.setContentText("You Should fill all fields ");
				error.show();
			}
		});

		HBox control = new HBox(10, addButton, backButton);
		control.setAlignment(Pos.CENTER);

		VBox box = new VBox(20, pane, control);
		box.setAlignment(Pos.CENTER);
		box.setBackground(new Background(
				new BackgroundImage(new Image("https://www.collinsdictionary.com/images/full/airport_324754607.jpg"),
						null, null, null, null)));

		Scene s = new Scene(box, 1000, 600);
		primaryStage.setScene(s);

	}

	private void readPassengerFromFile() {
		try {
			Scanner scan = new Scanner(new File("passengers.txt"));
			while (scan.hasNext()) {
				String s = scan.nextLine();
				String[] arr = s.split("/");
				for (int i = 0; i < trips.size(); i++)
					if (arr[0].equals(trips.get(i).getTripNum()))
						trips.get(i).reserveSeat(arr[3], new Passenger(arr[1], Long.parseLong(arr[2]), Passenger.NONE));
			}
			scan.close();
		} catch (Exception e) {
			error.setContentText(e.getMessage());
			error.show();
		}
	}

	private void readTripFromFile() {
		try {
			Scanner scan = new Scanner(new File("trips.txt"));
			while (scan.hasNext()) {
				String s = scan.nextLine();
				String[] arr = s.split("/");
				for (int i = 0; i < trips.size(); i++)
					if (trips.get(i).getTripNum().equals(arr[0])) {
						error.setContentText("trip " + arr[i] + " is already exist");
						error.show();
					}
				String[] soso = arr[3].split("-");

				trips.add(new Trip(arr[0], arr[1], arr[2], new GregorianCalendar(Integer.parseInt(soso[2]),
						Integer.parseInt(soso[1]), Integer.parseInt("12"))));

			}
			scan.close();
		} catch (Exception e) {
			error.setContentText(e.getMessage());
			error.show();
		}

		Collections.sort(trips);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
