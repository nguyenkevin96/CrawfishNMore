package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class editProduct implements Initializable {
    private String prodName, prodQuantity, prodDesc;
    private Date prodDate;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @FXML
    private TextField productName_Text, currentProd_Text, requiredProd_Text;

    public void addDataToController(Inventory product){
        productName_Text.setText(product.getProductName());
        currentProd_Text.setText(String.valueOf(product.getProductid()));
        requiredProd_Text.setText(String.valueOf(product.getCurrentProd()));
    }

/*    private boolean editProduct(ActionEvent event, Inventory inventory){
        try{
            pst = conn.prepareStatement("");
            pst.setString(1, inventory.getProductName());
            pst.setDate(2, inventory.getProductDate());
            pst.setString(3, inventory.getProductDesc());
            pst.setString(4, inventory.getProductName());
            int i = pst.executeUpdate();
            return (i > 0);
        } catch (SQLException ex){
            System.out.println("MenuItem Edit Error");
        }
        return false;
    }

    @FXML
    private void handleEditProduct(){
        Date getDate = Date.valueOf(productDate_Text.getValue());
        try{
            pst = conn.prepareStatement("UPDATE product SET quantity = ?, productDate = ?, product_desc = ? WHERE product_name = ?");
            pst.setString(1, productQuantity_Text.getText());
            pst.setDate(2, getDate);
            pst.setString(3, productDesc_TextA.getText());
            pst.setString(4, productName_Text.getText());
            pst.executeUpdate();
        } catch (SQLException ex){

        }
    }*/

    @Override
    public void initialize(URL url, ResourceBundle rb){
        conn = DbConnection.dbConnection();
    }
}
