/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodtracker.database;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author kanishksk
 */
public class Databasehandler {
    Connection conn = null;
    
    private static Databasehandler handler = null;
    
    
    private Databasehandler() {
        createConnection();
        
    }
    private void createConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://calorietrackdb.cakalg23jrdy.us-east-1.rds.amazonaws.com", 
                    "admin","OnTheMoon2023");
        } catch (Exception e) {
            System.out.println("Create connection exception" + e.getMessage());
        }
    
   }
    
    
    public static Databasehandler getInstance() {
        if (handler == null) {
            handler = new Databasehandler();
        }
        
        return handler;
    }
    
    
    public boolean insertUser(String firstName, String lastName, String email, String password) {
        PreparedStatement preparedStatement = null;
        
        try {
            ResultSet resultSet;
            
            String countEmails = "SELECT COUNT(*) FROM USERS WHERE email = ?";
            
            preparedStatement = conn.prepareStatement(countEmails);
            preparedStatement.setString(1, email);
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                if (resultSet.getInt(1) > 0) {
                    return false;
                }
            }
            
            String insertQuery = "INSERT INTO USERS(first_name, last_name, email, password) VALUES(?, ?, ?, ?)";
            
            preparedStatement = conn.prepareStatement(insertQuery);
            
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(1, lastName);
            preparedStatement.setString(1, email);
            preparedStatement.setString(1, password);
            
            int result = preparedStatement.executeUpdate();
            
            return (result == 1);
            
        } catch (Exception e) {
            
            System.out.println("Insert user error " + e.getMessage());
            
        }
        
        return false;
    }
    
}

