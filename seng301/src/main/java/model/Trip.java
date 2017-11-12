package model;


import controllers.Main;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;

/**
 * Created by cjd137 on 3/04/17.
 */
public class Trip {

    private Car tripCar;
    private LocalDate expiryDate;
    private LocalDate startDate;
    private String travelDirection;
    private Route route;
    private ArrayList<String> daysRecurrent;
    private String name;
    private String username;


    public String toString() {
        return name;
    }

    public void setDaysRecurrent(ArrayList<String> daysRecurrent) {
        this.daysRecurrent = daysRecurrent;
    }

    public void setTripCar(Car tripCar) {
        this.tripCar = tripCar;
    }

    public void setTravelDirection(String travelDirection) {
        this.travelDirection = travelDirection;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Route getRoute() {
        return this.route;
    }

    public ArrayList<String> getDaysRecurrent() {
        return daysRecurrent;
    }

    public Car getTripCar() {
        return tripCar;
    }

    public String getTravelDirection() {
        return travelDirection;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public String getName() {
         return name;
    }

    public void setUsername() {
        this.username = Main.getUsername();
    }

    public String getUsername() {
        return this.username;
    }

}
