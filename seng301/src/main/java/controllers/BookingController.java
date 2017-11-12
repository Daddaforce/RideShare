package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jdk.nashorn.internal.runtime.Context;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by cjd137 on 31/03/17.
 */
public class BookingController implements Initializable {

    @FXML
    private ListView<StopPoint> availableStopPointsList;
    @FXML
    private ListView<Ride> availableRidesList;
    @FXML
    private TextField searchStopPointsField;
    @FXML
    private ComboBox<String> travelDirectionCombo;
    @FXML
    private CheckBox filterDirectionCheck;

    private ObservableList<StopPoint> stopPoints = observableArrayList();
    private ObservableList<Ride> rides = observableArrayList();
    private ObservableList<String> travelDirectionList = observableArrayList();
    private Ride selectedRide;
    private static Stage dialog;
    private Account account;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        availableStopPointsList.setItems(stopPoints);
        availableRidesList.setItems(rides);
        travelDirectionCombo.setItems(travelDirectionList);
        travelDirectionList.add("To University");
        travelDirectionList.add("From University");
        travelDirectionCombo.getSelectionModel().selectFirst();
        for(Account account: Main.getRideShare().getAccounts()) {
            if(account.getUsername().equals(Main.getUsername())) {
                this.account = account;
            }
        }

        for(StopPoint stopPoint: Main.getRideShare().getStopPoints()) {
            stopPoints.add(stopPoint);
        }
        sortStopPointList(stopPoints);
    }

    public void backButton() throws IOException {
        FXMLLoader accountTypeLoader = new FXMLLoader(getClass().getClassLoader().getResource("accountDetails.fxml"));
        Parent accountType = accountTypeLoader.load();
        Scene accountTypeWindow = new Scene(accountType, 700, 500);
        Main.getStage().setScene(accountTypeWindow);
    }
    

    public void bookRide() {
        if(availableRidesList.getSelectionModel().getSelectedItem() != null) {
            Booking booking = new Booking();
            booking.setUsername();
            booking.setStatus("Booked");
            Main.getRideShare().addBooking(booking);
            selectedRide = availableRidesList.getSelectionModel().getSelectedItem();
            for(Ride ride : Main.getRideShare().getRides()) {
                if(ride.equals(selectedRide)) {
                    ride.useSeat();
                    booking.bookRide(ride);
                }
            }
            stopPointsRefresh();
            Alert alert = new Alert(Alert.AlertType.NONE, "Your Ride has been booked!", ButtonType.OK);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No Ride selected for booking!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void availableRides(MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getClickCount() == 2) {
            try {
                selectedRide = availableRidesList.getSelectionModel().getSelectedItem();
                account.setRideSelected(selectedRide);
                dialog = new Stage();
                dialog.initModality(Modality.WINDOW_MODAL);
                dialog.initOwner(Main.getStage());
                FXMLLoader newBooking = new FXMLLoader(getClass().getClassLoader().getResource("rideDetails.fxml"));
                Parent booking = newBooking.load();
                Scene dialogScene = new Scene(booking, 420, 500);
                dialog.setScene(dialogScene);
                dialog.show();
            } catch (LoadException e) {
            }
        }
    }

    public static void exitButton() {
        dialog.close();
    }

    public void availableStopPoints() {
        if(!filterDirectionCheck.isSelected()) {
            stopPointsRefresh();
        } else if(filterDirectionCheck.isSelected()) {
            travelDirection();
        }
    }

    private void stopPointsRefresh() {
        if(!filterDirectionCheck.isSelected()) {
            rides.clear();
            StopPoint stopPoint = availableStopPointsList.getSelectionModel().getSelectedItem();
            for (Ride ride : Main.getRideShare().getRides()) {
                for(StopPoint checkStopPoint: ride.getTrip().getRoute().getRouteStopPoints()) {
                    if(checkStopPoint.toString().equals(stopPoint.toString())) {
                        if(!rides.contains(ride) && ride.getNoOfSeats()>0) {
                            rides.add(ride);
                        }
                    }
                }
            }
            if (rides.size() > 0) {
                Collections.sort(rides, new Comparator<Ride>() {
                    @Override
                    public int compare(final Ride object1, final Ride object2) {
                        return object1.getName().compareTo(object2.getName());
                    }
                });
            }
        }
    }

    public void searchStopPoints(KeyEvent keyEvent) {

        String searchText = searchStopPointsField.getText();
        String combinedAddress;
        stopPoints.clear();
        String newKeyEvent = keyEvent.getCharacter();
        String currentText;
        if(!newKeyEvent.contains("\b") && !newKeyEvent.contains("\t")) {
            currentText = searchText + newKeyEvent;
        } else {
            currentText = searchText;
        }

        if(currentText.isEmpty()) {
            for(StopPoint stopPoint: Main.getRideShare().getStopPoints()) {
                stopPoints.add(stopPoint);
            }
            sortStopPointList(stopPoints);
        } else {
            for(StopPoint stopPoint: Main.getRideShare().getStopPoints()) {
                combinedAddress = stopPoint.toString();
                if(Integer.toString(stopPoint.getNumber()).contains(currentText)) {
                    stopPoints.add(stopPoint);
                } else if(stopPoint.getStreet().contains(currentText)) {
                    stopPoints.add(stopPoint);
                } else if(stopPoint.getSuburb().contains(currentText)) {
                    stopPoints.add(stopPoint);
                } else if(combinedAddress.contains(currentText)) {
                    stopPoints.add(stopPoint);
                }
            }
            sortStopPointList(stopPoints);
        }
    }

    public void travelDirection() {
        if (filterDirectionCheck.isSelected()) {
            rides.clear();
            StopPoint stopPoint = availableStopPointsList.getSelectionModel().getSelectedItem();
            String direction = travelDirectionCombo.getSelectionModel().getSelectedItem();
            for(Ride ride: Main.getRideShare().getRides()) {
                for(StopPoint checkStopPoint: ride.getTrip().getRoute().getRouteStopPoints()) {
                    if(checkStopPoint.toString().contains(stopPoint.toString())) {
                        if (direction.contains(ride.getTrip().getTravelDirection())) {
                            if(!rides.contains(ride)) {
                                rides.add(ride);
                            }
                        }
                    }
                }
            }
            if(rides.size() > 0) {
                Collections.sort(rides, new Comparator<Ride>() {
                    @Override
                    public int compare(final Ride object1, final Ride object2) {
                        return object1.getName().compareTo(object2.getName());
                    }
                });
            }
        }
    }

    private void sortStopPointList(ObservableList observableList) {
        if (observableList.size() > 0) {
            Collections.sort(observableList, new Comparator<StopPoint>() {
                @Override
                public int compare(final StopPoint object1, final StopPoint object2) {
                    int sorted;
                    int numberSort = Integer.toString(object1.getNumber()).compareTo(Integer.toString(object2.getNumber()));
                    int streetSort = object1.getStreet().compareTo(object2.getStreet());
                    int suburbSort = object1.getSuburb().compareTo(object2.getSuburb());
                    if (numberSort == 0) {
                        if (streetSort == 0) {
                            sorted = suburbSort;
                        } else {
                            sorted = streetSort;
                        }
                    } else {
                        sorted = numberSort;
                    }
                    return sorted;
                }
            });
        }
    }
}
