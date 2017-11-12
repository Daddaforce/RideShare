package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import model.Car;
import org.junit.Assert;

/**
 * Created by cjd137 on 29/05/17.
 */
public class register_Car {

    private String type;
    private String model;
    private String colour;
    private String licencePlate;
    private int year;
    private int noOfSeats;
    private String WOF;
    private String registration;
    private Car car;

    @Given("^\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", year is (\\d+) and number of physical seats is (\\d+)$")
    public void yearIsAndNumberOfPhysicalSeatsIs(String type, String model, String colour, String licencePlate, int year, int noOfSeats) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        this.type = type;
        this.model = model;
        this.colour = colour;
        this.licencePlate = licencePlate;
        this.year = year;
        this.noOfSeats = noOfSeats;
    }

    @When("^I enter \"([^\"]*)\" details$")
    public void i_enter_details(String car) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        this.car = new Car();
        this.car.registerCar(type, model, colour, licencePlate, year, noOfSeats, WOF, registration);
    }

    @Then("^A \"([^\"]*)\" object is created with the defining data$")
    public void a_object_is_created_with_the_defining_data(String object) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(car.getType(), type);
        Assert.assertEquals(car.getModel(), model);
        Assert.assertEquals(car.getColour(), colour);
        Assert.assertEquals(car.getLicense(), licencePlate);
        Assert.assertEquals(car.getYear(), year);
        Assert.assertEquals(car.getNoOfSeats(), noOfSeats);
    }
}
