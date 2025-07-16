package controller;

import java.sql.*;
import java.util.ArrayList;
import model.DBConnection;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author carst
 */
public class CounselorController {
    
    //I JUST ADDED THIS METHOD TO GET ALL THE COUNSELOR NAMES IN MY COMBO BOX, WE CAN CHANGE THIS LATER
    
    public ArrayList<String> getCounselorNames(){
        
        Connection conn = DBConnection.getConnection();
        
        ArrayList<String> counselorsNames = new ArrayList<>();
        
        counselorsNames.clear();
        
        try {
            
            if (conn == null) {
                System.err.println("❌ Database connection failed - cannot retrieve appointments");
                return null;
            }
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Counselors");
            
            while (rs.next()) {                
                String name = rs.getString("name");

                counselorsNames.add(name);
            }

            return counselorsNames;

        } catch (SQLException e) {
            System.err.println("❌ SQL Error in getAll(): " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("❌ Unexpected error in getAll(): " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        
    }
}
