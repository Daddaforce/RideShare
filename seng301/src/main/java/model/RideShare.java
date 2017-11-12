package model;

import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * Created by Chris on 31/03/2017.
 */
public class RideShare {

    private ArrayList<Car> cars = new ArrayList<>();
    private ArrayList<StopPoint> stopPoint = new ArrayList<>();
    private ArrayList<Route> route = new ArrayList<>();
    private ArrayList<Trip> trip = new ArrayList<>();
    private ArrayList<Ride> ride = new ArrayList<>();
    private ArrayList<Account> account = new ArrayList<>();
    private ArrayList<Photo> photo = new ArrayList<>();
    private ArrayList<Booking> booking = new ArrayList<>();

    public String getStringTextBox(TextField textField) {

        String stringText = "";
        if (textField.getText().isEmpty()) {
            textField.setStyle("-fx-border-color: red");
        } else {
            textField.setStyle("-fx-border-color: transparent");
            stringText = textField.getText();
        }
        return stringText;
    }

    public int getIntTextBox(TextField textField) {
        int intText = -1;
        if (textField.getText().isEmpty()) {
            textField.setStyle("-fx-border-color: red");
        } else {
            try {
                intText = Integer.parseInt(textField.getText());
                textField.setStyle("-fx-border-color: transparent");
            } catch (NumberFormatException e) {
                textField.setStyle("-fx-border-color: red");
            }
        }
        return intText;
    }


    public void addCar(Car car) {
        cars.add(car);
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void addStopPoint(StopPoint stopPoint) {
        this.stopPoint.add(stopPoint);
    }

    public ArrayList<StopPoint> getStopPoints() {
        return stopPoint;
    }

    public void addRoute(Route route) {
        this.route.add(route);
    }

    public ArrayList<Route> getRoutes() {
        return route;
    }

    public void addTrip(Trip trip) {
        this.trip.add(trip);
    }

    public ArrayList<Trip> getTrips() {
        return trip;
    }

    public void addRide(Ride ride) {
        this.ride.add(ride);
    }

    public ArrayList<Ride> getRides() {
        return ride;
    }

    public void addAccount(Account account) {
        this.account.add(account);
    }

    public ArrayList<Account> getAccounts() {
        return account;
    }

    public void addPhoto(Photo photo) {
        this.photo.add(photo);
    }

    public ArrayList<Photo> getPhotos() {
        return photo;
    }

    public void addBooking(Booking booking) {
        this.booking.add(booking);
    }

    public ArrayList<Booking> getBookings() {
        return booking;
    }

    public void setCarsArrayList(ArrayList<Car> cars) {
        this.cars = cars;
    }

    public void setStopPointArrayList(ArrayList<StopPoint> stopPoint) {
        this.stopPoint = stopPoint;
    }

    public void setRouteArrayList(ArrayList<Route> route) {
        this.route = route;
    }

    public void setTripArrayList(ArrayList<Trip> trip) {
        this.trip = trip;
    }

    public void setRideArrayList(ArrayList<Ride> ride) {
        this.ride = ride;
    }

    public void setAccountArrayList(ArrayList<Account> account) {
        this.account = account;
    }

    public void setPhotoArrayList(ArrayList<Photo> photo) {
        this.photo = photo;
    }

    public void setBookingArrayList(ArrayList<Booking> booking) {
        this.booking = booking;
    }




}
