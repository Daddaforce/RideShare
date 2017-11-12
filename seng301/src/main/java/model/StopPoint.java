package model;


import controllers.Main;

import java.util.Date;

/**
 * Created by Chris on 31/03/2017.
 */
public class StopPoint {

    private String street;
    private String suburb;
    private int number;
    private Date time;
    private String username;

    public String toString() {
        return String.format(number + " " + street + ", " + suburb);
    }

    public void createStopPoint(int number, String street, String suburb) {
        this.street = street;
        this.suburb = suburb;
        this.number = number;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getStreet() {
        return street;
    }

    public String getSuburb() {
        return suburb;
    }

    public int getNumber() {
        return  number;
    }

    public Date getTime() {
        return time;
    }

    public void setUsername() {
        this.username = Main.getUsername();
    }

    public String getUsername() {
        return this.username;
    }

}
