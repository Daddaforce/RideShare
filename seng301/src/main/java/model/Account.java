package model;

import controllers.Main;
import javafx.scene.image.Image;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by Chris on 24/05/2017.
 */
public class Account {

    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String address;
    private String city;
    private String landline;
    private String mobile;
    private boolean isPassenger;
    private boolean isDriver;
    private String licenceType;
    private String licenceNumber;
    private String licenceIssue;
    private String licenceExpiry;
    private String password;
    private Ride rideSelected;
    private Trip tripSelected;

    public void createAccount(String username, String email, String firstname, String lastname, String address, String city, String landline, String mobile, boolean isPassenger, boolean isDriver, String licenceType, String licenceNumber, String licenceIssue, String licenceExpiry, String password) {
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.city = city;
        this.landline = landline;
        this.mobile = mobile;
        this.isPassenger = isPassenger;
        this.isDriver = isDriver;
        this.licenceType = licenceType;
        this.licenceNumber = licenceNumber;
        this.licenceIssue = licenceIssue;
        this.licenceExpiry = licenceExpiry;
        this.password = password;
    }

    public void setRideSelected(Ride ride) {
        this.rideSelected = ride;
    }

    public Ride getRideSelected() {
        return this.rideSelected;
    }

    public void setTripSelected(Trip trip) {
        this.tripSelected = trip;
    }

    public Trip getTripSelected() {
        return this.tripSelected;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean getIsDriver() {
        return isDriver;
    }

    public long getLicenceExpiry() {
        String[] licenceSplit = licenceExpiry.split("/");
        int day = Integer.parseInt(licenceSplit[0]);
        int month = Integer.parseInt(licenceSplit[1]);
        int year = Integer.parseInt(licenceSplit[2]);
        LocalDate localDate = LocalDate.now();
        LocalDate licenceExpiryDate = LocalDate.of(year, month, day);
        long duration = Duration.between(localDate.atTime(0, 0), licenceExpiryDate.atTime(0, 0)).toDays();
        return duration;
    }
}
