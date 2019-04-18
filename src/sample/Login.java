package sample;

import com.ibatis.common.jdbc.ScriptRunner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

    Main main = new Main();

    @Override
    public void initialize(URL url, ResourceBundle rb){
        conn = DbConnection.dbConnection();
    }

    @FXML
    private void loginClicked(ActionEvent event) throws SQLException{
        try{
            pst = conn.prepareStatement("SELECT login.username, login.password, permtype.permType_id " +
                    "FROM staff " +
                    "INNER JOIN permtype " +
                    "ON staff.permtype_id = permtype.permType_id " +
                    "INNER JOIN login " +
                    "ON staff.login_id = login.login_id " +
                    "WHERE login.username = ?");
            pst.setString(1, user_Text.getText());
            rs = pst.executeQuery();
            String validUsername;
            String validPassword;
            int role;

            while(rs.next()){
                validUsername = rs.getString("username");
                validPassword = rs.getString("password");
                role = rs.getInt("permType_id");
                if(validPassword.equals(password_PText.getText()) && role == 1 || role == 2){
                    main.changeWindow(event, "AdminMenu.fxml", 1046, 614);
                    System.out.println("Logged in as manager");
                    break;
                } else if(validPassword.equals(password_PText.getText()) && role == 3) {
                    System.out.println("Will add later");
                    System.out.println("Logged in as employee");
                    break;
                } else if(!validUsername.equals(user_Text.getText())) {
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Login doesn't exist");
            alert.showAndWait();
        }

    }

    public void addSQL(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        Connection conn = DbConnection.dbConnection();
        ScriptRunner scriptRunner = new ScriptRunner(conn, false, false);
        InputStreamReader reader = new InputStreamReader(new FileInputStream("DatabaseFiller.sql"));
        scriptRunner.runScript(reader);
        reader.close();
        conn.close();
    }
}
