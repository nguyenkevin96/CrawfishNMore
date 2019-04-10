package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AdminMenu implements Initializable {
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    private Connection conn = null;

    @Override
    public void initialize(URL url, ResourceBundle rb){
    }

    public void inventoryManagementClicked(ActionEvent event) throws Exception{
        loadWindow(event, "InventoryManagement.fxml", "Clicked inventory button", 581, 498);
    }

    public void handleEmployeeManager(ActionEvent event) throws Exception{
        loadWindow(event, "EmployeeManager_Manager.fxml", "Clicked employee manager", 766, 396);
    }

    private void loadWindow(ActionEvent event, String location, String update, int x, int y) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(location));
        Scene scene = new Scene(parent, x, y);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        System.out.println(update);
    }

}
