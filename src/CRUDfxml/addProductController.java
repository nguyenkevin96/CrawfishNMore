package CRUDfxml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DbConnection;
import sample.InventoryManagement;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class addProductController implements Initializable {
    private Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    private List<String> tempInventoryList = new ArrayList<>();
    private ObservableList<String> inventoryOList;

    public Button save_Button;

    public TextField productName_Text, currentProd_Text, requiredProd_Text, price_Text;

    public ComboBox<String> supplierName_Combo;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        conn = DbConnection.dbConnection();
        inventoryOList = FXCollections.observableArrayList();
        addSupplierToCombo();

    }

    private void addSupplierToCombo(){
        try{
            pst = conn.prepareStatement("SELECT supplierName FROM suppliers");
            rs = pst.executeQuery();
            while(rs.next()){
                String supplierName = rs.getString("supplierName");
                tempInventoryList.add(supplierName);
            }
            inventoryOList = FXCollections.observableArrayList();
            inventoryOList.setAll(tempInventoryList);
            supplierName_Combo.setItems(inventoryOList);
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void handleSave(){
        int choice = supplierName_Combo.getSelectionModel().getSelectedIndex()+1;
        String productname = productName_Text.getText();
        try{
            pst = conn.prepareStatement("INSERT INTO product (supplier_id, productName, productPrice) VALUES (?, ?, ?)");
            pst.setInt(1, choice);
            pst.setString(2, productName_Text.getText());
            pst.setDouble(3, Double.parseDouble(price_Text.getText()));
            pst.executeUpdate();
        } catch (SQLException ex){

        }
        try{
            pst = conn.prepareStatement("SELECT product_id FROM product WHERE productName = ?");
            pst.setString(1, productname);
            rs = pst.executeQuery();
            while(rs.next()){
                int prodId = rs.getInt("product_id");
                pst = conn.prepareStatement("INSERT INTO inventory (product_id, currentProdAmt, requiredProdAmt) VALUES (?, ?, ?)");
                pst.setInt(1, prodId);
                pst.setDouble(2, Double.parseDouble(currentProd_Text.getText()));
                pst.setDouble(3, Double.parseDouble(requiredProd_Text.getText()));
                pst.executeUpdate();
                Stage close = (Stage)save_Button.getScene().getWindow();
                close.close();
            }
            /*FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/InventoryManagement.fxml"));
            InventoryManagement enfantController = loader.getController();
            enfantController.loadDataFromDatabase();*/
        } catch (SQLException ex){
            ex.getSQLState();
        }
    }
}
