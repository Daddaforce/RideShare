package controllers;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Account;
import model.Photo;
import model.Ride;
import model.Trip;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by Chris on 5/04/2017.
 */
public class TripDetailsController implements Initializable {

    @FXML
    private ListView<String> tripDetailsList;
    @FXML
    private ImageView profileImage;

    private ObservableList<String> tripDetails = observableArrayList();
    private Account account;
    private Photo photo;
    private Image image;
    private BufferedImage bufferedImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Trip trip;
        tripDetailsList.setItems(tripDetails);
        for(Account account : Main.getRideShare().getAccounts()) {
            if(account.getUsername().equals(Main.getUsername())) {
                this.account = account;
            }
        }
        for(Photo photo : Main.getRideShare().getPhotos()) {
            if(photo.getUsername().equals(account.getTripSelected().getUsername())) {
                this.photo = photo;
            }
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(photo.getPhoto());
        try {
            bufferedImage = ImageIO.read(bais);
            bais.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        image = SwingFXUtils.toFXImage(bufferedImage, null);
        profileImage.setImage(image);
        trip = account.getTripSelected();
        tripDetails.add("Trip Name: " +trip.getName());
        tripDetails.add("Trip Direction: " +trip.getTravelDirection());
        if(trip.getExpiryDate() != null) {
            tripDetails.add("Trip Expiry: " + trip.getExpiryDate());
        }
        tripDetails.add("Car:");
        tripDetails.add("Model: " + trip.getTripCar().getModel());
        tripDetails.add("Colour: " + trip.getTripCar().getColour());
        tripDetails.add("License Plate: " + trip.getTripCar().getLicense());
        tripDetails.add("Type: " + trip.getTripCar().getType());
        tripDetails.add("Year: " + Integer.toString(trip.getTripCar().getYear()));
        tripDetails.add("Number of Stops: " + Integer.toString(trip.getRoute().getRouteStopPoints().size()));
        //account.setTripSelected(new Trip());
    }

    public void exitButton() {
        try {
            BookingController.exitButton();
        } catch(Exception e){}
        try {
            DriverController.exitButton();
        } catch(Exception e){}
    }
}
