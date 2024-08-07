package com.hotelreservation.view;

import com.hotelreservation.dao.LoginService;
import javafx.fxml.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginViewController {

    @FXML
    private TextField usernameField;



    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink registerLink; // 登录链接

    @FXML
    private Label messageLabel;
    @FXML
    private Label userTypeLabel;
    @FXML
    private Button changeButton;
    int userType=1;

    @FXML
    public void initialize() {
        // 为按钮设置事件监听器
        changeButton.setOnAction(this::changeUserType);
    }
    @FXML
    protected void handleLogin(ActionEvent event) {
        // 登录逻辑处理...

        if (userType==0){
            handleLoginAdmin(null);
            return;
        }
        //goToSearchList(null);


        String username = usernameField.getText();
        String password = passwordField.getText();



       if (!LoginService.userExist(username)){
           messageLabel.setText("用户不存在");
           messageLabel.setTextFill(Color.RED);

           return;
       }

        if (LoginService.Login(username,password,1)) {
            Message.username=username;
            messageLabel.setText("登录成功!");
            messageLabel.setTextFill(Color.BLUE);
            goToSearchList(null);

        } else {
            messageLabel.setText("密码错误!");
            messageLabel.setTextFill(Color.RED);
        }
    }
    
    protected void handleLoginAdmin(ActionEvent event) {
       // goToAdminOrders(null, "wang");

        

        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.equals("")){
            messageLabel.setText("用户不存在");
            messageLabel.setTextFill(Color.RED);

            return;
        }





        if (LoginService.Login(username,password,0)) {
            Message.username=username;
            messageLabel.setText("登陆成功!");
            goToAdminOrders(null,username);

        } else {
            messageLabel.setText("密码错误!");
            messageLabel.setTextFill(Color.RED);
        }
    }

    @FXML
    public void goToRegister(ActionEvent event) {
        try {
            // 加载 RegisterView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hotelreservation/view/RegisterView.fxml"));
            Parent registerRoot = loader.load();

            // 创建新舞台（窗口）
            Stage registerStage = new Stage();
            Scene scene = new Scene(registerRoot);
            scene.getStylesheets().add(getClass().getResource("/com/hotelreservation/view/RegisterView.css").toExternalForm());
            registerStage.setTitle("Hotel Reservation System");

            registerStage.setScene(scene);
            registerStage.setWidth(600);
            registerStage.setHeight(400);
            registerStage.show(); // 显示新窗口

            // 可选：关闭当前登录窗口
            Stage stage = (Stage)loginButton.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void goToSearchList(ActionEvent event) {
        try {
            // 加载 RegisterView.fxml

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hotelreservation/view/HotelSearchListView.fxml"));
            Parent registerRoot = loader.load();

            // 创建新舞台（窗口）
            Stage registerStage = new Stage();
            Scene scene = new Scene(registerRoot);
            scene.getStylesheets().add(getClass().getResource("/com/hotelreservation/view/styles.css").toExternalForm());
            registerStage.setTitle("Hotel Reservation System");

            registerStage.setScene(scene);

            registerStage.show(); // 显示新窗口

            // 可选：关闭当前登录窗口
            Stage stage = (Stage)loginButton.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToAdminOrders(ActionEvent event, String username) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hotelreservation/view/OrderView.fxml"));
            Parent registerRoot = loader.load();

            // 创建新舞台（窗口）
            Stage registerStage = new Stage();
            Scene scene = new Scene(registerRoot);
            scene.getStylesheets().add(getClass().getResource("/com/hotelreservation/view/styles.css").toExternalForm());
            registerStage.setTitle("Hotel Reservation System");
            Message.HotelID= LoginService.getHotelID(username);

            registerStage.setScene(scene);

            registerStage.show(); // 显示新窗口

            // 可选：关闭当前登录窗口
            Stage stage = (Stage)loginButton.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void onLoginKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleLogin(null);
        }
    }



    public void changeUserType(ActionEvent actionEvent) {
        // 切换用户类型
        userType = (userType == 1) ? 0 : 1;
        // 更新标签显示System.out.println(userType);
        changeButton.setText("切换至" + ((userType == 0) ? "用户" : "管理员"));

    }
}