package com.hotelreservation.view;

import com.hotelreservation.dao.SearchService;
import com.hotelreservation.entity.Hotel;
import com.hotelreservation.entity.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RoomViewController {



    @FXML
    private TableView<Room> roomTableView;

    @FXML
    private TableColumn<Room, String> roomTypeColumn;
    @FXML
    private TableColumn<Room, String> descriptionColumn;
    @FXML
    private TableColumn<Room, Double> priceColumn;

    public Hotel selectedHotel;
    public static double price;



    @FXML
    public void initialize() {
        // 初始化列的单元格值工厂
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Room, Double>("price"));
        // 从数据库加载数据
        roomTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !event.isConsumed()) { // 检查是否是双击
                Room selectedroom = roomTableView.getSelectionModel().getSelectedItem();
                if (selectedroom != null) {


                    goToReaservationView(null,selectedroom);
                    System.out.println("Selected room: " + selectedroom.getName());

                }
                event.consume(); // 标记事件已处理
            }
        });





    }
    @FXML
    public void goToReaservationView(ActionEvent event, Room selectedroom) {


        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hotelreservation/view/RoomBookingView.fxml"));
            Parent registerRoot = loader.load();

            // 创建新舞台（窗口）
            Stage registerStage = new Stage();
            Scene scene = new Scene(registerRoot);
            scene.getStylesheets().add(getClass().getResource("/com/hotelreservation/view/styles.css").toExternalForm());
            Message.roomType=selectedroom.getName();
            Message.price=selectedroom.getPrice();
            Message.occupancy=selectedroom.getOccupancy();






            registerStage.setScene(scene);
            registerStage.setTitle(Message.roomType);

            registerStage.setWidth(850);
            registerStage.setHeight(650);
            registerStage.show(); // 显示新窗口


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleSearch(ActionEvent actionEvent) throws SQLException {

        List<Room> rooms= SearchService.searchRooms(Message.HotelID);

        ObservableList<Room> filteredHotels = FXCollections.observableArrayList();
        for (Room room : rooms) {
            filteredHotels.add(room);
           // System.out.println();
        }
        roomTableView.setItems(filteredHotels);
        roomTableView.refresh();
    }
}

