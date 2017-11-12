package model;

import controllers.Main;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by cjd137 on 3/04/17.
 */
public class Route {

    private ArrayList<StopPoint> route = new ArrayList<>();
    private String routeName;
    private String username;

    public String toString(){
        return routeName;
    }

    public void createRoute(StopPoint stopPoint) {
        route.add(stopPoint);
    }

    public void createRouteName(String name) {
        this.routeName = name;
    }

    public void removeStopPoint(int stopPoint) {
        route.remove(stopPoint);
    }

    public ArrayList<StopPoint> getRouteStopPoints() {
        return route;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setUsername() {
        this.username = Main.getUsername();
    }

    public String getUsername() {
        return this.username;
    }
}
