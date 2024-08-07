package com.hotelreservation.view;

import com.hotelreservation.dao.SearchService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import com.hotelreservation.entity.Order;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class MyOrdersViewController {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<Order> ordersTable;
    @FXML
    private TableColumn<Order, String> hotelNameColumn;
    @FXML
    private TableColumn<Order, String> descriptionColumn;
    @FXML
    private TableColumn<Order, String> roomTypeNameColumn;

    @FXML
    private TableColumn<Order, Date> checkInDateColumn;
    @FXML
    private TableColumn<Order, Date> checkOutDateColumn;
    @FXML
    private TableColumn<Order, Double> totalPriceColumn;
    @FXML
    private TableColumn<Order, String> statusColumn;
    @FXML
    private TableColumn<Order, String> actionColumn;

    @FXML
    private TableColumn<Order, String> reviewColumn;
    @FXML
    private TableColumn<Order, String> commitColumn;


    @FXML
    private ChoiceBox<String> statusChoiceBox;
    String commitText;




    // 模拟订单数据
    private ObservableList<Order> orders = FXCollections.observableArrayList();
    private ObservableList<Order> getAllOrders() throws ParseException, SQLException {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        List<Order> orderList= SearchService.getUserOrders(Message.username);
        for (Order order:orderList){
                orders.add(order);
        }

        // 添加一些模拟订单数据
      //  orders.add(new Order("1", "Alice", "Hotel Central", dateFormat.parse("2024-06-01"), dateFormat.parse("2024-06-05"), "Completed", "Great stay!"));
       // orders.add(new Order("2", "Bob", "Beach Resort", dateFormat.parse("2024-07-10"), dateFormat.parse("2024-07-15"), "Booked", "Looking forward to it!"));
        // ... 可以添加更多订单数据

        return orders;
    }
    @FXML
    public void initialize() throws ParseException, SQLException {
        statusChoiceBox.setItems(FXCollections.observableArrayList(
                "All", "Booked", "Completed", "Cancelled"
        ));
        statusChoiceBox.getSelectionModel().selectFirst();
        //guestNameColumn.setCellValueFactory(new PropertyValueFactory<>("guestName"));
        hotelNameColumn.setCellValueFactory(new PropertyValueFactory<>("hotelName"));
        roomTypeNameColumn.setCellValueFactory(new PropertyValueFactory<>("roomTypeName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        checkInDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        checkOutDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        reviewColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        // 为操作列添加按钮
        actionColumn.setCellFactory(column -> {
            Button actionButton = new Button("Cancel");
            actionButton.getStyleClass().add("cancel-button");
            actionButton.setOnAction(event -> {
                // 获取当前单元格的TableView
                TableView<Order> tableView = column.getTableView();
                // 获取当前单元格的行号
                if (tableView.getFocusModel()==null){
                    new Alert(Alert.AlertType.WARNING, "Please select an order to cancel.", ButtonType.OK).showAndWait();
                    return;
                }



                int rowIndex = tableView.getFocusModel().getFocusedIndex();
                if (rowIndex >= 0) {
                    // 获取当前行的订单项
                    Order order = tableView.getItems().get(rowIndex);
                    //System.out.println(order.getorderID());
                    // 执行取消订单逻辑
                    try {
                        cancelOrder(order);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
            return new TableCell<Order, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(actionButton);
                    }
                }
            };
        });
        // 设置评价列的单元格值工厂
        reviewColumn.setCellFactory(column -> {
            TextArea reviewArea = new TextArea();

            reviewArea.setWrapText(true);
            reviewArea.setEditable(true); // 默认评价不可编辑
            return new TableCell<Order, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        reviewArea.setText(item);
                        commitText=reviewArea.getText();
                        setGraphic(reviewArea);
                    }
                }
            };
        });
        commitColumn.setCellFactory(column -> {
            Button commitButton = new Button("review");
            commitButton.getStyleClass().add("checkIn-button");
            commitButton.setOnAction(event -> {
                // 获取当前单元格的 TableView
                TableView<Order> tableView = column.getTableView();
                // 检查是否有行被选中
                if (tableView.getFocusModel() == null || tableView.getFocusModel().getFocusedIndex() == -1) {
                    new Alert(Alert.AlertType.WARNING, "Please select an order to commit.", ButtonType.OK).showAndWait();
                    return;
                }
                int rowIndex = tableView.getFocusModel().getFocusedIndex();
                Order order = tableView.getItems().get(rowIndex);

                // 创建并显示评论输入对话框
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Commit Review");
                dialog.setHeaderText("Enter your review for the order:");
                dialog.setContentText("Review:");

                // 用户点击确认后执行
                Optional<String> result = dialog.showAndWait();
                result.ifPresent(reviewText -> {
                    // 执行提交评论逻辑

                    SearchService.editOrderCommit(reviewText, order.getorderID()); // 假设getOrderId()是获取订单ID的正确方法
                    try {
                        handleFilter(null);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                });
            });
            return new TableCell<Order, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(commitButton);
                    }
                }
            };
        });
        //ordersTable.setItems(orders);
        ordersTable.setItems(getAllOrders());
    }

    @FXML
    private void handleFilter(ActionEvent event) throws SQLException, ParseException {
        String selectedStatus = statusChoiceBox.getValue();
        filterOrders(selectedStatus);
    }

    private void filterOrders(String status) throws SQLException, ParseException {
        ObservableList<Order> filteredList = FXCollections.observableArrayList();

        if("All".equals(status)) {
            filteredList.addAll(getAllOrders());
        } else {
            for(Order order : getAllOrders()) {
                if(status.equals(order.getStatus())) {
                    filteredList.add(order);
                }
            }
        }

        ordersTable.setItems(filteredList);
    }

    private void cancelOrder(Order order) throws SQLException, ParseException {
        // 创建一个确认对话框
        if (!order.getStatus().equals("Booked")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "This order cannot be cancelled.", ButtonType.OK);
            errorAlert.show();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to cancel this order?", // 消息正文
                ButtonType.YES, ButtonType.NO); // 按钮类型

        // 设置对话框的标题和头部文本（可选）
        alert.setTitle("Confirm Cancel Order");
        alert.setHeaderText("Order Cancellation");

        // 显示对话框并等待用户响应
        Optional<ButtonType> result = alert.showAndWait();
        System.out.println(order.getStatus());

        // 检查用户的响应是否是“是”
        if (result.isPresent() && result.get() == ButtonType.YES) {
            // 检查订单状态是否可以取消
            if (order.getStatus().equals("Booked")) {
                // 执行取消订单的逻辑，例如调用后端服务API
                handleFilter(null);
                SearchService.editOrderStatus("Cancelled",order.getorderID());
                handleFilter(null);


                // 可以在这里刷新表格或更新UI
                // 例如：updateOrdersTable();
            } else {
                // 显示提示，订单状态不允许取消
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "This order cannot be cancelled.", ButtonType.OK);
                errorAlert.show();
            }
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
            Stage stage = (Stage)statusChoiceBox.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}