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

    public ObservableList<MenuItem> data;

    public Button refresh_Button;

    Main main = new Main();

    @FXML
    private TableView<MenuItem> productList;

    public  TableColumn<?, ?> menuItemName_Column, menuType_Column, price_Column, description_Column;

    @Override
    public void initialize(URL url, ResourceBundle rB){
        conn = DbConnection.dbConnection();
        data = FXCollections.observableArrayList();
        setCellValue();
        loadDataFromDatabase();

    }

    private void setCellValue(){
        menuItemName_Column.setCellValueFactory(new PropertyValueFactory<>("menuItemN"));
        menuType_Column.setCellValueFactory(new PropertyValueFactory<>("menuType"));
        price_Column.setCellValueFactory(new PropertyValueFactory<>("menuItemP"));
        description_Column.setCellValueFactory(new PropertyValueFactory<>("menuItemD"));
    }

    public void loadDataFromDatabase(){
        data.clear();
        try {
            pst = conn.prepareStatement("SELECT menuItemName, menu_id, menuItemPrice, menuItemDesc FROM menuItems");
            rs = pst.executeQuery();
            while(rs.next()){
                data.add(new MenuItem(
                        rs.getString("menuItemName"),
                        rs.getInt("menu_id"),
                        rs.getDouble("menuItemPrice"),
                        rs.getString("menuItemDesc")
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
        try{
            pst = conn.prepareStatement("DELETE FROM menuItems WHERE menuItemName = ?");
            pst.setString(1, productList.getSelectionModel().getSelectedItem().getMenuItemN());
            pst.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        loadDataFromDatabase();
    }
}
