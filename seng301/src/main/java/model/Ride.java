package model;

import controllers.Main;

import java.time.format.DateTimeFormatter;

/**
 * Created by Chris on 5/04/2017.
 */
public class Ride {

    private Trip trip;
    private int noOfSeats = 0;
    private String name;
    private String username;
    private String status;
    private String reason;


    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = trip.getStartDate().format(formatter);
        return name + "\nRide start date: " + formattedDate + "\nRide status: " + status+ "\n" + noOfSeats + " seats left!";
    }

    public void addTrip(Trip trip) {
        this.trip = trip;
    }

    public void addNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public Trip getTrip() {
        return trip;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername() {
        this.username = Main.getUsername();
    }

    public String getUsername() {
        return this.username;
    }

    public void useSeat() {
        noOfSeats -= 1;
    }

    public void releaseSeat() {
        noOfSeats += 1;
    }

    public void cancelRide() {
        noOfSeats = 0;
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
