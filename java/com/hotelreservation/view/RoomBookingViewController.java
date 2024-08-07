package com.hotelreservation.view;

import com.hotelreservation.dao.BookingService;
import com.hotelreservation.dao.SearchService;
import com.hotelreservation.entity.Guest;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomBookingViewController {

    @FXML
    private ChoiceBox<String> roomTypeChoiceBox;
    @FXML
    private Label roomAvailabilityLabel;
    @FXML
    private TextField numberOfGuestsField;
    @FXML
    private TableView<Guest> guestsTable;
    @FXML
    private TableColumn<Guest, String> guestNameColumn;
    @FXML
    private TableColumn<Guest, String> guestIDNumberColumn;
    @FXML
    private TableColumn<Guest, Integer> guestAgeColumn;
    @FXML
    private TableColumn<Guest, String> guestGenderColumn;
    @FXML
    private TableColumn<Guest, String> guestOccupationColumn;
    @FXML
    private TableColumn<Guest, String> guestEducationLevelColumn;
    @FXML
    private TableColumn<Guest, String> guestIncomeStatusColumn;


    @FXML
    private Button addGuestButton;
    @FXML
    private TextField guestIdNumberField;
    @FXML
    private TextField guestGenderField;
    @FXML
    private TextField guestOccupationField;
    @FXML
    private TextField guestEducationLevelField;
    @FXML
    private TextField guestIncomeStatusField;
    @FXML
    private Button reserveButton;
    @FXML private TextField roomQuantityField;
    @FXML private Button increaseRoomButton, decreaseRoomButton;

    @FXML
    private Label priceLabel;
    @FXML
    private Label roomCountLabel;
    @FXML
    private TextField guestNameField;
    @FXML
    private TextField guestAgeField;
    @FXML private RadioButton maleRadioButton, femaleRadioButton;
    @FXML private ChoiceBox<String> educationLevelChoiceBox, incomeStatusChoiceBox;
    @FXML
    private DatePicker checkInDatePicker;
    @FXML
    private DatePicker checkOutDatePicker;


    private int availableRoomCount;

    
    private ObservableList<Guest> guests = FXCollections.observableArrayList();
    private int totalPrice;
    List<Integer> availableRooms;


    @FXML
    public void initialize() {
        // 初始化房间类型和对应的最大入住人数
        availableRoomCount=2;




        roomQuantityField.setText("0");


        roomQuantityField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    try {
                        int quantity = Integer.parseInt(newValue);
                        // 如果输入的数量大于上限，则进行处理
                        if (quantity > availableRoomCount) {

                            // 重置为最
                            roomQuantityField.setText(String.valueOf(availableRoomCount));
                        }
                        setPrice();
                    } catch (NumberFormatException e) {
                        // 如果输入的不是数字，则重置为旧值或空字符串
                        roomQuantityField.setText(oldValue != null ? oldValue : "");
                    }
                }
            }
        });




        checkInDatePicker.setPromptText("入住日期");
        checkOutDatePicker.setPromptText("离开日期");
        checkOutDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.isBefore(checkInDatePicker.getValue())) {
                checkOutDatePicker.setValue(checkInDatePicker.getValue().plusDays(1));
            }
        });
