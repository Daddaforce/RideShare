package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import model.RideShare;

import java.io.IOException;


/**
 * Created by samschofield on 7/03/17.
 */
public class Main extends Application {

    private static Stage thisStage;
    private static RideShare rideShare;
    private static String username;
    private static int driverNotifiedLicence = 0;
    private static int driverNotifiedWOF = 0;
    private static int driverNotifiedRegistration = 0;

    public void start(Stage primaryStage) throws Exception {

        rideShare = new RideShare();

        try {
           SerializationController.load();
        } catch(RuntimeException e) {
            SerializationController.save();
        }

        thisStage = primaryStage;
        FXMLLoader startLoader = new FXMLLoader(getClass().getClassLoader().getResource("main.fxml"));
        Parent start = startLoader.load();
        Scene startWindow = new Scene(start, 700, 500);

        thisStage.setTitle("UC RSS");
        thisStage.setScene(startWindow);
        thisStage.show();
    }

    @Override
    public void stop() throws IOException {
        SerializationController.save();
    }

    public static void main( String[] args )
    {
        launch(args);
    }

    public static Stage getStage() {
        return thisStage;
    }

    public static RideShare getRideShare() {
        return rideShare;
    }

    public static void setUsername(String userName) {
        username = userName;
    }

    public static String getUsername() {
        return username;
    }

    public static void driverNotifiedLicenceUpdate() {
        driverNotifiedLicence += 1;
    }

    public static int getDriverNotifiedLicence() {
        return driverNotifiedLicence;
    }

    public static void driverNotifiedWOFUpdate() {
        driverNotifiedWOF += 1;
    }

    public static int getDriverNotifiedWOF() {
        return driverNotifiedWOF;
    }

    public static void driverNotifiedRegistrationUpdate() {
        driverNotifiedRegistration += 1;
    }

    public static int getDriverNotifiedRegistration() {
        return driverNotifiedRegistration;
    }
}