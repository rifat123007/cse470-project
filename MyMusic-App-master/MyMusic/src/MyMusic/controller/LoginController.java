package MyMusic.controller;

import MyMusic.model.DatabaseManager;
import MyMusic.model.PageChanger;
import MyMusic.model.User;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;


/*
// Controller for login.fxml
*/
public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    // Method called when the login button is pressed.
    // Gets username and password from login.fxml.
    // Validates user input. If invalid, display error message.
    // Validates username and password with the database.
    // If credentials are valid, log user in, else display error message.
    @FXML
    private void login(ActionEvent event) {
        if (validateLogin()) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            User user = null;

            // Use database manager to get a user, using the username and password input
            DatabaseManager dbManager= DatabaseManager.getInstance();
            try {
                user = dbManager.getUser(username, password);
            } catch (Exception e) {
               e.printStackTrace();
            }

            if (user != null) {
                // If a user is retrieved login
                errorLabel.setText("");
                PageChanger.getInstance().goToHomePage(usernameField.getScene(), user, "");
            }
            else {
                // If no user is returned
                errorLabel.setText("You have entered an invalid username or password");
            }
        }
        else {
            // If one or both of username or password fields is empty
            errorLabel.setText("You have entered an invalid username or password");
        }
    }

    // Check if the text entered in the username and password fields is valid
    // If the input is invalid return false
    private boolean validateLogin() {
        Boolean isValid = true;
        if (usernameField.getText().equals("")) {
            usernameField.setStyle("-fx-border-color: red");
            isValid = false;
        }
        else {
            usernameField.setStyle("-fx-border-color: none");
        }

        if (passwordField.getText().equals("")) {
            passwordField.setStyle("-fx-border-color: red");
            isValid = false;
        }
        else {
            passwordField.setStyle("-fx-border-color: none");
        }

        return isValid;
    }

    public void goToSignUpPage()  {
        PageChanger.getInstance().goToSignUpPage(usernameField.getScene());
    }
}
