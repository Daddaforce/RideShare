package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import model.Car;

import java.io.IOException;

/**
 * Created by cjd137 on 3/04/17.
 */
public class CarController {

    @FXML
    private TextField carModelField;
    @FXML
    private TextField carYearField;
    @FXML
    private TextField carTypeField;
    @FXML
    private TextField carLicenseField;
    @FXML
    private TextField carColourField;
    @FXML
    private TextField carSeatsField;
    @FXML
    private TextField carWOFField;
    @FXML
    private TextField carRegistrationField;

    private String typeField;
    private String modelField;
    private String colourField;
    private String licenseField;
    private int yearField;
    private int seatsField;
    private String wofField;
    private String registrationField;

    public void carBack() throws IOException {
        FXMLLoader driverLoader = new FXMLLoader(getClass().getClassLoader().getResource("driver.fxml"));
        Parent driver = driverLoader.load();
        Scene driverWindow = new Scene(driver, 700, 500);
        Main.getStage().setScene(driverWindow);
    }

    public void carCreate() throws IOException {

        Car car = new Car();

        typeField = Main.getRideShare().getStringTextBox(carTypeField);
        modelField = Main.getRideShare().getStringTextBox(carModelField);
        colourField = Main.getRideShare().getStringTextBox(carColourField);
        licenseField = Main.getRideShare().getStringTextBox(carLicenseField);
        yearField = Main.getRideShare().getIntTextBox(carYearField);
        seatsField = Main.getRideShare().getIntTextBox(carSeatsField);
        wofField  = Main.getRideShare().getStringTextBox(carWOFField);
        registrationField = Main.getRideShare().getStringTextBox(carRegistrationField);


        if(typeField != null && modelField != null && colourField != null && licenseField != null && yearField != -1 && seatsField != -1) {
            car.registerCar(typeField, modelField, colourField, licenseField, yearField, seatsField, wofField, registrationField);
            car.setUsername();
            Main.getRideShare().addCar(car);
            carBack();
        }
    }
}
