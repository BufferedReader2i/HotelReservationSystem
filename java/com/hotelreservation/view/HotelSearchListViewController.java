package com.hotelreservation.view;


import com.hotelreservation.dao.SearchService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import com.hotelreservation.entity.Hotel;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class HotelSearchListViewController {

    @FXML
    private TextField cityField;
    @FXML
    private TextField areaField;
    @FXML
    private TextField hotelNameField;
    @FXML
    private TextField minPriceField;
    @FXML
    private TextField maxPriceField;
    @FXML
    private DatePicker checkInDate;
    @FXML
    private DatePicker checkOutDate;
    @FXML
    private TableView<Hotel> hotelTable;
    @FXML
    private TableColumn<Hotel, String> hotelNameColumn;
    @FXML
    private TableColumn<Hotel, String> cityColumn;
    @FXML
    private TableColumn<Hotel, String> districtColumn;
    @FXML
    private TableColumn<Hotel, String> addressColumn;

    @FXML
    private Button searchButton;
    @FXML
    private Button viewHotelDetailsButton;



    @FXML
    public void initialize() {




        // 设置表格列属性
        hotelNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Hotel, String>("address"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<Hotel, String>("city"));
        districtColumn.setCellValueFactory(new PropertyValueFactory<Hotel, String>("district"));

        hotelTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !event.isConsumed()) { // 检查是否是双击
                Hotel selectedHotel = hotelTable.getSelectionModel().getSelectedItem();
                if (selectedHotel != null) {


                    goToRoomList(null,selectedHotel);
                    System.out.println("Selected Hotel: " + selectedHotel.getName());
                    // 可以在这里添加更多的逻辑，比如打开一个新的窗口或面板来显示更多信息
                }
                event.consume(); // 标记事件已处理
            }
        });









        hotelTable.refresh();
    }

    @FXML
    protected void handleSearch(ActionEvent event) throws SQLException {
        // 根据用户输入执行搜索逻辑
        // 这里只是示例，实际搜索逻辑可能需要从数据库或远程服务获取数据
        String city = cityField.getText();
        String area = areaField.getText();
        String name = hotelNameField.getText();
        String min  = minPriceField.getText();
        String max  = maxPriceField.getText();

        LocalDate checkIn=checkInDate.getValue();
        LocalDate checkOut=checkOutDate.getValue();
        boolean priceCheck=false;
        if (!min.equals("")&&!min.matches("^\\d+$")) {
            minPriceField.setText("");
            priceCheck=true;

        }
        if (!max.equals("")&&!max.matches("^\\d+$")) {
            maxPriceField.setText("");
            priceCheck=true;
        }
        if (priceCheck==true){
            showAlert("请输入非负整数");
            return;
        }
        if (min.equals(""))min="0";
        if (max.equals(""))max="10000";
        int minPrice=Integer.parseInt(min);
        int maxPrice=Integer.parseInt(max);
        if (maxPrice<minPrice){
            int tmp=minPrice;
            minPrice=maxPrice;
            maxPrice=tmp;
            minPriceField.setText(""+minPrice);
            maxPriceField.setText(""+maxPrice);

        }
         DatePicker empty = new DatePicker();
        if (checkIn==null) {
            checkIn = LocalDate.now();
            checkInDate.setValue(LocalDate.now());
        }

        if (checkOut==null) {
            checkOut = LocalDate.now().plusDays(1);
            checkOutDate.setValue(LocalDate.now().plusDays(1));
        }
        if (checkIn.isAfter(checkOut)){
            checkInDate.setValue(checkOut);
            checkOutDate.setValue(checkIn);
            LocalDate tmp=checkIn;
            checkIn=checkOut;
            checkOut=tmp;


        }
        if (checkIn.isBefore(LocalDate.now())) {
            checkInDate.setValue(LocalDate.now());

        }
        if (checkOut.isBefore(LocalDate.now())){
            checkOutDate.setValue(LocalDate.now().plusDays(1));
            return;
        }



       // System.out.println(checkOut);


        //System.out.println(checkOut);
        //System.out.println(max);



        // 根据输入条件过滤酒店列表
        filterHotels(name, city, area,minPrice,maxPrice,checkIn,checkOut);
    }


    private void filterHotels(String name, String city, String area, int min, int max, LocalDate checkIn, LocalDate checkOut) throws SQLException {
        // 模拟搜索结果过滤逻辑
        // 实际应用中，这里可能需要执行数据库查询或调用后端API

        List<Hotel> hotels= SearchService.searchHotels(name,city,area,min,max,checkIn,checkOut);
        System.out.println("hotels size:"+hotels.size());
        ObservableList<Hotel> filteredHotels = FXCollections.observableArrayList();
        for (Hotel hotel : hotels) {
            filteredHotels.add(hotel);
        }
        hotelTable.setItems(filteredHotels);
    }

    @FXML
    public void goToRoomList(ActionEvent event, Hotel selectedHotel) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hotelreservation/view/RoomView.fxml"));
            Parent registerRoot = loader.load();

            // 创建新舞台（窗口）
            Stage registerStage = new Stage();
            Scene scene = new Scene(registerRoot);
            scene.getStylesheets().add(getClass().getResource("/com/hotelreservation/view/styles.css").toExternalForm());

            Message.HotelID=selectedHotel.getHotelID();
            //System.out.println(Message.HotelID);




            registerStage.setScene(scene);
            registerStage.setTitle("房间列表");

            registerStage.setWidth(600);
            registerStage.setHeight(500);
            registerStage.show(); // 显示新窗口


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*private void showHotelDetails(Hotel hotel) {
        // 弹出详细界面或执行其他逻辑
        // 例如，使用一个Alert来显示详情
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Hotel Details", ButtonType.OK);
        alert.setHeaderText(hotel.getName() + " Details");
        alert.setContentText("Location: " + hotel.getLocation() + "\n" +
                "Price: " + hotel.getPrice() + "\n" +
                "Rating: " + hotel.getRating());
        alert.showAndWait();
    }*/

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.showAndWait();
    }


    public void clear(ActionEvent actionEvent) {
        hotelNameField.clear();
        cityField.clear();
        areaField.clear();
        maxPriceField.clear();
        minPriceField.clear();
        checkInDate.setValue(null);
        checkOutDate.setValue(null);
    }

    public void goToMyOrders(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hotelreservation/view/MyOrdersView.fxml"));
            Parent registerRoot = loader.load();

            // 创建新舞台（窗口）
            Stage registerStage = new Stage();
            Scene scene = new Scene(registerRoot);
            scene.getStylesheets().add(getClass().getResource("/com/hotelreservation/view/styles.css").toExternalForm());






            registerStage.setScene(scene);

            registerStage.setWidth(900);
            registerStage.setHeight(500);
            registerStage.show(); // 显示新窗口
            Stage stage = (Stage)searchButton.getScene().getWindow();
            stage.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void goToLoginView(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hotelreservation/view/LoginView.fxml"));
            Parent registerRoot = loader.load();

            // 创建新舞台（窗口）
            Stage registerStage = new Stage();
            Scene scene = new Scene(registerRoot);
            scene.getStylesheets().add(getClass().getResource("/com/hotelreservation/view/LoginView.css").toExternalForm());






            registerStage.setScene(scene);

            registerStage.setWidth(600);
            registerStage.setHeight(400);
            registerStage.show(); // 显示新窗口
            Stage stage = (Stage)searchButton.getScene().getWindow();
            stage.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}