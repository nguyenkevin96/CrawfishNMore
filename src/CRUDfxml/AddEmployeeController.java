package CRUDfxml;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.DbConnection;
import sample.Staff;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {
    private Connection conn = null;
    private PreparedStatement pst = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    public TextField empFirstName_Text, empLastName_Text, empUsername_Text, empPassword_Text, empRePassword_Text;

    public Button addEmployee_Button;

    public ComboBox employeeRole = new ComboBox();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = DbConnection.dbConnection();

        employeeRole.getItems().addAll(
                "Admin",
                "Manager",
                "Employee"
        );
    }

    @FXML
    private void handleAddEmployee(ActionEvent e){
        String currPass = empPassword_Text.getText();
        String newPass = empRePassword_Text.getText();
        if(currPass.equals(newPass)){
            try {
                pst = conn.prepareStatement("INSERT INTO login (username, password) VALUES (?, ?)");
                pst.setString(1, empUsername_Text.getText());
                pst.setString(2, empPassword_Text.getText());
                pst.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            try{
                pst = conn.prepareStatement("SELECT login_id FROM login WHERE username = ?");
                pst.setString(1, empUsername_Text.getText());
                rs = pst.executeQuery();
                while(rs.next()){
                    int name = rs.getInt("login_id");
                    try{
                        pst = conn.prepareStatement("INSERT INTO staff (firstName, lastName, login_id, permtype_id) VALUES (?, ?, ?, ?)");
                        pst.setString(1, empFirstName_Text.getText());
                        pst.setString(2, empLastName_Text.getText());
                        pst.setInt(3, name);
                        pst.setInt(4, employeeRole.getSelectionModel().getSelectedIndex()+1);
                        pst.executeUpdate();
                        Stage close = (Stage)addEmployee_Button.getScene().getWindow();
                        close.close();
                    } catch (SQLException ex){
                        System.out.println("Insert staff failed");
                    }
                }
            } catch (SQLException ex){
                System.out.println("Select login_id failed");
            }


            try{
                pst = conn.prepareStatment("SELECT ")
            } catch (SQLException e){
                e.getMessage();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Passwords do not match");
            alert.showAndWait();
        }
    }

}
