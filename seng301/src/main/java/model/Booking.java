package model;

import controllers.Main;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Created by Chris on 5/04/2017.
 */
public class Booking {

    private Ride ride;
    private String username;
    private String status;
    private String reason;

    public void bookRide(Ride ride) {
        this.ride = ride;
    }

    public Ride getRide() {
        return ride;
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = ride.getTrip().getStartDate().format(formatter);
        return ride.getName() + "\nRide start date: " + formattedDate + "\nBooking status: " + status+ "\n" + ride.getNoOfSeats() + " seats left!";
    }

    public void setUsername()
    {
        this.username = Main.getUsername();
    }

    public String getUsername() {
        return this.username;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
