package CRUDfxml;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import sample.DbConnection;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CustomSQLController implements Initializable {
    private Connection conn = null;
    private PreparedStatement pst = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    public TextArea customSQL_TextA;

    public TableView customSQLTableView;

    public Label customSQL_Label;

    public Button customSQL_Button;

    private ObservableList<ObservableList> tableData;

    @Override
    public void initialize(URL url, ResourceBundle rs){
        conn = DbConnection.dbConnection();
        tableData = FXCollections.observableArrayList();
        customSQL_Button.setDisable(true);
        customSQL_TextA.textProperty().addListener( (v, oldValue, newValue) -> {
            if(customSQL_TextA.getText().equals(""))
                customSQL_Button.setDisable(true);
            else
                customSQL_Button.setDisable(false);

        });
    }

    public void handleCustomSQL(){
        customSQLTableView.getColumns().clear();
        customSQLTableView.getItems().clear();
        String sql = customSQL_TextA.getText();
        customSQL_TextA.setText("");

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> x) {
                        return new SimpleStringProperty(x.getValue().get(j).toString());
                    }
                });
                customSQLTableView.getColumns().addAll(col);
            }

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                tableData.add(row);
            }

            customSQLTableView.setItems(tableData);
        } catch (Exception e) {
            e.getMessage();
        }
    }

}
