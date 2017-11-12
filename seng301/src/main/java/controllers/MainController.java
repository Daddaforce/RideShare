package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Account;
import utilities.PasswordAuthentication;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by samschofield on 15/03/17.
 */
public class MainController{

    @FXML
    private Button createAccountButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    public void login() throws IOException {
        boolean correctUsername = false;
        boolean correctPassword = false;
        String username = usernameField.getText();
        Main.setUsername(username);
        String password = passwordField.getText();
        for(Account account: Main.getRideShare().getAccounts()) {
            if(account.getUsername().equals(Main.getUsername())) {
                correctUsername = true;
                if (PasswordAuthentication.authenticate(password.toCharArray(), account.getPassword())) {
                    correctPassword = true;
                    FXMLLoader accountTypeLoader = new FXMLLoader(getClass().getClassLoader().getResource("accountDetails.fxml"));
                    Parent accountType = accountTypeLoader.load();
                    Scene accountTypeWindow = new Scene(accountType, 700, 500);
                    Main.getStage().setScene(accountTypeWindow);
                }
            }
        }
        if(!correctUsername) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Username does not exist!", ButtonType.OK);
            alert.showAndWait();
            usernameField.clear();
            passwordField.clear();
        } else if(!correctPassword) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect Password!", ButtonType.OK);
            alert.showAndWait();
            passwordField.clear();
        }
    }

    public void createAccount() throws IOException {
        FXMLLoader accountLoader = new FXMLLoader(getClass().getClassLoader().getResource("createAccount.fxml"));
        Parent account = accountLoader.load();
        Scene accountWindow = new Scene(account, 1073, 682);
        Main.getStage().setScene(accountWindow);
    }

    public void enterLogin(KeyEvent keyEvent) throws IOException {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            login();
        }
    }
}
