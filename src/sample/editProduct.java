package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class editProduct implements Initializable {
    private String prodName, prodQuantity, prodDesc;
    private Date prodDate;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @FXML
    private TextField productName_Text, productQuantity_Text;
    @FXML
    private DatePicker productDate_Text;
    @FXML
    private TextArea productDesc_TextA;

    public void addDataToController(Product product){
        productName_Text.setText(product.getProductName());
        productQuantity_Text.setText(product.getQuantityProd());
        productDate_Text.setValue(product.getProductDate().toLocalDate());
        productDesc_TextA.setText(product.getProductDesc());
    }

    private boolean editProduct(ActionEvent event, Product product){
        try{
            pst = conn.prepareStatement("UPDATE product SET quantity = ?, productDate = ?, product_desc = ? WHERE product_name = ?");
            pst.setString(1, product.getProductName());
            pst.setDate(2, product.getProductDate());
            pst.setString(3, product.getProductDesc());
            pst.setString(4, product.getProductName());
            int i = pst.executeUpdate();
            return (i > 0);
        } catch (SQLException ex){
            System.out.println("MenuItem Edit Error");
        }
        return false;
    }

    @FXML
    private void handleEditProduct(){
        java.sql.Date getDate = java.sql.Date.valueOf(productDate_Text.getValue());
        try{
            pst = conn.prepareStatement("UPDATE product SET product_desc = ? WHERE product_name = ?");
            pst.setString(1, productDesc_TextA.getText());
            pst.setString(2, productName_Text.getText());
            pst.executeUpdate();
        } catch (SQLException ex){

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        conn = DbConnection.dbConnection();
    }
}
