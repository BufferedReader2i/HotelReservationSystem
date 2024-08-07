package com.hotelreservation.view;

import com.hotelreservation.dao.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterViewController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField emailField;
    @FXML
    private Button registerButton;
    @FXML
    private Hyperlink backToLoginLink;
    @FXML
    private Label messageLabel;

    @FXML
    protected void handleRegister(ActionEvent event) throws InterruptedException {
        // 在这里添加注册逻辑
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if (LoginService.userExist(username)){
            messageLabel.setText("用户已经存在");
            messageLabel.setTextFill(Color.RED);

            return;
        }
        if (username.equals("")){
            messageLabel.setText("请输入用户名");
            messageLabel.setTextFill(Color.RED);
            return;
        }
        if (!password.equals(confirmPassword)){
            messageLabel.setText("两次密码不一致");
            messageLabel.setTextFill(Color.RED);
            return;
        }

        LoginService.createAccount(username,password);


        goToLogin(null);



    }
    public void goToSearchList() {
        try {
            // 加载 RegisterView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hotelreservation/view/HotelSearchListView.fxml"));
            Parent registerRoot = loader.load();

            // 创建新舞台（窗口）
            Stage registerStage = new Stage();
            Scene scene = new Scene(registerRoot);
            scene.getStylesheets().add(getClass().getResource("/com/hotelreservation/view/styles.css").toExternalForm());
            registerStage.setTitle("Register - Hotel Reservation System");

            registerStage.setScene(scene);

            registerStage.show(); // 显示新窗口

            // 可选：关闭当前登录窗口
            Stage stage = (Stage)registerButton.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void goToLogin(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hotelreservation/view/LoginView.fxml"));
            Parent registerRoot = loader.load();

            // 创建新舞台（窗口）
            Stage registerStage = new Stage();
            Scene scene = new Scene(registerRoot);
            scene.getStylesheets().add(getClass().getResource("/com/hotelreservation/view/LoginView.css").toExternalForm());
            registerStage.setTitle("Hotel Reservation System - Login");

            registerStage.setScene(scene);
            registerStage.setWidth(600);
            registerStage.setHeight(400);
            registerStage.show(); // 显示新窗口

            // 可选：关闭当前登录窗口
            Stage stage = (Stage)registerButton.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}