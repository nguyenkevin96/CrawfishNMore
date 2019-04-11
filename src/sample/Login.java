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

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
public class Login implements Initializable{
    final private String incorrectUserOrPass = "Incorrect username or password";
    final private String userDoesNotExist = "Username does not exist";

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
    private Label incorrectLogin_Label, usernameDNE;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        conn = DbConnection.dbConnection();
    }

    @FXML
    private void loginClicked(ActionEvent event) throws SQLException{
        try{
            pst = conn.prepareStatement("SELECT * FROM login WHERE username = ?");
            pst.setString(1, user_Text.getText());
            rs = pst.executeQuery();
            String validUsername;
            String validPassword;
            int role;

            while(rs.next()){
                validUsername = rs.getString("username");
                validPassword = rs.getString("password");
                role = rs.getInt("login_id");
                if(validPassword.equals(password_PText.getText()) && role == 3){
                    Parent managerMenu = FXMLLoader.load(getClass().getResource("AdminMenu.fxml"));
                    Scene managerScene = new Scene(managerMenu, 802, 605);
                    Stage managerStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    managerStage.setScene(managerScene);
                    managerStage.show();
                    System.out.println("Logged in as manager");
                    break;
                } else if(validPassword.equals(password_PText.getText()) && role == 0) {
                    System.out.println("Will add later");
                    System.out.println("Logged in as employee");
                    break;
                } else if(validUsername.equals(user_Text.getText())) {
                    incorrectLogin_Label.setVisible(true);
                    incorrectLogin_Label.setText(userDoesNotExist);
                    System.out.println(userDoesNotExist);
                } else {
                    usernameDNE.setVisible(false);
                    incorrectLogin_Label.setVisible(true);
                    incorrectLogin_Label.setText(incorrectUserOrPass);
                    System.out.println(incorrectUserOrPass);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
