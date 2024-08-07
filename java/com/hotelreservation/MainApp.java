package com.hotelreservation;

import com.hotelreservation.view.LoginViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/hotelreservation/view/LoginView.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/com/hotelreservation/view/LoginView.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("酒店预订管理系统 -登录");
            primaryStage.setWidth(600);
            primaryStage.setHeight(400);


            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}