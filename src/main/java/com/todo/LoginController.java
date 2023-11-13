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

public class LoginController {

    @FXML
    private Button LoginPageLoginButton;

    @FXML
    private Button LoginPageSignUpButton;

    @FXML
    private TextField LoginPageUsername;

    @FXML
    private PasswordField LoginPagePassword;

    @FXML
    private Circle LoginPageVerificationCircle;

    @FXML
    void initialize() {

        DatabaseHandler dbHandler = new DatabaseHandler();

        LoginPageLoginButton.setOnAction(actionEvent -> {

            String loginName = LoginPageUsername.getText().trim();
            String loginPass = LoginPagePassword.getText().trim();

            User user = new User();
            user.setUsername(loginName);
            user.setPassword(loginPass);

            ResultSet userRow = dbHandler.getUserAndPass(user);

            int counter = 0;

            try{
                while(userRow.next()){
                    counter++;
                }
                if(counter == 1){
                    LoginPageVerificationCircle.setFill(Color.GREEN);
                    CurrentUser.currentUserName = loginName;
                    showTaskListScreen();

                } else{
                    LoginPageVerificationCircle.setFill(Color.RED);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        LoginPageSignUpButton.setOnAction(actionEvent -> {
            LoginPageSignUpButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("signUp.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root = loader.getRoot();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        });
    }

    private void showTaskListScreen(){
        LoginPageSignUpButton.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("home.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Parent root = loader.getRoot();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}
