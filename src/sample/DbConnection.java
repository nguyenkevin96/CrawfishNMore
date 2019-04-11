package sample;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    public static Connection dbConnection(){
        Connection conn = null;
        try{
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crawfish", "root", "Sfn8tjpansv!");
            conn = DriverManager.getConnection("jdbc:sqlserver://172.26.54.36:1433;database=crawfish", "root", "Sfn8tjpansv!");
        } catch (SQLException ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Connection Failed");
            alert.showAndWait();
        }
        return conn;
    }
}
