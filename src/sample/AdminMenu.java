package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Supplier;

public class AdminMenu implements Initializable {
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    private Statement stmt = null;
    private Connection conn = null;
    Main main = new Main();

    public ObservableList<Staff> loginData;

    public ListView<String> employee_List, adminManager_List, recentRec_List;

    public List<String> tempEmployee, tempAdmin, tempRecentRecieve;

    public ObservableList<String> currEmployeeList, currAdminList, currRecentRecieved;

    public TableView<Staff> loginTableView;
    public TableView<Inventory> inventoryTableView;

    public TableColumn<Staff, String> firstname_Column, lastname_Column, username_Column, permission_Column;
    public TableColumn<Supplier, String> suppliername_Column, productname_Column, currentamt_Column, requiredamt_Column;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        conn = DbConnection.dbConnection();
        loginData = FXCollections.observableArrayList();
        tempEmployee = new ArrayList<>();
        tempAdmin = new ArrayList<>();
        tempRecentRecieve = new ArrayList<>();
        addEmployeeListData();
//        addAdminListData();
        addRecentReceivedData();
        loadLoginData();
        loginSupplierTableViewCellData();
    }

    public void inventoryManagementClicked(ActionEvent event) throws Exception{
        main.changeWindow(event, "InventoryManagement.fxml", 581, 498);
    }

    public void handleEmployeeManager(ActionEvent event) throws Exception{
        main.changeWindow(event, "EmployeeManager_Manager.fxml", 766, 396);
    }

    private void addEmployeeListData(){
        try{
            String sql = "SELECT staff.staff_id, staff.firstName, staff.lastName FROM staff";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                String name = rs.getInt("staff_id") + "\t" + rs.getString("firstName") + " " + rs.getString("lastName");
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
            pst = conn.prepareStatement("SELECT * FROM staff");
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

    private void loginSupplierTableViewCellData(){
        firstname_Column.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastname_Column.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        username_Column.setCellValueFactory(new PropertyValueFactory<>("username"));
        permission_Column.setCellValueFactory(new PropertyValueFactory<>("perm_desc"));
/*        suppliername_Column.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        productname_Column.setCellValueFactory(new PropertyValueFactory<>("productName"));
        currentamt_Column.setCellValueFactory(new PropertyValueFactory<>("currentProd"));
        requiredamt_Column.setCellValueFactory(new PropertyValueFactory<>("requiredProd"));*/
    }

    private void loadLoginData(){
        try{
            pst = conn.prepareStatement("SELECT firstName, lastName, login.username, permtype.perm_desc " +
                    "FROM staff " +
                    "INNER JOIN login " +
                    "ON staff.login_id = login.login_id " +
                    "INNER JOIN permtype " +
                    "ON staff.permtype_id = permtype.permType_id");
            rs = pst.executeQuery();
            while(rs.next()){
                loginData.add(new Staff(
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("username"),
                        rs.getString("perm_desc")
                ));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        loginTableView.setItems(loginData);
    }

    private void addRecentReceivedData(){

    }

    public void changeToEmployee(ActionEvent event){
        /*main.changeWindow(event);*/
    }
}