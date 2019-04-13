package CRUDfxml;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.AdminMenu;
import sample.DbConnection;
import sample.Staff;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditEmployeeController implements Initializable {
    private Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    public TextField employeeFirstName_Text, employeeLastName_Text, employeeUsername_Text;
    public ComboBox<String> employeeRole_Combo;

    public Button update_Button;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        conn = DbConnection.dbConnection();
        employeeRole_Combo.getItems().setAll(
                "Admin",
                "Manager",
                "Employee"
        );
    }

    public void addDataToController(Staff employee){
        employeeFirstName_Text.setText(employee.getFirstname());
        employeeLastName_Text.setText(employee.getLastname());
        employeeUsername_Text.setText(employee.getUsername());
        employeeRole_Combo.setValue(employee.getPerm_desc());
    }

    public void handleUpdateEmployee(ActionEvent event){
        try{
            pst = conn.prepareStatement("SELECT login_id FROM login WHERE username = ?");
            pst.setString(1, employeeUsername_Text.getText());
            rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt("login_id");
                try{
                    pst = conn.prepareStatement("UPDATE staff " +
                            "SET firstName = ?, lastName = ?, permtype_id = ? WHERE login_id = ?");
                    pst.setString(1, employeeFirstName_Text.getText());
                    pst.setString(2, employeeLastName_Text.getText());
                    pst.setInt(3, employeeRole_Combo.getSelectionModel().getSelectedIndex()+1);
                    pst.setInt(4, id);
                    pst.executeUpdate();
                    Stage close = (Stage)update_Button.getScene().getWindow();
                    close.close();
                } catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