/*
        checkInDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (!empty && date != null) {
                    // 定义要禁用的日期范围
                    LocalDate startDate = LocalDate.of(2024, 7, 1);
                    LocalDate endDate = LocalDate.of(2024, 7, 10);

                    // 检查日期是否在禁用的范围内
                    if (date.isAfter(startDate) && date.isBefore(endDate)) {
                        setDisable(true);
                        setStyle("-fx-backgroundcolor: #b01a1a;"); // 禁用日期的样式
                    }
                }
            }
        });
*/


        // 为增加和减少按钮添加事件监听器
        increaseRoomButton.setOnAction(event -> increaseRoom());
        decreaseRoomButton.setOnAction(event -> decreaseRoom());

        guestNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        guestIDNumberColumn.setCellValueFactory(new PropertyValueFactory<>("idNumber"));
        guestGenderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        guestOccupationColumn.setCellValueFactory(new PropertyValueFactory<>("occupation"));
        guestEducationLevelColumn.setCellValueFactory(new PropertyValueFactory<>("educationLevel"));
        guestIncomeStatusColumn.setCellValueFactory(new PropertyValueFactory<>("incomeStatus"));
        guestAgeColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        educationLevelChoiceBox.getItems().addAll("高中", "本科", "研究生", "其他");
        // 初始化收入水平下拉框
        incomeStatusChoiceBox.getItems().addAll("较低", "中等", "高", "未注明");

        // 单选按钮分组，确保一次只能选择一个选项
        ToggleGroup genderGroup = new ToggleGroup();
        maleRadioButton.setToggleGroup(genderGroup);
        femaleRadioButton.setToggleGroup(genderGroup);
    
        
    }

    private void setPrice() {
        String userLevel= SearchService.getuserLevel(Message.username);
       // System.out.println();
        double oriPrice=(Message.price*Integer.parseInt(roomQuantityField.getText()));
        if (userLevel.equals("白银贵宾")){
            totalPrice=(int)(oriPrice*0.95);
        }
        if (userLevel.equals("黄金贵宾")){
            totalPrice=(int)(oriPrice*0.9);
        }
        if (userLevel.equals("普通会员")){
            totalPrice= (int) oriPrice;
            priceLabel.setText("totalPrice: "+totalPrice);
        }else {
            priceLabel.setText("折扣价: "+totalPrice+"   (原价:"+oriPrice+")");
        }
    }


    @FXML
    private void handleAddGuest() {
        String name = guestNameField.getText();
        String ageString = guestAgeField.getText();

        String idNumber = guestIdNumberField.getText();

        String occupation = guestOccupationField.getText();
        LocalDate checkIn=checkInDatePicker.getValue();
        LocalDate checkOut=checkOutDatePicker.getValue();
        int quantity =Integer.parseInt(roomQuantityField.getText());
        String gender = "";
        if (maleRadioButton.isSelected()) {
            gender = "男";
        } else if (femaleRadioButton.isSelected()) {
            gender = "女";
        }
        if (checkIn==null||checkOut==null){
            showWarning("请选择日期");
            return;
        }



        // 获取下拉框选择的值
        String educationLevel = educationLevelChoiceBox.getValue();
        String incomeStatus = incomeStatusChoiceBox.getValue();
        if (name == null || name.trim().isEmpty()) {
            // 如果姓名为空，则显示警告
            showWarning("Please enter the guest's name.");
            return;
        }

        if (ageString == null || ageString.trim().isEmpty()||ageString.matches("^[+]?[1-9]\\\\d*$")) {
            // 如果年龄为空，则显示警告
            showWarning("Please enter the guest's age.");
            return;
        }
        if (idNumber == null || idNumber.trim().isEmpty() || idNumber.length() != 18) {
            showWarning("Please enter a valid ID number (length should be 18).");
            return;
        }

        if (guests.size() >= Message.occupancy*quantity) {
            showAlert("The number of guests exceeds the room capacity.");
            return;
        }

        try {
            int age = Integer.parseInt(ageString); // 尝试将年龄字符串转换为整数
            System.out.println(age);
            Guest newGuest = new Guest(name, idNumber,age , gender, occupation, educationLevel, incomeStatus); // 创建新的客人对象
            guests.add(newGuest); // 将新客人添加到列表中
            guestsTable.setItems(guests); // 更新表格视图
            guestNameField.clear(); // 清空姓名字段
            guestAgeField.clear(); // 清空年龄字段
            guestIdNumberField.clear();

        } catch (NumberFormatException e) {
            // 如果年龄不是有效的整数，则显示警告
            showWarning("Please enter a valid age number.");
        }
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.showAndWait();
    }    @FXML
    private void handleReserveRoom() throws SQLException {
        // 执行预订逻辑
        if (guests.isEmpty()){
            showWarning("Please add guest!");
            return;
        }

        LocalDate checkIn=checkInDatePicker.getValue();
        LocalDate checkOut=checkOutDatePicker.getValue();

        BookingService.bookRoom(Message.username,checkIn,checkOut,totalPrice,"Booked",LocalDate.now(),availableRooms,Message.HotelID, Integer.parseInt(roomQuantityField.getText()));

        System.out.println(totalPrice);
        // 显示预订成功的信息
        int orderID=BookingService.getOrderID();
        for (Guest guest:guests){
            BookingService.addGuests(orderID,guest.getName(),guest.getIdNumber(),guest.getAge(),guest.getGender(),guest.getOccupation(),guest.getEducationLevel(),guest.getIncomeStatus());
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Reservation successful!", ButtonType.OK);

        alert.showAndWait();
        Stage stage = (Stage)decreaseRoomButton.getScene().getWindow();
        stage.close();

    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.showAndWait();
    }

    public void clearGuest(ActionEvent actionEvent) {
        guests.clear(); // 清空客人列表
        guestsTable.setItems(guests); // 可选：重新设置表格项，以确保表格视图更新
    }
    private void increaseRoom() {
        // 增加房间数量
        LocalDate checkIn=checkInDatePicker.getValue();
        LocalDate checkOut=checkOutDatePicker.getValue();
        if (checkIn==null||checkOut==null){
            showWarning("please choice dates");
            return;
        }

        if (checkIn.isBefore(LocalDate.now())) {
            checkInDatePicker.setValue(LocalDate.now());

        }
        if (checkOut.isBefore(LocalDate.now())){
            checkOutDatePicker.setValue(LocalDate.now().plusDays(1));

        }
        if (checkIn.isBefore(LocalDate.now())||checkOut.isBefore(LocalDate.now())){
            showWarning("dates can not after today");
            return;
        }
        availableRooms= SearchService.searchAvabileRooms(checkIn,checkOut,Message.HotelID,Message.roomType);
        //System.out.println(checkIn+" "+checkOut+" "+Message.HotelID+Message.roomType);
        availableRoomCount=availableRooms.size();
        roomCountLabel.setText("Remaining rooms:"+availableRoomCount);
        System.out.println("availableRoomCount:"+availableRoomCount);
        int currentRoomCount = Integer.parseInt(roomQuantityField.getText());
        if (currentRoomCount ==availableRoomCount){
            showWarning("无更多可用房间");
            return;
        }
        roomQuantityField.setText(String.valueOf(currentRoomCount + 1));
    }

    private void decreaseRoom() {
        // 减少房间数量，确保数量不会小于1
        int currentRoomCount = Integer.parseInt(roomQuantityField.getText());
        if (currentRoomCount > 1) {
            roomQuantityField.setText(String.valueOf(currentRoomCount - 1));
        }
    }

}