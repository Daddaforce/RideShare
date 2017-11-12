package controllers;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import model.Account;
import model.Photo;
import utilities.PasswordAuthentication;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.Buffer;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by Chris on 24/05/2017.
 */
public class CreateAccountController implements Initializable{

    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField landLineField;
    @FXML
    private TextField mobileField;
    @FXML
    private CheckBox isPassengerCheck;
    @FXML
    private CheckBox isDriverCheck;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private ImageView photoView;
    @FXML
    private GridPane isDriverPane;
    @FXML
    private ComboBox licenceCombo;
    @FXML
    private TextField licenceNumberField;
    @FXML
    private TextField issueDateField;
    @FXML
    private TextField expiryDateField;


    private byte[] newPhoto = {};
    private ObservableList<String> licenceTypesShown = observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        licenceCombo.setItems(licenceTypesShown);
        licenceTypesShown.add("Learners");
        licenceTypesShown.add("Restricted");
        licenceTypesShown.add("Full < 2 Years");
        licenceTypesShown.add("Full > 2 years");
    }

    public void accountCreate(ActionEvent actionEvent) throws IOException {
        Account account = new Account();
        Photo photo = new Photo();
        String username = Main.getRideShare().getStringTextBox(usernameField);
        String password = Main.getRideShare().getStringTextBox(passwordField);
        String confirmPassword = Main.getRideShare().getStringTextBox(confirmPasswordField);
        String email = Main.getRideShare().getStringTextBox(emailField);
        String firstName = Main.getRideShare().getStringTextBox(firstNameField);
        String lastName = Main.getRideShare().getStringTextBox(lastNameField);
        String address = Main.getRideShare().getStringTextBox(addressField);
        String city = Main.getRideShare().getStringTextBox(cityField);
        String landLine = Main.getRideShare().getStringTextBox(landLineField);
        String mobile = Main.getRideShare().getStringTextBox(mobileField);
        boolean isPassenger = isPassengerCheck.isSelected();
        boolean isDriver = isDriverCheck.isSelected();
        String licenceType = licenceCombo.getSelectionModel().toString();
        String licenceNumber = Main.getRideShare().getStringTextBox(licenceNumberField);
        String issueDate = Main.getRideShare().getStringTextBox(issueDateField);
        String expiryDate = Main.getRideShare().getStringTextBox(expiryDateField);
        String emailExtension;
        boolean uclive = false;
        boolean canterbury = false;
        if(!emailField.getText().isEmpty()) {
            try {
                emailExtension = email.substring(email.indexOf("@"), email.length());
                uclive = emailExtension.equals("@uclive.ac.nz");
                canterbury = emailExtension.equals("@canterbury.ac.nz");
            } catch(StringIndexOutOfBoundsException e) {
                emailField.setStyle("-fx-border-color: red");
            }
        }
        boolean newAlert = false;
        boolean correctFields = false;
        Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
        String alertMessage = "";
        if(!password.equals(confirmPassword)) {
            alertMessage = alertMessage + "Passwords must match!\n";
            newAlert = true;
            passwordField.clear();
            confirmPasswordField.clear();
            passwordField.setStyle("-fx-border-color: red");
            confirmPasswordField.setStyle("-fx-border-color: red");
        }
        if(!uclive && !canterbury) {
            alertMessage = alertMessage + "Email must be a University of Canterbury email!\n";
            newAlert = true;
        }
        if(newPhoto.length == 0) {
            alertMessage = alertMessage + "Must upload a photo!";
            photoView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(200,0,0,1), 10, 0, 0, 0)");
            newAlert = true;
        }
        if(!newAlert && !passwordField.getText().isEmpty()) {
            correctFields = true;
        } else {
            alert.setContentText(alertMessage);
            alert.showAndWait();
        }
        if (!username.isEmpty() && !password.isEmpty() && correctFields && !email.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty() && !address.isEmpty() && !city.isEmpty() && !landLine.isEmpty() && !mobile.isEmpty() && photo.getPhoto().length!=0) {
            if(isDriver) {
                if(!licenceType.isEmpty() && !licenceNumber.isEmpty() && !issueDate.isEmpty() && !expiryDate.isEmpty()) {
                    licenceType = licenceCombo.getSelectionModel().getSelectedItem().toString();
                    correctFields = true;
                }
            } else if(isPassenger && !isDriver) {
                correctFields = true;
            }
        }

        if(correctFields) {
            if (password.length() > 4) {
                String hashedPassword = PasswordAuthentication.hash(password.toCharArray());
                account.createAccount(username, email, firstName, lastName, address, city, landLine, mobile, isPassenger, isDriver, licenceType, licenceNumber, issueDate, expiryDate, hashedPassword);
                Main.getRideShare().addAccount(account);
                photo.setPhoto(newPhoto, username);
                Main.getRideShare().addPhoto(photo);
                backButton();
                SerializationController.save();
            }
        }
    }

    public void backButton() throws IOException {
        FXMLLoader startLoader = new FXMLLoader(getClass().getClassLoader().getResource("main.fxml"));
        Parent start = startLoader.load();
        Scene startWindow = new Scene(start, 700, 500);
        Main.getStage().setScene(startWindow);
    }

    public void uploadPicture(ActionEvent actionEvent) throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Photo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(Main.getStage());

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString(), 150, 150, true, false);
            photoView.setFitHeight(150);
            photoView.setFitWidth(150);
            photoView.setImage(image);
            BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
            ByteArrayOutputStream s = new ByteArrayOutputStream();
            String fileName = selectedFile.getName();
            String fileExtension = fileName.substring(fileName.indexOf(".") + 1, selectedFile.getName().length());
            ImageIO.write(bImage, fileExtension, s);
            newPhoto = s.toByteArray();
            s.close();
        }
    }

    public void showDriverDetails(ActionEvent actionEvent) {
        if(isDriverCheck.isSelected()) {
            isDriverPane.setVisible(true);
        } else {
            isDriverPane.setVisible(false);
        }
    }

}
