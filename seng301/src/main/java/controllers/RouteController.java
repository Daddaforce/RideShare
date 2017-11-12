package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Route;
import model.StopPoint;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by cjd137 on 3/04/17.
 */
public class RouteController implements Initializable {

    @FXML
    private ListView<StopPoint> rightRoute;
    @FXML
    private ListView<StopPoint> leftRoute;
    @FXML
    private TextField routeNameField;
    @FXML
    private Label errorRouteLabel;

    private static ObservableList<StopPoint> rightRouteList = observableArrayList();
    private static ObservableList<StopPoint> leftRouteList = observableArrayList();
    private static Route route;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        route = new Route();
        rightRoute.setItems(rightRouteList);
        leftRoute.setItems(leftRouteList);
        leftRouteList.clear();
        rightRouteList.clear();
        for(StopPoint stopPoint: Main.getRideShare().getStopPoints()) {
            if(stopPoint.getUsername().equals(Main.getUsername())) {
                leftRouteList.add(stopPoint);
            }
        }
        for(StopPoint stopPoint: route.getRouteStopPoints()) {
            rightRouteList.add(stopPoint);
        }
    }


    public void backButton() throws IOException {
        FXMLLoader driverLoader = new FXMLLoader(getClass().getClassLoader().getResource("driver.fxml"));
        Parent driver = driverLoader.load();
        Scene driverWindow = new Scene(driver, 700, 500);
        Main.getStage().setScene(driverWindow);
    }

    public void addStopPoint() {
        try {
            StopPoint selected = new StopPoint();

            for(StopPoint stopPoint: Main.getRideShare().getStopPoints()) {
                if(stopPoint.toString().equals(leftRoute.getSelectionModel().getSelectedItem().toString()) && stopPoint.getUsername().equals(Main.getUsername())) {
                    selected.createStopPoint(stopPoint.getNumber(), stopPoint.getStreet(), stopPoint.getSuburb());
                    selected.setUsername();
                }
            }
            route.createRoute(selected);
            rightRouteList.add(selected);
        } catch (Exception e){}
    }

    public void removeStopPoint() {
        try {
            int selected = rightRoute.getSelectionModel().getSelectedIndex();
            route.removeStopPoint(selected);
            rightRouteList.remove(selected);
        } catch (Exception e){}
    }

    public void doneButton(ActionEvent actionEvent) throws IOException {
        errorRouteLabel.setText("");
        String routeName =  Main.getRideShare().getStringTextBox(routeNameField);
        if(!routeName.isEmpty() && !rightRouteList.isEmpty()) {
            route.createRouteName(routeName);
            route.setUsername();
            Main.getRideShare().addRoute(route);
            backButton();
        } else {
            if(rightRouteList.isEmpty()) {
                errorRouteLabel.setText("Your route needs stop points.");
                rightRoute.setStyle("-fx-border-color: red");
            } else {
                errorRouteLabel.setText("Please enter a Route name.");
                rightRoute.setStyle("-fx-border-color: transparent");
            }
        }
    }

    public void addStopPointClick(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2) {
            try {
                addStopPoint();
            } catch (Exception e) {}
        }
    }

    public void removeStopPointClick(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2) {
            try {
                removeStopPoint();
            } catch (Exception e) {}
        }
    }
}
