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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;


/**
 * Created by cjd137 on 31/03/17.
 */
public class DriverController implements Initializable {


    @FXML
    private ListView<Trip> tripShowList;

    private ObservableList<Trip> tripList = observableArrayList();
    private static Stage dialog;
    private Trip trip;
    private Account account;

    public void initialize(URL Location, ResourceBundle resources) {
        tripShowList.setItems(tripList);
        for(Account account : Main.getRideShare().getAccounts()) {
            if(account.getUsername().equals(Main.getUsername())) {
                this.account = account;
            }
        }
        for(Trip trip: Main.getRideShare().getTrips()) {
            if(trip.getUsername().equals(Main.getUsername())) {
                tripList.add(trip);
            }
        }
    }

    public void backButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader accountTypeLoader = new FXMLLoader(getClass().getClassLoader().getResource("accountDetails.fxml"));
        Parent accountType = accountTypeLoader.load();
        Scene accountTypeWindow = new Scene(accountType, 700, 500);
        Main.getStage().setScene(accountTypeWindow);
    }

    public void registerCar() throws IOException {
        FXMLLoader newCarLoader = new FXMLLoader(getClass().getClassLoader().getResource("car.fxml"));
        Parent car = newCarLoader.load();
        Scene registerNewCar = new Scene(car, 700, 500);
        Main.getStage().setScene(registerNewCar);
    }


    public void stopPoints(ActionEvent actionEvent) throws IOException {
        FXMLLoader newStopPoint = new FXMLLoader(getClass().getClassLoader().getResource("stopPoint.fxml"));
        Parent stopPoint = newStopPoint.load();
        Scene createStopPoint = new Scene(stopPoint, 700, 500);
        Main.getStage().setScene(createStopPoint);
    }

    public void routes(ActionEvent actionEvent) throws IOException {
        FXMLLoader newRoute = new FXMLLoader(getClass().getClassLoader().getResource("route.fxml"));
        Parent route = newRoute.load();
        Scene createRoute = new Scene(route, 700, 500);
        Main.getStage().setScene(createRoute);
    }

    public void toTrip(ActionEvent actionEvent) throws IOException {
        FXMLLoader newTrip = new FXMLLoader(getClass().getClassLoader().getResource("trip.fxml"));
        Parent trip = newTrip.load();
        Scene createTrip = new Scene(trip, 850, 500);
        Main.getStage().setScene(createTrip);
    }

    public void ride(ActionEvent actionEvent) throws IOException {
        FXMLLoader newRide = new FXMLLoader(getClass().getClassLoader().getResource("ride.fxml"));
        Parent ride = newRide.load();
        Scene createRide = new Scene(ride, 420, 150);
        Main.getStage().setScene(createRide);
    }

    public void showTrip(MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getClickCount() == 2) {
            try {
                trip = tripShowList.getSelectionModel().getSelectedItem();
                account.setTripSelected(trip);
                dialog = new Stage();
                dialog.initModality(Modality.WINDOW_MODAL);
                dialog.initOwner(Main.getStage());
                FXMLLoader newTrip = new FXMLLoader(getClass().getClassLoader().getResource("tripDetails.fxml"));
                Parent trip = newTrip.load();
                Scene dialogScene = new Scene(trip, 420, 500);
                dialog.setScene(dialogScene);
                dialog.show();
            } catch (LoadException e) {
                e.printStackTrace();
            }
        }
    }

    public static void exitButton() {
        dialog.close();
    }
}

