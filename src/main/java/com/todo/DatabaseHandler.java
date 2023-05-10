package com.todo;

import java.sql.*;

public class DatabaseHandler extends Configs{

    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {

        String connectionString = "jdbc:mysql://"
                + dbHost + ":"
                + dbPort + "/"
                + dbName;

        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public void signUpUser(User user){

        String insert = "INSERT INTO " +
                Const.USERS_TABLE + "(" +
                Const.USERS_FIRSTNAME + "," +
                Const.USERS_LASTNAME + "," +
                Const.USERS_USERNAME + "," +
                Const.USERS_PASSWORD + ")" +
                "VALUES(?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);

            preparedStatement.setString(1,user.getFirstname());
            preparedStatement.setString(2,user.getLastname());
            preparedStatement.setString(3,user.getUsername());
            preparedStatement.setString(4,user.getPassword());

            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getUserAndPass(User user){

        ResultSet resultSet = null;

        if(!user.getUsername().equals("") || !user.getPassword().equals("")){
            String query = "SELECT * FROM " + Const.USERS_TABLE + " WHERE " +
                    Const.USERS_USERNAME + "=?" + " AND " + Const.USERS_PASSWORD + "=?";
            try{
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());

                resultSet = preparedStatement.executeQuery();

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return resultSet;
    }

    public ResultSet getUserName(User user){

        ResultSet resultSet = null;

        if(!user.getUsername().equals("")){
            String query = "SELECT * FROM " + Const.USERS_TABLE + " WHERE " +
                    Const.USERS_USERNAME + "=?";
            try{
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
                preparedStatement.setString(1, user.getUsername());

                resultSet = preparedStatement.executeQuery();

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return resultSet;
    }

    public ResultSet getAllTasks(){
        ResultSet resultSet;

        String query = "SELECT " + Const.TASKS_TASKINFO + " FROM " + Const.TASKS_TABLE + " WHERE " + Const.TASKS_USERID + "=?";
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, CurrentUser.currentUserID);

            resultSet = preparedStatement.executeQuery();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public void createNewTask(Task task){
        String insert = "INSERT INTO " +
                Const.TASKS_TABLE + "(" +
                Const.TASKS_TASKINFO + "," +
                Const.TASKS_USERID +
                ")" + "VALUES(?,?)";

        if(!task.getTaskInfo().equals("")){
            try {
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);

                preparedStatement.setString(1, task.getTaskInfo());
                preparedStatement.setInt(2, CurrentUser.currentUserID);

                preparedStatement.executeUpdate();
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteTask(Task task){
        String delete = "DELETE FROM " +
                Const.TASKS_TABLE +
                " WHERE " + Const.TASKS_TASKINFO +
                " =?";

        if(!task.getTaskInfo().equals("")){
            try {
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(delete);

                preparedStatement.setString(1, task.getTaskInfo());

                preparedStatement.executeUpdate();
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ResultSet getCurrentUserID(){
        ResultSet resultSet;

        String query = "SELECT " + Const.USERS_USERID + " FROM " + Const.USERS_TABLE + " WHERE " +
                Const.USERS_USERNAME + "=?";
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString(1, CurrentUser.currentUserName);

            resultSet = preparedStatement.executeQuery();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }
}
