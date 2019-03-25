package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ManagerMenu implements Initializable {
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    private Connection conn = null;

    @Override
    public void initialize(URL url, ResourceBundle rb){
    }

    @FXML
    private void inventoryManagementClicked(ActionEvent event) throws Exception{
        Parent inventoryManager = FXMLLoader.load(getClass().getResource("InventoryManagement.fxml"));
        Scene inventoryScene = new Scene(inventoryManager, 915, 525);
        Stage inventoryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        inventoryStage.setScene(inventoryScene);
        inventoryStage.show();
        System.out.println("Logged in as manager");
    }

    @FXML
    private void handleEmployeeManager(ActionEvent event) throws Exception{
        Parent employeeManager = FXMLLoader.load(getClass().getResource("EmployeeManager_Manager.fxml"));
        Scene employeeScene = new Scene(employeeManager, 766, 396);
        Stage employeeStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        employeeStage.setScene(employeeScene);
        employeeStage.show();
        System.out.println("Logged in as manager");
    }

}
