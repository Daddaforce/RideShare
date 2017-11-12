package controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import model.Booking;

/**
 * Created by cjd137 on 29/05/17.
 */
public class NotificationController {

    private long licenceExpiry;
    private long WOFExpiry;
    private long registrationExpiry;
    private boolean carExists = false;
    private String alertMessage = "";
    private String formattedAlertMessage = "";
    private boolean showAlert = false;
    private Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.OK);



    public void checkExpiry() {

        int licenceNotified = Main.getDriverNotifiedLicence();
        int WOFNotified = Main.getDriverNotifiedWOF();
        int registrationNotified = Main.getDriverNotifiedRegistration();
        String licenceCheck = "";
        String WOFCheck = "";
        String regoCheck = "";


        if(licenceExpiry<=31 && licenceExpiry>=15) {
            licenceCheck = "will expire in less than 1 month!";
            Main.driverNotifiedLicenceUpdate();
        } else if(licenceExpiry<=14 && licenceExpiry>=8) {
            licenceCheck = "will expire in less than 2 weeks!";
            Main.driverNotifiedLicenceUpdate();
        } else if(licenceExpiry<=7 && licenceExpiry>0) {
            licenceCheck = "will expire in less than 1 week!";
            Main.driverNotifiedLicenceUpdate();
        } else if(licenceExpiry<=0) {
            licenceCheck =  "is expired! Please renew right away!";
        } else {
            licenceNotified = 1;
        }

        if(carExists) {
            if (WOFExpiry <= 31 && WOFExpiry >= 15) {
                WOFCheck = "will expire in less than 1 month!";
                Main.driverNotifiedWOFUpdate();
            } else if (WOFExpiry <= 14 && WOFExpiry >= 8) {
                WOFCheck = "will expire in less than 2 weeks!";
                Main.driverNotifiedWOFUpdate();
            } else if (WOFExpiry <= 7 && WOFExpiry>0) {
                WOFCheck = "will expire in less than 1 week!";
                Main.driverNotifiedWOFUpdate();
            } else if(WOFExpiry<=0) {
                WOFCheck =  "is expired! Please renew right away!";
            } else {
                WOFNotified = 1;
            }

            if (registrationExpiry <= 31 && registrationExpiry >= 15) {
                regoCheck = "will expire in less than 1 month!";
                Main.driverNotifiedRegistrationUpdate();
            } else if (registrationExpiry <= 14 && registrationExpiry >= 8) {
                regoCheck = "will expire in less than 2 weeks!";
                Main.driverNotifiedRegistrationUpdate();
            } else if (registrationExpiry <= 7 && registrationExpiry>0) {
                regoCheck = "will expire in less than 1 week!";
                Main.driverNotifiedRegistrationUpdate();
            } else if(registrationExpiry<=0) {
                regoCheck =  "is expired! Please renew right away!";
            } else {
                registrationNotified = 1;
            }
        } else {
            WOFNotified = 1;
            registrationNotified = 1;
        }

        if(licenceNotified == 0) {
            alertMessage = alertMessage + "Your Licence  " + licenceCheck + "\n";
            showAlert = true;
        }

        if(WOFNotified == 0) {
            alertMessage = alertMessage + "Your WOF " + WOFCheck + "\n";
            showAlert = true;
        }

        if(registrationNotified == 0) {
            alertMessage = alertMessage + "Your Registration " + regoCheck + "\n";
            showAlert = true;
        }
    }

    public void checkIfRideCancelled() {
        try {
            for (Booking booking : Main.getRideShare().getBookings()) {
                if (booking.getUsername().equals(Main.getUsername())) {
                    if (booking.getStatus().contains("Driver Cancelled")) {
                        formattedAlertMessage = formattedAlertMessage + "Driver cancelled ride for your booking: " +
                                booking.getRide().getTrip().getName() + "\n";
                        booking.setStatus("Ride Cancelled.");
                        showAlert = true;
                    }
                }
            }
        } catch (NullPointerException e) {}
    }

    public void checkIfBookingCancelled() {
        try {
            for(Booking booking: Main.getRideShare().getBookings()) {
                if(booking.getRide().getUsername().equals(Main.getUsername())) {
                    if(booking.getStatus().contains("User Cancelled")) {
                        formattedAlertMessage = formattedAlertMessage + "User cancelled booking for your ride: " +
                                booking.getRide().getTrip().getName() + ", seat has been remade available.\n";
                        booking.setStatus("Booking Cancelled");
                        showAlert = true;
                    }
                }
            }
        } catch (NullPointerException e) {}
    }

    public void setLicenceExpiry(long licenceExpiry) {
        this.licenceExpiry = licenceExpiry;
    }

    public void setWOFExpiry(long WOFExpiry) {
        this.WOFExpiry = WOFExpiry;
    }

    public void setRegistrationExpiry(long registrationExpiry) {
        this.registrationExpiry = registrationExpiry;
    }

    public void setCarExists() {
        this.carExists = true;
    }


    public void showAlert() {
        if (showAlert) {
            formattedAlertMessage = formattedAlertMessage + "\n" + alertMessage;
            alert.getDialogPane().setContent(new Text(formattedAlertMessage));
            alert.showAndWait();
        }
    }
}
