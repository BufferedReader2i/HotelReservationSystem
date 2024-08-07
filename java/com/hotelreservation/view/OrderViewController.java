package com.hotelreservation.view;

import com.hotelreservation.dao.BookingService;
import com.hotelreservation.dao.SearchService;
import com.hotelreservation.entity.Order;
import com.hotelreservation.entity.Statics;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class OrderViewController {

    @FXML
    private TextField orderIdField;
    @FXML
    private TextField statusField;
    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order, String> orderIdColumn;
    @FXML
    private TableColumn<Order, String> guestNameColumn;
    @FXML
    private TableColumn<Order, String> hotelNameColumn;
    @FXML
    private TableColumn<Order, String> roomTypeColumn;
    @FXML
    private TableColumn<Order, Date> checkInDateColumn;
    @FXML
    private TableColumn<Order, Date> checkOutDateColumn;
    @FXML
    private TableColumn<Order, String> statusColumn;
    @FXML
    private TableColumn<Order, Double> totalPriceColumn;
    @FXML
    private TableColumn<com.hotelreservation.entity.Order, String> actionColumn;
    @FXML
    private TableColumn<Order, String> reviewColumn;
    @FXML
    private ChoiceBox<String> statusChoiceBox;

    private ObservableList<Order> orders = FXCollections.observableArrayList();


    @FXML
    public void initialize() throws SQLException {
        statusChoiceBox.setItems(FXCollections.observableArrayList(
                "All", "Booked", "Completed", "Cancelled"
        ));
        statusChoiceBox.getSelectionModel().selectFirst();
        hotelNameColumn.setCellValueFactory(new PropertyValueFactory<>("hotelName"));

        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomTypeName"));
        checkInDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        checkOutDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        reviewColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));

        actionColumn.setCellFactory(column -> {
            Button actionButton = new Button("check in");
            actionButton.getStyleClass().add("checkIn-button");
            actionButton.setOnAction(event -> {
                // 获取当前单元格的TableView
                TableView<com.hotelreservation.entity.Order> tableView = column.getTableView();
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
                    if (order.getStatus().equals("Booked")){
                        SearchService.editOrderStatus("Completed",order.getorderID());
                        try {
                            update();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        return;
                    }


                }
            });
            return new TableCell<com.hotelreservation.entity.Order, String>() {
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
        reviewColumn.setCellFactory(column -> {
            TextArea reviewArea = new TextArea();

            reviewArea.setWrapText(true);
            reviewArea.setEditable(false); // 默认评价不可编辑
            return new TableCell<Order, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        reviewArea.setText(item);
                        setGraphic(reviewArea);
                    }
                }
            };
        });

        update();


    }
/*@FXML
private void updateByHotel() throws SQLException {
    ObservableList<Order> orders = FXCollections.observableArrayList();
    List<Order> orderList = SearchService.getOrders(Message.HotelID);

    for (Order order : orderList) {
        orders.add(order);
        //System.out.println(order.getOrderDate());

    }
    orderTable.setItems(orders);
}*/
    @FXML
    private void update() throws SQLException {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        List<Order> orderList = SearchService.getALLOrders();

        for (Order order : orderList) {
            orders.add(order);
            //System.out.println(order.getOrderDate());

        }
        orderTable.setItems(orders);
    }






    private ObservableList<Order> getAllOrders() throws ParseException, SQLException {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        List<Order> orderList= SearchService.getALLOrders();
        for (Order order:orderList){
            orders.add(order);
        }



        return orders;
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

        orderTable.setItems(filteredList);
    }

@FXML
    public void goToStatics2(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hotelreservation/view/PieCharts2.fxml"));
            Parent registerRoot = loader.load();

            // 创建新舞台（窗口）
            Stage registerStage = new Stage();
            Scene scene = new Scene(registerRoot);
            scene.getStylesheets().add(getClass().getResource("/com/hotelreservation/view/styles.css").toExternalForm());






            registerStage.setScene(scene);

            registerStage.setWidth(600);
            registerStage.setHeight(600);
            registerStage.show(); // 显示新窗口



        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void goToStatics(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hotelreservation/view/PieCharts1.fxml"));
            Parent registerRoot = loader.load();

            // 创建新舞台（窗口）
            Stage registerStage = new Stage();
            Scene scene = new Scene(registerRoot);
            scene.getStylesheets().add(getClass().getResource("/com/hotelreservation/view/styles.css").toExternalForm());






            registerStage.setScene(scene);

            registerStage.setWidth(600);
            registerStage.setHeight(600);
            registerStage.show(); // 显示新窗口



        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void goToRoomTypeStatics(ActionEvent actionEvent) {

        // 创建两个DatePicker控件
        DatePicker datePicker1 = new DatePicker();
        DatePicker datePicker2 = new DatePicker();

        // 创建一个垂直布局的VBox，并设置间距
        HBox hbox = new HBox(10); // 间距为10
        Button button=new Button();
        button.setOnAction(event -> {
            LocalDate begin=LocalDate.of(2024,7,1);
            LocalDate end=LocalDate.of(2024,7,2);
            if (begin==null||end==null){
                return;
            }

            Stage primaryStage=new Stage();
            CategoryAxis xAxis    = new CategoryAxis();


            NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("房型");
            yAxis.setLabel("入住率");

            BarChart barChart = new BarChart(xAxis, yAxis);

            XYChart.Series dataSeries1 = new XYChart.Series();
            dataSeries1.setName("");
            List<Statics> datas = SearchService.getData("CalculateRoomOccupancyRate", begin, end);
            for (Statics data : datas) {
                dataSeries1.getData().add(new XYChart.Data<>(data.getName(), data.getValue()));
            }


            barChart.getData().add(dataSeries1);


            VBox vbox1 = new VBox(barChart);

            Scene scene1 = new Scene(vbox1, 400, 300);



            primaryStage.setScene(scene1);
            primaryStage.setHeight(300);
            primaryStage.setWidth(300);
            Stage stage=(Stage)button.getScene().getWindow();
            stage.close();


            primaryStage.show();

        });
        hbox.getChildren().addAll(datePicker1, new Label("——"),datePicker2,button);

        // 创建一个场景，并将VBox作为根节点
        Scene scene = new Scene(hbox, 400, 200);

        // 设置舞台（窗口）的标题和场景
        Stage mainStage = new Stage();
        mainStage.setTitle("入住率查询");
        mainStage.setScene(scene);
        mainStage.show();





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
            Stage stage = (Stage)statusChoiceBox.getScene().getWindow();
            stage.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void batchAdd(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("批量添加房间");
        dialog.setHeaderText("请输入路径");
        dialog.setContentText("路径:");

        // 用户点击确认后执行
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(Text -> {
            // 执行提交评论逻辑

            Text=Text.replace("\"","");

            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Text), Charset.forName("GBK")))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    String name=values[0];
                    String des=values[1];
                    int  occ= Integer.parseInt(values[4]);
                    double price= Double.parseDouble(values[3]);
                    int cnt= Integer.parseInt(values[2]);
                    BookingService.addRooms(name,des,Message.HotelID,occ,price,cnt);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                handleFilter(null);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // 订单数据模型
}