package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InventoryManagement implements Initializable {
    Connection conn = null;
    PreparedStatement pst = null;
    ObservableList<Product> data;


    @FXML
    private TableView<Product> productList;
    @FXML
    private TableColumn<?, ?> productname_Column, productdesc_Column,
                            productquantity_Column, productdate_Column;
    @FXML
    private TextField productname_Text, productquantity_Text;
    @FXML
    private DatePicker productdate_Date;
    @FXML
    private TextArea productdesc_TextA;

    @Override
    public void initialize(URL url, ResourceBundle rB){
        conn = DbConnection.dbConnection();
        data = FXCollections.observableArrayList();

    }

    private void setCellValue(){
        productname_Column.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productdesc_Column.setCellValueFactory(new PropertyValueFactory<>("productDesc"));
        productquantity_Column.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productdate_Column.setCellValueFactory(new PropertyValueFactory<>("productDate"));
    }







    @FXML
    private void handleProductEdit(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/window/editProduct.fxml"));

            Product selected = productList.getItems().get(productList.getSelectionModel().getSelectedIndex());
            productname_Column.setText(selected.getProductName());
            Stage stage = new Stage();
            stage.setTitle("Edit Product");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
