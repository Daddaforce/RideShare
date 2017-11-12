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
public class RideDetailsController implements Initializable {

    @FXML
    private ListView<String> rideDetailsList;
    @FXML
    private ImageView profileImage;

    private ObservableList<String> rideDetails = observableArrayList();
    private Account account;
    private Photo photo;
    private Image image;
    private BufferedImage bufferedImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Ride ride;
        rideDetailsList.setItems(rideDetails);
        for(Account account : Main.getRideShare().getAccounts()) {
            if(account.getUsername().equals(Main.getUsername())) {
                this.account = account;
            }
        }
        for(Photo photo : Main.getRideShare().getPhotos()) {
            if(photo.getUsername().equals(account.getRideSelected().getUsername())) {
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
        ride = account.getRideSelected();
        rideDetails.add("Trip Name: " +ride.getName());
        rideDetails.add("Trip Direction: " +ride.getTrip().getTravelDirection());
        if(ride.getTrip().getExpiryDate() != null) {
            rideDetails.add("Trip Expiry: " + ride.getTrip().getExpiryDate());
        }
        rideDetails.add("Car:");
        rideDetails.add("Model: " + ride.getTrip().getTripCar().getModel());
        rideDetails.add("Colour: " + ride.getTrip().getTripCar().getColour());
        rideDetails.add("License Plate: " + ride.getTrip().getTripCar().getLicense());
        rideDetails.add("Type: " + ride.getTrip().getTripCar().getType());
        rideDetails.add("Year: " + Integer.toString(ride.getTrip().getTripCar().getYear()));
        rideDetails.add("Number of seats left: " + Integer.toString(ride.getNoOfSeats()));
        rideDetails.add("Number of Stops: " + Integer.toString(ride.getTrip().getRoute().getRouteStopPoints().size()));
        //account.setRideSelected(new Ride());
    }

    public void exitButton() {
        try {
            BookingController.exitButton();
        } catch(Exception e){}
        try {
            DriverController.exitButton();
        } catch(Exception e){}
        try {
            AccountController.exitButton();
        } catch(Exception e) {}
    }
}
