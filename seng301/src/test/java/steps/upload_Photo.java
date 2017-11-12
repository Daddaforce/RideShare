package steps;

import cucumber.api.java.en.Given ;
import cucumber.api.java.en.Then ;
import cucumber.api.java.en.When ;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import model.Photo;
import org.junit.Assert ;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by cjd137 on 29/05/17.
 */

public class upload_Photo {

    byte[] emptyPhoto = {-119,80,78,71,13,10,26,10,0,0,0,13,73,72,68,82,0,0,0,-106,0,0,0,-106,8,6,0,0,0,60,1,113,-30,0,0,0,110,73,68,65,84,120,-38,-19,-63,49,1,0,0,0,-62,-96,-11,79,109,11,47,-96,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-8,25,96,53,0,1,96,12,88,-3,0,0,0,0,73,69,78,68,-82,66,96,-126};
    private Photo photo;

    @Given("^a \"([^\"]*)\" of unknown size$")
    public void a_of_unknown_size(byte[] newPhoto) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("^I upload \"([^\"]*)\"$")
    public void i_upload(byte[] newPhoto) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        photo = new Photo();
        photo.setPhoto(emptyPhoto, "testUsername");

    }

    @Then("^total size of \"([^\"]*)\" is (\\d+) width and (\\d+) height$")
    public void total_size_of_is_width_and_height(byte[] newPhoto, int width, int height) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        BufferedImage bufferedImage;
        ByteArrayInputStream bais = new ByteArrayInputStream(photo.getPhoto());
        try {
            bufferedImage = ImageIO.read(bais);
            bais.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        Assert.assertEquals(image.getHeight(), height, 0);
        Assert.assertEquals(image.getWidth(), width, 0);
    }
}
