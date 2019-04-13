package sample;

import CRUDfxml.editProductController;
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

    public ObservableList<Inventory> data;

    public Button refresh_Button;

    Main main = new Main();

    @FXML
    private TableView<Inventory> productList;

    public  TableColumn<?, ?> supplierName_Column, productname_Column, currentProd_Column, requiredProd_Column;

    @Override
    public void initialize(URL url, ResourceBundle rB){
        conn = DbConnection.dbConnection();
        data = FXCollections.observableArrayList();
        setCellValue();
        loadDataFromDatabase();

    }

    private void setCellValue(){
        supplierName_Column.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        productname_Column.setCellValueFactory(new PropertyValueFactory<>("productName"));
        currentProd_Column.setCellValueFactory(new PropertyValueFactory<>("currentP"));
        requiredProd_Column.setCellValueFactory(new PropertyValueFactory<>("requiredP"));
    }

    public void loadDataFromDatabase(){
        data.clear();
        try {
            pst = conn.prepareStatement("SELECT suppliers.supplierName, product.productName, inventory.currentProdAmt, inventory.requiredProdAmt " +
                    "FROM inventory " +
                    "INNER JOIN product " +
                    "ON inventory.product_id = product.product_id " +
                    "INNER JOIN suppliers " +
                    "ON product.supplier_id = suppliers.supplier_id");
            rs = pst.executeQuery();
            while(rs.next()){
                data.add(new Inventory(
                        rs.getString("supplierName"),
                        rs.getString("productName"),
                        rs.getDouble("currentProdAmt"),
                        rs.getDouble("currentProdAmt")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        productList.setItems(data);
    }

    public void handleAddEmployee(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CRUDfxml/addProduct.fxml"));
        Parent parent = loader.load();
        Stage window = new Stage();
        window.setTitle("Add Employee");
        window.setScene(new Scene(parent));
        window.show();
    }

    public void handleBack(ActionEvent event) throws IOException{
        main.changeWindow(event, "/sample/AdminMenu.fxml", 1068, 615);
    }

    public void handleEditProduct() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CRUDfxml/editProduct.fxml"));
        Parent parent = loader.load();

        editProductController controller = loader.getController();
        controller.addDataToController(productList.getSelectionModel().getSelectedItem());

        Stage window = new Stage();
        //window.setTitle("Edit " + productList.getSelectionModel().getSelectedItem().getProductName());
        window.setScene(new Scene(parent));
        window.show();
    }

    public void handleDeleteProduct(){
        Inventory in = productList.getSelectionModel().getSelectedItem();
        System.out.println(in);
    }

    /*@FXML
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
    }*/
}
