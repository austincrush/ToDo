package com.todo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpController {

    @FXML
    private TextField SignUpPageFirstName;

    @FXML
    private TextField SignUpPageLastName;

    @FXML
    private PasswordField SignUpPagePassword;

    @FXML
    private Button SignUpPageSignUpButton;

    @FXML
    private TextField SignUpPageUsername;

    @FXML
    private Circle SignUpPageVerificationCircle;

    @FXML
    void initialize() {

        SignUpPageSignUpButton.setOnAction(actionEvent -> {
            createUser();

            SignUpPageSignUpButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("login.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root = loader.getRoot();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
    }

    private void createUser(){
        DatabaseHandler dbHandler = new DatabaseHandler();

        String firstName = SignUpPageFirstName.getText().trim();
        String lastName = SignUpPageLastName.getText().trim();
        String userName = SignUpPageUsername.getText().trim();
        String password = SignUpPagePassword.getText().trim();

        User user = new User();
        user.setUsername(userName);

        ResultSet userRow = dbHandler.getUserName(user);

        int counter = 0;

        if(!firstName.equals("") && !lastName.equals("") && !userName.equals("") && !password.equals("")){
            try{
                while(userRow.next()){
                    counter++;
                }
                if(counter == 1){
                    SignUpPageVerificationCircle.setFill(Color.RED);
                } else{
                    SignUpPageVerificationCircle.setFill(Color.GREEN);
                    User user1 = new User(firstName, lastName, userName, password);
                    dbHandler.signUpUser(user1);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
