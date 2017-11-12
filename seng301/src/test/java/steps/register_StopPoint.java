package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import model.StopPoint;
import org.junit.Assert;

/**
 * Created by cjd137 on 29/05/17.
 */
public class register_StopPoint {

    private int number;
    private String street;
    private String suburb;
    private StopPoint stopPoint;

    @Given("^my \"([^\"]*)\" is (\\d+) \"([^\"]*)\" in \"([^\"]*)\"$")
    public void my_is_in(String stopPoint, int number, String street, String suburb) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        this.number = number;
        this.street = street;
        this.suburb = suburb;
    }

    @When("^I add my stopPoint$")
    public void i_add_my() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        stopPoint = new StopPoint();
        stopPoint.createStopPoint(number, street, suburb);
    }

    @Then("^A new stopPoint should be added$")
    public void a_new_should_be_added() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(stopPoint.getNumber(), number);
        Assert.assertEquals(stopPoint.getStreet(), street);
        Assert.assertEquals(stopPoint.getSuburb(), suburb);
    }
}
