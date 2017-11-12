package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Account;
import model.Booking;
import model.Car;
import model.Ride;
import utilities.PasswordAuthentication;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by Chris on 26/05/2017.
 */
public class AccountController implements Initializable{

    @FXML
    private Button passengerButton;
    @FXML
    private Button driverButton;
    @FXML
    private Button loginButton;
    @FXML
    private ListView<String> showBookingList;

    private ArrayList<Booking> booking = new ArrayList<>();
    private ArrayList<Ride> ride = new ArrayList<>();
    private String bookingRide;
    private Account account;
    private static Stage dialog;
    private Ride currentlySelectedRide;
    private boolean rideSelected = false;
    private ObservableList<String> bookingDetails = observableArrayList();
    private String yourRide = "Your ride: ";
    private String yourBooking = "Your booking: ";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showBookingList.setItems(bookingDetails);
        NotificationController newNotification = new NotificationController();
        for(Account account : Main.getRideShare().getAccounts()) {
            if(account.getUsername().equals(Main.getUsername())) {
                this.account = account;
                if(account.getIsDriver()) {
                    driverButton.setVisible(true);
                    newNotification.setLicenceExpiry(account.getLicenceExpiry());//licenceExpiry = account.getLicenceExpiry();

                    for(Car car : Main.getRideShare().getCars()) {
                        if(car.getUsername().equals(Main.getUsername())) {
                            newNotification.setWOFExpiry(car.getWOFExpiryTime());
                            newNotification.setRegistrationExpiry(car.getRegistrationExpiryTime());
                            newNotification.setCarExists();
                        }
                    }
                    newNotification.checkExpiry();
                }
            }
        }
        newNotification.checkIfRideCancelled();
        newNotification.checkIfBookingCancelled();
        newNotification.showAlert();
        updateBookingList();
    }


    private void updateBookingList() {
        booking.clear();
        ride.clear();
        bookingDetails.clear();
        for (Booking booking : Main.getRideShare().getBookings()) {
            if (booking.getUsername().equals(Main.getUsername())) {
                this.booking.add(booking);
            }
        }
        sortBookings(booking);
        for (Booking booking : booking) {
            bookingDetails.add(yourBooking + booking.toString());
        }
        for (Ride ride : Main.getRideShare().getRides()) {
            if (ride.getUsername().equals(Main.getUsername())) {
                this.ride.add(ride);
            }
        }
        sortRides(ride);
        for (Ride ride : ride) {
            bookingDetails.add(yourRide + ride.toString());
        }
    }

    public void isPassenger() throws IOException {
        FXMLLoader passengerLoader = new FXMLLoader(getClass().getClassLoader().getResource("booking.fxml"));
        Parent passenger = passengerLoader.load();
        Scene passengerWindow = new Scene(passenger, 1100, 500);
        Main.getStage().setScene(passengerWindow);
    }

    public void isDriver() throws IOException {
        FXMLLoader driverLoader = new FXMLLoader(getClass().getClassLoader().getResource("driver.fxml"));
        Parent driver = driverLoader.load();
        Scene driverWindow = new Scene(driver, 700, 500);
        Main.getStage().setScene(driverWindow);
    }

    public void logout() throws IOException {
        FXMLLoader startLoader = new FXMLLoader(getClass().getClassLoader().getResource("main.fxml"));
        Parent start = startLoader.load();
        Scene startWindow = new Scene(start, 700, 500);
        Main.getStage().setScene(startWindow);
        SerializationController.save();
    }

    private void sortBookings(ArrayList<Booking> booking) {
        if (booking.size() > 0) {
            Collections.sort(booking, new Comparator<Booking>() {
                @Override
                public int compare(final Booking object1, final Booking object2) {
                    return object1.getRide().getTrip().getStartDate().compareTo(object2.getRide().getTrip().getStartDate());
                }
            });
        }
    }

    private void sortRides(ArrayList<Ride> Ride) {
        if (ride.size() > 0) {
            Collections.sort(ride, new Comparator<Ride>() {
                @Override
                public int compare(final Ride object1, final Ride object2) {
                    return object1.getTrip().getStartDate().compareTo(object2.getTrip().getStartDate());
                }
            });
        }
    }

    public void editAccount() {
    }


    public void cancelRide() {
        try {
            getSelectedBooking();
            if (rideSelected) {
                if (bookingRide.contains(yourRide)) {
                    int rideIndex = showBookingList.getSelectionModel().getSelectedIndex();
                    int bookingSize = booking.size();
                    rideIndex -= bookingSize;
                    Ride checkRide = this.ride.get(rideIndex);
                    for (Ride ride : Main.getRideShare().getRides()) {
                        if (ride.equals(checkRide)) {
                            String path = JOptionPane.showInputDialog("Enter a reason for cancelling: ");
                            if (!path.isEmpty()) {
                                ride.setReason(path);
                                ride.setStatus("Driver Cancelled");
                                ride.cancelRide();

                                for (Booking booking : Main.getRideShare().getBookings()) {
                                    if (booking.getRide().equals(ride)) {
                                        booking.setStatus("Driver Cancelled");
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "You need to enter a reason for cancelling.");
                            }
                        }
                    }
                } else if (bookingRide.contains(yourBooking)) {
                    int bookingIndex = showBookingList.getSelectionModel().getSelectedIndex();
                    Booking checkBooking = this.booking.get(bookingIndex);
                    for(Booking booking : Main.getRideShare().getBookings()) {
                        if(booking.equals(checkBooking)) {
                            String path = JOptionPane.showInputDialog("Enter a reason for cancelling: ");
                            if(!path.isEmpty()) {
                                booking.setStatus("User Cancelled");
                                booking.setReason(path);

                                for(Ride ride : Main.getRideShare().getRides()) {
                                    if(ride.equals(booking.getRide())) {
                                        ride.releaseSeat();
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "You need to enter a reason for cancelling.");
                            }
                        }
                    }
                }
                updateBookingList();
            }
        } catch(NullPointerException e) {}
    }

    private void getSelectedBooking() {
        bookingRide = showBookingList.getSelectionModel().getSelectedItem();
        if(bookingRide.contains(yourRide)) {
            int rideIndex = showBookingList.getSelectionModel().getSelectedIndex();
            int bookingSize = booking.size();
            rideIndex -= bookingSize;
            currentlySelectedRide= this.ride.get(rideIndex);
            rideSelected = true;
        } else if(bookingRide.contains(yourBooking)) {
            int bookingIndex = showBookingList.getSelectionModel().getSelectedIndex();
            currentlySelectedRide = this.booking.get(bookingIndex).getRide();
            rideSelected = true;
        } else {
            rideSelected = false;
        }
    }

    public void selectedBooking(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2) {
            getSelectedBooking();
            try {
                account.setRideSelected(currentlySelectedRide);
                dialog = new Stage();
                dialog.initModality(Modality.WINDOW_MODAL);
                dialog.initOwner(Main.getStage());
                FXMLLoader newRide = new FXMLLoader(getClass().getClassLoader().getResource("rideDetails.fxml"));
                Parent ride = newRide.load();
                Scene dialogScene = new Scene(ride, 420, 500);
                dialog.setScene(dialogScene);
                dialog.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void exitButton() {
        dialog.close();
    }
}
