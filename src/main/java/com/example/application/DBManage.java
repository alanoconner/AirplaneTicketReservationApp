package com.example.application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManage {


    Connection dbconn;
    Statement statement;

    public DBManage() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        dbconn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/airWaysWebApp?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "airwayuser", "alnukod1993"
        );
        statement =dbconn.createStatement();
    }

    public static void main(String[] args) throws SQLException {

        String strSel = "select * from flightplan1";
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/airWaysWebApp?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "airwayuser", "alnukod1993"
        );
        Statement state = conn.createStatement();
        ResultSet resultSet = state.executeQuery(strSel);
        List<String> list = new ArrayList<>();
        while(resultSet.next()) {
//            int id = resultSet.getInt("id");
            String depC = resultSet.getString("departcity");
//            String depD = resultSet.getString("departdate");
//            int depT = resultSet.getInt("departtime");
//            double flightT = resultSet.getFloat("flighttime");
//            String arrC = resultSet.getString("arrcity");
//            int seats = resultSet.getInt("availableseats");
//            int price = resultSet.getInt("price");
//            //
//
//            System.out.println(id+", "+depC+", "+depD+", "+depT+", "+flightT+", "+arrC+", "+seats+", "+price);
            list.add(depC);
        }
        System.out.println(list);
    }

    public List<String> getColumnValues(String columnName){
        List<String> columnVal = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("select * from flightplan1" );
            while (resultSet.next()) columnVal.add(resultSet.getString(columnName));
        } catch (SQLException e) {
            columnVal.add("Error");
            return  columnVal;
        }
        columnVal.add("asdfa");
        return columnVal;
    }
}
