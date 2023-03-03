package UserPackage;

import java.sql.*;

/**
 * User Class:
 * @author Aysema Kasap
 * @version 02.05.2021
 */

public class User {
    // properties
    public String name;
    public String password;
    private String gender;
    public double height;
    private double weight;
    public String email;
    public double calorieNeed;
    private int age;
    public boolean cheatDay;
    
    // constructor for login user
    public User( String n, String p )
    {
        
        name = n;
        password = p;
        
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://34.69.74.87:3306/userdemo";
        String username = "root";
        String password = "20210406cp";
        
        try
        {
            Class.forName(driver);
            java.sql.Connection conn = DriverManager.getConnection( url, username, password );
            
            Statement statement2 = ((java.sql.Connection) conn).createStatement(); 
            String sql2 = "SELECT height FROM user WHERE username = '" + n + "' and password = '" + p + "'";
            ResultSet resultset1 = statement2.executeQuery(sql2);
            
            while(  resultset1.next() )
            {
                height = Double.parseDouble(resultset1.getString("height"));
            }
            
            Statement statement3 = ((java.sql.Connection) conn).createStatement(); 
            String sql3 = "SELECT weight FROM user WHERE username = '" + n + "' and password = '" + p + "'";
            ResultSet resultset2 = statement3.executeQuery(sql3);
            
            while(  resultset2.next() )
            {
                weight = Double.parseDouble(resultset2.getString("height"));
            }
            
            Statement statement4 = ((java.sql.Connection) conn).createStatement(); 
            String sql4 = "SELECT age FROM user WHERE username = '" + n + "' and password = '" + p + "'";
            ResultSet resultset3 = statement4.executeQuery(sql4);
            
            while(  resultset3.next() )
            {
                age = Integer.parseInt(resultset2.getString("height"));
            }
         }
        
        catch(SQLException e ) { e.printStackTrace();} catch (ClassNotFoundException e) {e.printStackTrace();}
    }

    // MAKE
//    //constructor for register
//    public User( String n, String ) {
//        password = ;
//        this.id = id;
//    }

    public double calculateBMI() {
        return height / ( weight * weight);
    }

    // public ArrayList<String> getDiseases() {
    //     return diseases;
    // }

    public double calculateCalorieNeed() {

        if( gender.equalsIgnoreCase( "female" ) ) {
            return 387 - ( 7.31 * age ) + ( 14.2 * weight + ( 503 * ( height / 100 ) ) );
        }
        else {
            return 864 - ( 9.72 * age ) + ( 14.2 * weight + ( 503 * ( height / 100 ) ) );
        }
    }
    
    public String getUsername()
    {
        return this.name;
    }
    
    public String getPassword()
    {
        return this.password;
    }
    
}
