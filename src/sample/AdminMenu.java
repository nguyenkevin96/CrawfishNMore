package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminMenu implements Initializable {
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    private Statement stmt = null;
    private Connection conn = null;

    public ListView<String> employee_List, adminManager_List, recentRec_List;

    public List<String> tempEmployee, tempAdmin, tempRecentRecieve;

    public ObservableList<String> currEmployeeList, currAdminList, currRecentRecieved;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        conn = DbConnection.dbConnection();
        tempEmployee = new ArrayList<>();
        tempAdmin = new ArrayList<>();
        tempRecentRecieve = new ArrayList<>();
        addEmployeeListData();
        addAdminListData();
        addRecentReceivedData();
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

    private void addEmployeeListData(){
        try{
            String sql = "SELECT * FROM employee";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                String name = rs.getString("first_name") + " " + rs.getString("last_name");
                tempEmployee.add(name);
            }
            currEmployeeList = FXCollections.observableArrayList(tempEmployee);
            employee_List.setItems(currEmployeeList);
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private void addAdminListData(){
        try{
            pst = conn.prepareStatement("SELECT * FROM admin");
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("firstName") + " " + rs.getString("lastName");
                tempAdmin.add(name);
            }
            currAdminList = FXCollections.observableArrayList(tempAdmin);
            adminManager_List.setItems(currAdminList);
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private void addRecentReceivedData(){
        try{
            pst = conn.prepareStatement("SELECT TOP 10 suppliers.supplierName, product.productName, product.productPrice " +
                    "FROM product " +
                    "INNER JOIN suppliers " +
                    "ON product.supplier_id = suppliers.supplier_id");
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("supplierName") + " " + rs.getString("productName") + " $" + rs.getDouble("productPrice");
                tempRecentRecieve.add(name);
            }
            currRecentRecieved = FXCollections.observableArrayList(tempRecentRecieve);
            recentRec_List.setItems(currRecentRecieved);
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
