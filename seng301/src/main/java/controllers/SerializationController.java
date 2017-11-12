package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.*;


import java.io.*;
import java.util.ArrayList;

/**
 * Created by cjd137 on 23/05/17.
 */

public class SerializationController {
    private static Gson gson = new GsonBuilder().create();
    private static TypeToken<ArrayList<Account>> accounts = new TypeToken<ArrayList<Account>>(){};
    private static TypeToken<ArrayList<Car>> cars = new TypeToken<ArrayList<Car>>(){};
    private static TypeToken<ArrayList<StopPoint>> stopPoints = new TypeToken<ArrayList<StopPoint>>(){};
    private static TypeToken<ArrayList<Route>> routes = new TypeToken<ArrayList<Route>>(){};
    private static TypeToken<ArrayList<Trip>> trips = new TypeToken<ArrayList<Trip>>(){};
    private static TypeToken<ArrayList<Ride>> rides = new TypeToken<ArrayList<Ride>>(){};
    private static TypeToken<ArrayList<Photo>> photos = new TypeToken<ArrayList<Photo>>(){};
    private static TypeToken<ArrayList<Booking>> bookings = new TypeToken<ArrayList<Booking>>(){};

    public static void save() throws IOException {
        saveAccount();
        saveCars();
        saveStopPoints();
        saveRoutes();
        saveTrips();
        saveRides();
        savePhotos();
        saveBookings();
    }

    public static void load() throws IOException {
        loadAccounts();
        loadCars();
        loadStopPoints();
        loadRoutes();
        loadTrips();
        loadRides();
        loadPhotos();
        loadBookings();
    }

    private static void loadAccounts() throws IOException {
        Reader reader = new InputStreamReader(SerializationController.class.getResourceAsStream("/accounts.json"), "UTF-8");
        ArrayList<Account> account = gson.fromJson(reader, accounts.getType());
        Main.getRideShare().setAccountArrayList(account);
    }

    private static void loadCars() throws IOException {
        Reader reader = new InputStreamReader(SerializationController.class.getResourceAsStream("/cars.json"), "UTF-8");
        ArrayList<Car> car = gson.fromJson(reader, cars.getType());
        Main.getRideShare().setCarsArrayList(car);
    }
    private static void loadStopPoints() throws IOException {
        Reader reader = new InputStreamReader(SerializationController.class.getResourceAsStream("/stopPoints.json"), "UTF-8");
        ArrayList<StopPoint> stopPoint = gson.fromJson(reader, stopPoints.getType());
        Main.getRideShare().setStopPointArrayList(stopPoint);
    }
    private static void loadRoutes() throws IOException {
        Reader reader = new InputStreamReader(SerializationController.class.getResourceAsStream("/routes.json"), "UTF-8");
        ArrayList<Route> route = gson.fromJson(reader, routes.getType());
        Main.getRideShare().setRouteArrayList(route);
    }
    private static void loadTrips() throws IOException {
        Reader reader = new InputStreamReader(SerializationController.class.getResourceAsStream("/trips.json"), "UTF-8");
        ArrayList<Trip> trip = gson.fromJson(reader, trips.getType());
        Main.getRideShare().setTripArrayList(trip);
    }
    private static void loadRides() throws IOException {
        Reader reader = new InputStreamReader(SerializationController.class.getResourceAsStream("/rides.json"), "UTF-8");
        ArrayList<Ride> ride = gson.fromJson(reader, rides.getType());
        Main.getRideShare().setRideArrayList(ride);
    }

    private static void loadPhotos() throws IOException {
        Reader reader = new InputStreamReader(SerializationController.class.getResourceAsStream("/photos.json"), "UTF-8");
        ArrayList<Photo> photo = gson.fromJson(reader, photos.getType());
        Main.getRideShare().setPhotoArrayList(photo);
    }

    private static void loadBookings() throws IOException {
        Reader reader = new InputStreamReader(SerializationController.class.getResourceAsStream("/bookings.json"), "UTF-8");
        ArrayList<Booking> booking = gson.fromJson(reader, bookings.getType());
        Main.getRideShare().setBookingArrayList(booking);
    }

    private static void saveAccount() throws IOException {
        Writer writer = new OutputStreamWriter(new FileOutputStream("src/main/resources/accounts.json"), "UTF-8");
        gson.toJson(Main.getRideShare().getAccounts(), writer);
        writer.close();
    }
    private static void saveCars() throws IOException {
        Writer writer = new OutputStreamWriter(new FileOutputStream("src/main/resources/cars.json"), "UTF-8");
        gson.toJson(Main.getRideShare().getCars(), writer);
        writer.close();
    }
    private static void saveStopPoints() throws IOException {
        Writer writer = new OutputStreamWriter(new FileOutputStream("src/main/resources/stopPoints.json"), "UTF-8");
        gson.toJson(Main.getRideShare().getStopPoints(), writer);
        writer.close();
    }
    private static void saveRoutes() throws IOException {
        Writer writer = new OutputStreamWriter(new FileOutputStream("src/main/resources/routes.json"), "UTF-8");
        gson.toJson(Main.getRideShare().getRoutes(), writer);
        writer.close();
    }
    private static void saveTrips() throws IOException {
        Writer writer = new OutputStreamWriter(new FileOutputStream("src/main/resources/trips.json"), "UTF-8");
        gson.toJson(Main.getRideShare().getTrips(), writer);
        writer.close();
    }
    private static void saveRides() throws IOException {
        Writer writer = new OutputStreamWriter(new FileOutputStream("src/main/resources/rides.json"), "UTF-8");
        gson.toJson(Main.getRideShare().getRides(), writer);
        writer.close();
    }

    private static void savePhotos() throws IOException {
        Writer writer = new OutputStreamWriter(new FileOutputStream("src/main/resources/photos.json"), "UTF-8");
        gson.toJson(Main.getRideShare().getPhotos(), writer);
        writer.close();
    }

    private static void saveBookings() throws IOException {
        Writer writer = new OutputStreamWriter(new FileOutputStream("src/main/resources/bookings.json"), "UTF-8");
        gson.toJson(Main.getRideShare().getBookings(), writer);
        writer.close();
    }
}