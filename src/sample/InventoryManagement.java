package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class InventoryManagement implements Initializable {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    ObservableList<Inventory> data;


    @FXML
    private TableView<Inventory> productList;

    public  TableColumn<?, ?> productname_Column, productid_Column, currentProd_Column, requiredProd_Column;

    @Override
    public void initialize(URL url, ResourceBundle rB){
        conn = DbConnection.dbConnection();
        data = FXCollections.observableArrayList();
        setCellValue();
        loadDataFromDatabase();

    }

    private void setCellValue(){
        productname_Column.setCellValueFactory(new PropertyValueFactory<>("productName"));
        currentProd_Column.setCellValueFactory(new PropertyValueFactory<>("currentProd"));
        requiredProd_Column.setCellValueFactory(new PropertyValueFactory<>("requiredProd"));
    }

    private void loadDataFromDatabase(){
        data.clear();
        try {
            pst = conn.prepareStatement("SELECT product.productName, inventory.currentProdAmt, inventory.requiredProdAmt " +
                    "FROM inventory " +
                    "INNER JOIN product " +
                    "ON inventory.product_id = product.product_id");
            rs = pst.executeQuery();
            while(rs.next()){
                data.add(new Inventory(
                        rs.getString("productName"),
                        rs.getInt("currentProdAmt"),
                        rs.getInt("requiredProdAmt")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        productList.setItems(data);
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
