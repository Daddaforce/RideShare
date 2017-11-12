package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import model.Ride;
import model.Trip;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by Chris on 5/04/2017.
 */
public class RideController implements Initializable{

    @FXML
    private ComboBox<Integer> noOfSeatsCombo;
    @FXML
    private ComboBox<Trip> useTripCombo;

    private ObservableList<Trip> tripList = observableArrayList();
    private ObservableList<Integer> noOfSeats = observableArrayList();
    private Trip sharedTrip;
    private int noOfSeatsAvailable;
    private Ride ride;
    ArrayList<Trip> trips = Main.getRideShare().getTrips();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ride = new Ride();
        useTripCombo.setItems(tripList);
        noOfSeatsCombo.setItems(noOfSeats);
        for(Trip trip: trips) {
            if(trip.getUsername().equals(Main.getUsername())) {
                tripList.addAll(trip);
            }
        }
    }

    public void backButton() throws IOException {
        FXMLLoader driverLoader = new FXMLLoader(getClass().getClassLoader().getResource("driver.fxml"));
        Parent driver = driverLoader.load();
        Scene driverWindow = new Scene(driver, 700, 500);
        Main.getStage().setScene(driverWindow);
    }

    public void shareRideButton() throws IOException {
        for(Trip trip : trips) {
            if(trip.equals(sharedTrip)) {
                ride.addTrip(trip);
            }
        }
        ride.addNoOfSeats(noOfSeatsAvailable);
        ride.setName(sharedTrip.getName());
        ride.setUsername();
        ride.setStatus("Confirmed");
        Main.getRideShare().addRide(ride);
        backButton();
    }

    public void numberOfSeats() {
        noOfSeatsAvailable = noOfSeatsCombo.getSelectionModel().getSelectedItem();
    }

    public void useTrip() {
        sharedTrip = useTripCombo.getSelectionModel().getSelectedItem();
        for(Trip trip : trips) {
            if(trip.equals(sharedTrip)) {
                for(int i = 1; i<=trip.getTripCar().getNoOfSeats(); i++) {
                    noOfSeats.add(i);
                }
            }
        }


    }
}
