package sample;

import com.ibatis.common.jdbc.ScriptRunner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {
    public Connection conn = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Crawfish N' More System");
        primaryStage.setScene(new Scene(root, 420, 280));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void changeWindow(ActionEvent event, String location, int x, int y) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(location));
        Scene scene = new Scene(parent, x, y);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
