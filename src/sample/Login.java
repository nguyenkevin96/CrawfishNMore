package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login implements Initializable{
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    private Connection conn = null;

    @FXML
    private TextField user_Text;
    @FXML
    private PasswordField password_PText;
    @FXML
    private Button login_Button;
    @FXML
    private Label incorrectLogin_Label;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        conn = DbConnection.dbConnection();
        addData();
    }

    private void addData(){
        try{
            pst = conn.prepareStatement("INSERT INTO login(username, password, role) VALUES (?, ?, ?)");
            pst.setString(1, "kevin");
            pst.setString(2, "kevin");
            pst.setInt(3, 1);
            pst.executeUpdate();
            System.out.println("Added Successfully");
            pst.close();
        } catch (SQLException ex){
            System.out.println("Failed to add");
        }
    }

    @FXML
    private void loginClicked(ActionEvent event) throws SQLException{
        try{
            pst = conn.prepareStatement("SELECT password, role FROM login where username = ?");
            pst.setString(1, user_Text.getText());
            rs = pst.executeQuery();
            String validPassword;
            int role;
            while(rs.next()){
                validPassword = rs.getString("password");
                role = rs.getInt("role");
                if(validPassword.equals(password_PText.getText()) && role == 1){
                    Parent managerMenu = FXMLLoader.load(getClass().getResource("ManagerMenu.fxml"));
                    Scene managerScene = new Scene(managerMenu, 515, 363);
                    Stage managerStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    managerStage.setScene(managerScene);
                    managerStage.show();
                    System.out.println("Logged in as manager");
                    break;
                } else if(validPassword.equals(password_PText.getText()) && role == 0){
                    System.out.println("Will add later");
                    System.out.println("Logged in as employee");
                    break;
                } else {
                    incorrectLogin_Label.setVisible(true);
                    System.out.println("Incorrect username or password");
                }

            }
        } catch (Exception e) {

        }
    }
}
