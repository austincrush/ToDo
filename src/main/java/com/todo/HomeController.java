package com.todo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeController {

    @FXML
    private Button HomePageAddTaskButton;

    @FXML
    private Button HomePageDeleteTaskButton;

    @FXML
    private ListView<String> HomePageTaskList;

    @FXML
    void initialize() throws SQLException {

        DatabaseHandler dbHandler = new DatabaseHandler();

        ResultSet resultSet1 = dbHandler.getAllTasks();
        ResultSet resultSet2 = dbHandler.getAllTasks();

        int resultSetSize = 0;
        while(resultSet1.next()){
            if(resultSet1.isLast()){
                resultSetSize = resultSet1.getRow();
            }
        }

        String[] items = new String[resultSetSize];

        int i = 0;

        while (resultSet2.next()) {
            String temp = resultSet2.getString(Const.TASKS_TASKINFO);
            items[i] = temp;
            i++;
        }

        HomePageTaskList.getItems().addAll(items);

        HomePageDeleteTaskButton.setDisable(HomePageTaskList.getSelectionModel().getSelectedItem() == null);

        HomePageAddTaskButton.setOnAction(actionEvent -> {
            HomePageAddTaskButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("addTask.fxml"));

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

        HomePageDeleteTaskButton.setOnAction(actionEvent -> {

            Task task = new Task(HomePageTaskList.getSelectionModel().getSelectedItem(), CurrentUser.currentUserID);

            dbHandler.deleteTask(task);

            HomePageDeleteTaskButton.getScene().getWindow().hide();

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
        });
    }
}
