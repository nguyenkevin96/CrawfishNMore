package sample;

import CRUDfxml.EditEmployeeController;
import CRUDfxml.editProductController;
import com.sun.javafx.iio.ios.IosDescriptor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
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

    private ObservableList<Staff> loginData;
    private ObservableList<Inventory> recentOrders;

    public ListView<String> employee_List, adminManager_List, recentRec_List;

    private List<String> tempEmployee, tempAdmin, tempRecentRecieve;

    private ObservableList<String> currEmployeeList, currAdminList, currRecentRecieved;

    public TableView<Staff> loginTableView;
    public TableView<Inventory> recentOrderTableView;

    public TableColumn<Staff, String> firstname_Column, lastname_Column, username_Column, permission_Column;
    public TableColumn<Supplier, String> suppliername_Column, productname_Column, currentamt_Column, requiredamt_Column;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = DbConnection.dbConnection();
        loginData = FXCollections.observableArrayList();
        recentOrders = FXCollections.observableArrayList();
        tempEmployee = new ArrayList<>();
        tempAdmin = new ArrayList<>();
        tempRecentRecieve = new ArrayList<>();
        addEmployeeListData();
        addAdminListData();
        addRecentReceivedData();
        loadLoginData();
        loginSupplierTableViewCellData();
    }

    public void inventoryManagementClicked(ActionEvent event) throws Exception {
        main.changeWindow(event, "/sample/InventoryManagement.fxml", 593, 575);
    }

    public void handleEmployeeManager(ActionEvent event) throws Exception {
        main.changeWindow(event, "/sample/EmployeeManager_Manager.fxml", 766, 396);
    }

    private void addEmployeeListData() {
        tempEmployee.clear();
        try {
            String sql = "SELECT staff.staff_id, staff.firstName, staff.lastName FROM staff WHERE permtype_id = 3";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getInt("staff_id") + "\t" + rs.getString("firstName") + " " + rs.getString("lastName");
                tempEmployee.add(name);
            }
            currEmployeeList = FXCollections.observableArrayList(tempEmployee);
            employee_List.setItems(currEmployeeList);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void addAdminListData() {
        tempAdmin.clear();
        try {
            pst = conn.prepareStatement("SELECT staff.staff_id, staff.firstName, staff.lastName " +
                    "FROM staff WHERE staff.permtype_id = 1 OR staff.permtype_id = 2");
            rs = pst.executeQuery();
            while (rs.next()) {
                String name = rs.getInt("staff_id") + "\t" + rs.getString("firstName") + " " + rs.getString("lastName");
                tempAdmin.add(name);
            }
            currAdminList = FXCollections.observableArrayList(tempAdmin);
            adminManager_List.setItems(currAdminList);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void loginSupplierTableViewCellData() {
        firstname_Column.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastname_Column.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        username_Column.setCellValueFactory(new PropertyValueFactory<>("username"));
        permission_Column.setCellValueFactory(new PropertyValueFactory<>("perm_desc"));
        suppliername_Column.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        productname_Column.setCellValueFactory(new PropertyValueFactory<>("productName"));
        currentamt_Column.setCellValueFactory(new PropertyValueFactory<>("currentP"));
        requiredamt_Column.setCellValueFactory(new PropertyValueFactory<>("requiredP"));
    }

    public void loadLoginData() {
        loginData.clear();
        try {
            pst = conn.prepareStatement("SELECT firstName, lastName, login.username, permtype.perm_desc " +
                    "FROM staff " +
                    "INNER JOIN login " +
                    "ON staff.login_id = login.login_id " +
                    "INNER JOIN permtype " +
                    "ON staff.permtype_id = permtype.permType_id");
            rs = pst.executeQuery();
            while (rs.next()) {
                loginData.add(new Staff(
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("username"),
                        rs.getString("perm_desc")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        loginTableView.setItems(loginData);
    }

    public void refreshTables() {
        loadLoginData();
        addAdminListData();
        addEmployeeListData();
    }

    private void addRecentReceivedData() {
        try {
            pst = conn.prepareStatement("SELECT TOP 10 suppliers.supplierName, product.productName, inventory.currentProdAmt, inventory.requiredProdAmt " +
                    "FROM inventory " +
                    "INNER JOIN product " +
                    "ON inventory.product_id = product.product_id " +
                    "INNER JOIN suppliers " +
                    "on product.supplier_id = suppliers.supplier_id " +
                    "ORDER BY inventory_id DESC");
            rs = pst.executeQuery();
            while (rs.next()) {
                recentOrders.add(new Inventory(
                        rs.getString("supplierName"),
                        rs.getString("productName"),
                        rs.getDouble("currentProdAmt"),
                        rs.getDouble("requiredProdAmt")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        recentOrderTableView.setItems(recentOrders);
    }

    public void changeToEmployee(ActionEvent event) throws IOException{
        main.changeWindow(event, "/sameple/EmployeeManager_Manager.fxml", 766, 396);
    }

    public void handleAddEmployee(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CRUDfxml/AddEmployee.fxml"));
        Parent parent = loader.load();
        Stage window = new Stage();
        window.setTitle("Add Employee");
        window.setScene(new Scene(parent));
        window.show();
    }

    public void handleEditEmployee() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CRUDfxml/EditEmployee.fxml"));
        Parent parent = loader.load();

        EditEmployeeController controller = loader.getController();
        controller.addDataToController(loginTableView.getSelectionModel().getSelectedItem());

        Stage window = new Stage();
        window.setTitle("Edit Employee");
        window.setScene(new Scene(parent));
        window.show();
    }

    public void handleDeleteEmployee() {
        Staff emp = loginTableView.getSelectionModel().getSelectedItem();
        String name = emp.getUsername();
        try {
            pst = conn.prepareStatement("SELECT login_id FROM login WHERE username = (?)");
            pst.setString(1, name);
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("login_id");
                try {
                    pst = conn.prepareStatement("DELETE FROM staff WHERE staff_id = (?)");
                    pst.setInt(1, id);
                    pst.executeUpdate();
                } catch (SQLException ex) {
                    System.out.println("NO!");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        loadLoginData();
    }

    public void changeCustomSQL(ActionEvent event) throws IOException {
        main.changeWindow(event, "/CRUDfxml/CustomSQL.fxml", 600, 509);
    }

    public void handleLogOut(ActionEvent event) throws IOException {
        main.changeWindow(event, "/sample/login.fxml", 420, 280);
    }
}
