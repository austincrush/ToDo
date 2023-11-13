package com.todo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddTaskController {

    @FXML
    private Button AddTaskPageConfirmButton;

    @FXML
    private TextField AddTaskPageEnterTask;

    @FXML
    void initialize() {

        AddTaskPageConfirmButton.setOnAction(actionEvent -> {

            if(!AddTaskPageEnterTask.getText().equals("")){
                createTask();

                AddTaskPageConfirmButton.getScene().getWindow().hide();

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
        });
    }

    private void createTask(){

        String newTask = AddTaskPageEnterTask.getText().trim();

        DatabaseHandler dbHandler = new DatabaseHandler();

        ResultSet rs = dbHandler.getCurrentUserID();

        try {
            while(rs.next()){
                CurrentUser.currentUserID = rs.getInt("userid");
                if(!newTask.equals("")){
                    Task task = new Task(newTask, CurrentUser.currentUserID);
                    dbHandler.createNewTask(task);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
