package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EmployeeManager implements Initializable {
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    private Connection conn = null;
    private ObservableList<Employee> data;

    @FXML
    private TableView<Employee> employeeList;
    @FXML
    private TableColumn<?, ?> firstname_Column, lastname_Column,
            phone_Column, username_Column, role_Column;
    @FXML
    private TextField firstname_Text, lastname_Text, phone_Text,
            username_Text, password_Text, role_Text;


    @Override
    public void initialize(URL url, ResourceBundle rb){
        conn = DbConnection.dbConnection();
        data = FXCollections.observableArrayList();
        setCellValue();
        loadDataFromEmployee();
        selectedRowToTable();
    }

    private void setCellValue(){
        firstname_Column.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastname_Column.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phone_Column.setCellValueFactory(new PropertyValueFactory<>("phoneE"));
        username_Column.setCellValueFactory(new PropertyValueFactory<>("userName"));
        role_Column.setCellValueFactory(new PropertyValueFactory<>("roleE"));
    }

    private void loadDataFromEmployee(){
        data.clear();
        try{
            pst = conn.prepareStatement("SELECT * FROM login");
            rs = pst.executeQuery();
            while(rs.next()){
                data.add(new Employee(
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("phone"),
                        rs.getString("username"),
                        rs.getInt("role")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("Error");
        }
        employeeList.setItems(data);
    }

    private void selectedRowToTable(){
        employeeList.setOnMouseClicked(e -> {
            Employee empList = employeeList.getItems().get(employeeList.getSelectionModel().getSelectedIndex());
            firstname_Text.setText(empList.getFirstName());
            lastname_Text.setText(empList.getLastName());
            phone_Text.setText(empList.getPhoneE());
            username_Text.setText(empList.getUserName());
            role_Text.setText(("" + empList.getRoleE()));
        });
    }

    @FXML
    private void handleAddEmployee(){
        data.clear();
        try{
            pst = conn.prepareStatement("INSERT INTO login(username, password, role, firstname, lastname, phone) " +
                    "VALUES (?, ?, ?, ?, ?, ?)");
            pst.setString(1, username_Text.getText());
            pst.setString(2, password_Text.getText());
            pst.setInt(3, Integer.parseInt(role_Text.getText()));
            pst.setString(4, firstname_Text.getText());
            pst.setString(5, lastname_Text.getText());
            pst.setString(6, phone_Text.getText());
            pst.executeUpdate();
            System.out.println("Added Successfully");
        } catch (SQLException ex){
            System.out.println("Add handle employee failure");
        }
        loadDataFromEmployee();
    }

    @FXML
    private void handleEditEmployee(){
        try{
            pst = conn.prepareStatement("UPDATE login SET role = ?, firstname = ?, lastname = ?, phone = ? " +
                    "WHERE username = ?");
            pst.setInt(1, Integer.parseInt(role_Text.getText()));
            pst.setString(2, firstname_Text.getText());
            pst.setString(3, lastname_Text.getText());
            pst.setString(4, phone_Text.getText());
            pst.setString(5, username_Text.getText());
            pst.executeUpdate();
        } catch (SQLException ex){

        }
        loadDataFromEmployee();
    }

    @FXML
    private void handleDeleteEmployee() {
        try {
            pst = conn.prepareStatement("DELETE FROM login where username = ?");
            pst.setString(1, username_Text.getText());
            pst.executeUpdate();
            System.out.println(username_Text.getText() + " was successfully deleted");
        } catch (SQLException ex) {
            System.out.println("Did not delete Error");
        }
        loadDataFromEmployee();
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        Parent back = FXMLLoader.load(getClass().getResource("AdminMenu.fxml"));
        Scene backScene = new Scene(back, 915, 525);
        Stage backStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        backStage.setScene(backScene);
        backStage.show();
        System.out.println("Returned to Manager Menu Screen");
    }
}
