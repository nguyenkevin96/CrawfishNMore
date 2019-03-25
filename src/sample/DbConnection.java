package sample;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    public static Connection dbConnection(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost/crawfish", "root", "root");
        } catch (SQLException ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Connection Failed");
        }
        return conn;
    }
}
