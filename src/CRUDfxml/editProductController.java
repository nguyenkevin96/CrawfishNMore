package CRUDfxml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.DbConnection;
import sample.Inventory;

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

    public TextField currentProd_Text, requiredProd_Text;

    public Label editTitle_Label;

    public ComboBox<String> supplierName_Combo;

    @Override
    public void initialize(URL url, ResourceBundle rs){
        conn = DbConnection.dbConnection();
        addSupplierToCombo();
    }

    public void addDataToController(Inventory inventory){
        supplierName_Combo.setValue(inventory.getSupplierName());
        currentProd_Text.setText(String.valueOf(inventory.getCurrentP()));
        requiredProd_Text.setText(String.valueOf(inventory.getRequiredP()));
    }

    private void addSupplierToCombo(){
        try{
            pst = conn.prepareStatement("SELECT supplierName FROM suppliers");
            rs = pst.executeQuery();
            while(rs.next()){
                String supplierName = rs.getString("supplierName");
                tempSupplierList.add(supplierName);
            }
            supplierOList = FXCollections.observableArrayList();
            supplierOList.setAll(tempSupplierList);
            supplierName_Combo = new ComboBox<>(supplierOList);
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void handleSave(){
        /*try{

        } catch (SQLException ex){
            ex.printStackTrace();
        }*/
    }
}
