package CRUDfxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DbConnection;
import sample.InventoryManagement;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class addProductController implements Initializable {
    private Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    public Button save_Button;

    public TextField menuItemName_Text, price_Text;
    public TextArea menuItemDesc_TextA;

    public ComboBox menuType_Combo = new ComboBox();

    @Override
    public void initialize(URL url, ResourceBundle rb){
        conn = DbConnection.dbConnection();

        menuType_Combo.getItems().addAll(
                "Seasonal",
                "Non-Seasonal"
        );
    }

    public void handleSave(ActionEvent event){
        try{
            int i = menuType_Combo.getSelectionModel().getSelectedIndex()+1;
            pst = conn.prepareStatement("INSERT INTO menuItems (menu_id, menuItemName, menuItemPrice, menuItemDesc) VALUES (?, ?, ?, ?)");
            pst.setInt(1, i);
            pst.setString(2, menuItemName_Text.getText());
            pst.setDouble(3, Double.parseDouble(price_Text.getText()));
            pst.setString(4, menuItemDesc_TextA.getText());
            pst.executeUpdate();
            Stage close = (Stage)save_Button.getScene().getWindow();
            close.close();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
