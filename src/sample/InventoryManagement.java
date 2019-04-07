package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.sql.*;
import java.util.ResourceBundle;

public class InventoryManagement implements Initializable {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    ObservableList<Product> data;


    @FXML
    private TableView<Product> productList;
    @FXML
    private TableColumn<?, ?> productname_Column, productdesc_Column,
            productquantity_Column;
    @FXML
    private TableColumn<Product, Date> productdate_Column;
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
        setCellValue();
        loadDataFromDatabase();

    }

    private void setCellValue(){
        productname_Column.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productdesc_Column.setCellValueFactory(new PropertyValueFactory<>("productDesc"));
        productquantity_Column.setCellValueFactory(new PropertyValueFactory<>("quantityProd"));
        productdate_Column.setCellValueFactory(new PropertyValueFactory<>("productDate"));
    }

    private void loadDataFromDatabase(){
        data.clear();
        try {
            pst = conn.prepareStatement("SELECT * FROM product");
            rs = pst.executeQuery();
            while(rs.next()){
                data.add(new Product(
                        rs.getString("product_name"),
                        rs.getString("product_desc"),
                        rs.getString("quantity"),
                        rs.getDate("productDate")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Product Error");
        }
        productList.setItems(data);
    }

    @FXML
    private void handleAddProduct(){
        Date date = Date.valueOf(productdate_Date.getValue());
        data.clear();
        try{
            pst = conn.prepareStatement("INSERT INTO product(product_name, product_desc, quantity, productDate) " +
                    "VALUES (?, ?, ?, ?)");
            pst.setString(1, productname_Text.getText());
            pst.setString(2, productdesc_TextA.getText());
            pst.setString(3, productquantity_Text.getText());
            pst.setDate(4, date);
            pst.executeUpdate();
            System.out.println("Added Successfully");
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        loadDataFromDatabase();
    }

    @FXML
    private void handleRefresh(){
        loadDataFromDatabase();
    }

    @FXML
    private void loadEditProduct(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("editProduct.fxml"));
        Parent parent = loader.load();

        editProduct controller = loader.getController();

        controller.addDataToController(productList.getSelectionModel().getSelectedItem());

        Stage window = new Stage();
        window.setTitle("Edit Employee");
        window.setScene(new Scene(parent));
        window.show();
    }
}
