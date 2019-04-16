package CRUDfxml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.DbConnection;
import sample.Inventory;
import sample.MenuItem;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class editProductController implements Initializable {
    private Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<String> supplierOList;
    private List<String> tempSupplierList = new ArrayList<>();

    public TextField menuItemName_Text, price_Text;
    public TextArea description_TextA;

    public Label editTitle_Label;

    public ComboBox<String> menuType_Combo = new ComboBox<>();

    public Button save_Button;

    @Override
    public void initialize(URL url, ResourceBundle rs){
        conn = DbConnection.dbConnection();

        menuType_Combo.getItems().addAll(
                "Seasonal",
                "Non-Seasonal"
        );
    }

    public void addDataToController(MenuItem menuItem){
        if(menuItem.getMenuType() == 1)
            menuType_Combo.setValue("Seasonal");
        else if (menuItem.getMenuType() == 2)
            menuType_Combo.setValue("Non-Seasonal");

        menuItemName_Text.setText(menuItem.getMenuItemN());
        price_Text.setText(String.valueOf(menuItem.getMenuItemP()));
        description_TextA.setText(menuItem.getMenuItemD());
    }


    public void handleSave(ActionEvent event){
        try{
            pst = conn.prepareStatement("UPDATE menuItems SET menu_id = ?, menuItemName = ?, menuItemPrice = ?, menuItemDesc = ? WHERE menuItemName = (?)");
            pst.setInt(1, menuType_Combo.getSelectionModel().getSelectedIndex()+1);
            pst.setString(2, menuItemName_Text.getText());
            pst.setDouble(3, Double.parseDouble(price_Text.getText()));
            pst.setString(4, description_TextA.getText());
            pst.setString(5, menuItemName_Text.getText());
            pst.executeUpdate();
            Stage close = (Stage)save_Button.getScene().getWindow();
            close.close();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
