package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.RideShare;
import model.StopPoint;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by cjd137 on 3/04/17.
 */
public class StopPointController implements Initializable{

    @FXML
    private ListView<String> stopPointList;
    @FXML
    private TextField numberField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField suburbField;
    @FXML
    private Label duplicateAddress;

    private static ObservableList<String> stopPoints = observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addStopPoints();
        stopPointList.setItems(stopPoints);
    }

    private void addStopPoints() {
        this.stopPoints.clear();
        ArrayList<StopPoint> stopPoints = Main.getRideShare().getStopPoints();
        for(StopPoint existingStopPoints: stopPoints) {
            if(existingStopPoints.getUsername().equals(Main.getUsername())) {
                this.stopPoints.add(existingStopPoints.toString());
            }
        }
    }

    public void backButton() throws IOException {
        FXMLLoader driverLoader = new FXMLLoader(getClass().getClassLoader().getResource("driver.fxml"));
        Parent driver = driverLoader.load();
        Scene driverWindow = new Scene(driver, 700, 500);
        Main.getStage().setScene(driverWindow);
    }

    public void addAddress() {
        int numberAddressStopPoint;
        String streetAddressStopPoint;
        String suburbAddressStopPoint;

        StopPoint newStopPoint = new StopPoint();
        boolean duplicate = false;
        numberAddressStopPoint = Main.getRideShare().getIntTextBox(numberField);
        streetAddressStopPoint = Main.getRideShare().getStringTextBox(streetField);
        suburbAddressStopPoint = Main.getRideShare().getStringTextBox(suburbField);
        if(numberAddressStopPoint != -1 && streetAddressStopPoint != null && suburbAddressStopPoint != null) {
            for(StopPoint stopPoint: Main.getRideShare().getStopPoints()) {
                if(stopPoint.getNumber()==numberAddressStopPoint && stopPoint.getStreet().equals(streetAddressStopPoint) && stopPoint.getSuburb().equals(suburbAddressStopPoint)) {
                    duplicate = true;
                }
            }
            if(!duplicate) {
                newStopPoint.createStopPoint(numberAddressStopPoint, streetAddressStopPoint, suburbAddressStopPoint);
                newStopPoint.setUsername();
                Main.getRideShare().addStopPoint(newStopPoint);
                String stopPoint = String.format("%s", newStopPoint.toString());
                stopPoints.add(stopPoint);
                duplicateAddress.setText("");
            } else {
                numberField.setStyle("-fx-border-color: red");
                streetField.setStyle("-fx-border-color: red");
                suburbField.setStyle("-fx-border-color: red");
                duplicateAddress.setText("Duplicate Address");
            }
        } else {
            duplicateAddress.setText("Please enter an address");
        }
        numberField.clear();
        streetField.clear();
        suburbField.clear();
    }
}
