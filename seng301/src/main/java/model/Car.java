package model;

import controllers.Main;
import javafx.collections.ObservableList;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by cjd137 on 31/03/17.
 */
public class Car {

    private String type;
    private String model;
    private String colour;
    private String licensePlate;
    private int year;
    private int noOfSeats;
    private String username;
    private String WOF;
    private String registration;

    /**
     *
     * @param type
     * @param model
     * @param colour
     * @param licensePlate
     * @param year
     * @param noOfSeats
     */
    public void registerCar(String type, String model, String colour, String licensePlate, int year, int noOfSeats, String WOF, String registration) {
        this.colour = colour;
        this.licensePlate = licensePlate;
        this.model = model;
        this.noOfSeats = noOfSeats;
        this.type = type;
        this.year = year;
        this.WOF = WOF;
        this.registration = registration;
    }

    public String toString() {
        return licensePlate;
    }

    public String getColour() {
        return colour;
    }

    public String getLicense() {
        return licensePlate;
    }

    public String getModel() {
        return model;
    }

    public String getType() {
        return type;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public int getYear() {
        return year;
    }

    public long getWOFExpiryTime() {
        String[] WOFSplit = WOF.split("/");
        int day = Integer.parseInt(WOFSplit[0]);
        int month = Integer.parseInt(WOFSplit[1]);
        int year = Integer.parseInt(WOFSplit[2]);
        LocalDate WOFExpiryDate = LocalDate.of(year, month, day);
        long duration = Duration.between(LocalDate.now().atTime(0, 0), WOFExpiryDate.atTime(0, 0)).toDays();
        return duration;
    }

    public long getRegistrationExpiryTime() {
        String[] registrationSplit = registration.split("/");
        int day = Integer.parseInt(registrationSplit[0]);
        int month = Integer.parseInt(registrationSplit[1]);
        int year = Integer.parseInt(registrationSplit[2]);
        LocalDate registrationExpiryDate = LocalDate.of(year, month, day);
        long duration = Duration.between(LocalDate.now().atTime(0, 0), registrationExpiryDate.atTime(0, 0)).toDays();
        return duration;
    }

    public void setUsername() {
        this.username = Main.getUsername();
    }

    public String getUsername() {
        return this.username;
    }

}
