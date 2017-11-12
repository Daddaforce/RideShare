package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.Car;
import model.Route;
import model.StopPoint;
import model.Trip;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by cjd137 on 3/04/17.
 */
public class TripController implements Initializable{

    @FXML
    private CheckBox isRecurrentCheck;
    @FXML
    private GridPane isRecurrentPane;
    @FXML
    private ComboBox<Route> routeCombo;
    @FXML
    private ComboBox<StopPoint> stopPointCombo;
    @FXML
    private ListView<String> routeTimesField;
    @FXML
    private ComboBox<String> timeInput;
    @FXML
    private ComboBox<Car> carSelectCombo;
    @FXML
    private ComboBox<String> travelDirectionCombo;
    @FXML
    private CheckBox mondayCheck;
    @FXML
    private CheckBox tuesdayCheck;
    @FXML
    private CheckBox wednesdayCheck;
    @FXML
    private CheckBox thursdayCheck;
    @FXML
    private CheckBox fridayCheck;
    @FXML
    private CheckBox saturdayCheck;
    @FXML
    private CheckBox sundayCheck;
    @FXML
    private DatePicker tripExpiryCalendar;
    @FXML
    private DatePicker tripStartCalendar;
    @FXML
    private TextField tripNameField;


    private ArrayList<Route> route = Main.getRideShare().getRoutes();
    private ObservableList<Route> routes = observableArrayList();
    private ObservableList<StopPoint> stopPoints = observableArrayList();
    private ObservableList<String> routeTimes = observableArrayList();
    private ObservableList<Date> times = observableArrayList();
    private ObservableList<String> timesShown = observableArrayList();
    private ObservableList<Car> carSelect = observableArrayList();
    private ObservableList<String> travelDirectionList = observableArrayList();
    private Route selectedRoute;
    private StopPoint selectedStopPoint;
    private Date stopPointTime;
    private Car carSelected;
    private String travelDirection;
    private Trip newTrip;
    private boolean isReady = false;
    private SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        newTrip = new Trip();
        timeInput.setItems(timesShown);
        routeCombo.setItems(routes);
        stopPointCombo.setItems(stopPoints);
        routeTimesField.setItems(routeTimes);
        carSelectCombo.setItems(carSelect);
        travelDirectionCombo.setItems((travelDirectionList));
        travelDirectionList.add("To University");
        travelDirectionList.add("From University");
        for(Route route: this.route) {
            if(route.getUsername().equals(Main.getUsername())) {
                routes.add(route);
            }
        }
        for(Car car: Main.getRideShare().getCars()) {
            if(car.getUsername().equals(Main.getUsername())) {
                carSelect.add(car);
            }
        }
        for(int i = 0; i < 2400; i+= 50) {
            Date date = null;
            try {
                if(i % 100 == 0) {
                    date = new SimpleDateFormat("hhmm").parse(String.format("%04d", i));
                } else {
                    date = new SimpleDateFormat("hhmm").parse(String.format("%04d", i-20));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            timesShown.add(sdf.format(date));
            times.add(date);
        }


    }

    public void backButton() throws IOException {
        FXMLLoader driverLoader = new FXMLLoader(getClass().getClassLoader().getResource("driver.fxml"));
        Parent driver = driverLoader.load();
        Scene driverWindow = new Scene(driver, 700, 500);
        Main.getStage().setScene(driverWindow);
    }

    public void createTrip() throws IOException {
        if(createCheck()) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Your Trip has been created!", ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                alert.close();
                newTrip.setUsername();
                Main.getRideShare().addTrip(newTrip);
                backButton();
            }
        }
    }

    public void routeComboSelect() {
        selectedRoute = routeCombo.getSelectionModel().getSelectedItem();
        stopPoints.clear();
        for(StopPoint stopPoint: selectedRoute.getRouteStopPoints()) {
            stopPoints.add(stopPoint);
        }
        updateListView();
    }

    public void stopPointComboSelect() {
        selectedStopPoint = stopPointCombo.getSelectionModel().getSelectedItem();
    }

    public void setTimeInput() {
        stopPointTime = times.get(timeInput.getSelectionModel().getSelectedIndex());
    }

    public void updateListView() {
        routeTimes.clear();
        for(StopPoint stopPoint: selectedRoute.getRouteStopPoints()) {
            if (stopPoint.getTime() != null) {
                routeTimes.add(String.format("StopPoint: %s\n   Time: %s", stopPoint.toString(), sdf.format(stopPoint.getTime())));
            } else {
                routeTimes.add(String.format("StopPoint: %s", stopPoint.toString()));
            }
        }
    }

    public void addTime() {
        selectedStopPoint.setTime(stopPointTime);
        updateListView();
    }

    public void travelDirection() {
        travelDirection = travelDirectionCombo.getSelectionModel().getSelectedItem();
    }

    public void carSelected() {
        carSelected = carSelectCombo.getSelectionModel().getSelectedItem();
    }

    public void isRecurrent() {
        tripExpiryCalendar.setVisible(isRecurrentCheck.isSelected());
        isRecurrentPane.setVisible(isRecurrentCheck.isSelected());
    }

    public boolean createCheck() {
        boolean checkTrip, checkCar, checkDirection, checkStopPoint = true, checkTripExpiry = true, checkTripStart = true, checkName;
        checkTrip =  !routeCombo.getSelectionModel().isEmpty();
        checkCar = !carSelectCombo.getSelectionModel().isEmpty();
        checkDirection = !travelDirectionCombo.getSelectionModel().isEmpty();
        checkName = !tripNameField.getText().isEmpty();

        if(checkTrip) {
            for (StopPoint stopPoint : selectedRoute.getRouteStopPoints()) {
                if (stopPoint.getTime() == null) {
                    checkStopPoint = false;
                }
            }
        }
        if(tripExpiryCalendar.isVisible()) {
            if(tripExpiryCalendar.getValue().equals(null)) {
                checkTripExpiry = false;
            }
        }
        if(tripStartCalendar.getValue().equals(null)) {
            checkTripStart = false;
        }

        if(checkTrip && checkCar && checkDirection && checkStopPoint && checkTripExpiry && checkTripStart && checkName) {
            if (tripExpiryCalendar.isVisible() && isRecurrentPane.isVisible()) {
                newTrip.setExpiryDate(tripExpiryCalendar.getValue());

                ArrayList<String> daysSelected = new ArrayList<>();
                daysSelected.add(String.valueOf(mondayCheck.isSelected()));
                daysSelected.add(String.valueOf(tuesdayCheck.isSelected()));
                daysSelected.add(String.valueOf(wednesdayCheck.isSelected()));
                daysSelected.add(String.valueOf(thursdayCheck.isSelected()));
                daysSelected.add(String.valueOf(fridayCheck.isSelected()));
                daysSelected.add(String.valueOf(saturdayCheck.isSelected()));
                daysSelected.add(String.valueOf(sundayCheck.isSelected()));
                newTrip.setDaysRecurrent(daysSelected);
            }
            newTrip.setStartDate(tripStartCalendar.getValue());

            newTrip.setRoute(selectedRoute);

            newTrip.setTripCar(carSelected);

            newTrip.setTravelDirection(travelDirection);

            newTrip.setName(tripNameField.getText());

            isReady = true;
        }

        return isReady;
    }

    public void selectedStopPoint() {
        int selected = routeTimesField.getSelectionModel().getSelectedIndex();
        stopPointCombo.getSelectionModel().select(selected);
    }
}
