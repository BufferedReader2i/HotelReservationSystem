package com.hotelreservation.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
//import javafx.scene.chart.BarChart.Bar;


import java.util.ArrayList;
import java.util.List;

public class StatisticsViewController {

    @FXML
    private TableView<BookingStatistic> bookingStatisticsTable;
    @FXML
    private TableColumn<BookingStatistic, String> cityColumn;
    @FXML
    private TableColumn<BookingStatistic, String> districtColumn;
    @FXML
    private TableColumn<BookingStatistic, String> hotelColumn;
    @FXML
    private TableColumn<BookingStatistic, String> priceRangeColumn;
    @FXML
    private TableColumn<BookingStatistic, Integer> bookingsColumn;

    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis barChartCategoryAxis;
    @FXML
    private NumberAxis barChartNumberAxis;

    @FXML
    private PieChart pieChart;

    @FXML
    public void initialize() {
        // 初始化表格数据
        List<BookingStatistic> bookingData = getSampleBookingStatistics();
        bookingStatisticsTable.setItems((ObservableList<BookingStatistic>) bookingData);
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        districtColumn.setCellValueFactory(new PropertyValueFactory<>("district"));
        hotelColumn.setCellValueFactory(new PropertyValueFactory<>("hotel"));
        priceRangeColumn.setCellValueFactory(new PropertyValueFactory<>("priceRange"));
        bookingsColumn.setCellValueFactory(new PropertyValueFactory<>("bookings"));

        // 初始化柱状图数据
        setupBarChart(bookingData);

        // 初始化饼图数据
        setupPieChart();
    }

    private void setupBarChart(List<BookingStatistic> bookingData) {
        barChart.getData().clear(); // 清除现有数据

        // 创建并填充条形图数据
        for (BookingStatistic stat : bookingData) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(stat.getCity()); // 设置系列名称为城市名称
            series.getData().add(new XYChart.Data<>(stat.getCity(), stat.getBookings())); // 添加数据点
            barChart.getData().add(series); // 将系列添加到条形图中
        }
    }


    private void setupPieChart() {
        pieChart.getData().clear();
        List<Data> pieChartData = new ArrayList<>();
        // 示例数据，您应该根据实际数据填充
        pieChartData.add(new Data("Male", 60));
        pieChartData.add(new Data("Female", 40));
        pieChart.setData((ObservableList<Data>) pieChartData);
    }

    private List<BookingStatistic> getSampleBookingStatistics() {
        List<BookingStatistic> data = new ArrayList<>();
        data.add(new BookingStatistic("New York", "Manhattan", "Hotel 1", "$100-200", 150));
        data.add(new BookingStatistic("Los Angeles", "Downtown", "Hotel 2", "$150-300", 120));
        // 可以添加更多样例数据
        return data;
    }

    // 统计数据模型
    public static class BookingStatistic {
        private String city;
        private String district;
        private String hotel;
        private String priceRange;
        private int bookings;

        public BookingStatistic(String city, String district, String hotel, String priceRange, int bookings) {
            this.city = city;
            this.district = district;
            this.hotel = hotel;
            this.priceRange = priceRange;
            this.bookings = bookings;
        }

        // Getters
        public String getCity() {
            return city;
        }

        public String getDistrict() {
            return district;
        }

        public String getHotel() {
            return hotel;
        }

        public String getPriceRange() {
            return priceRange;
        }

        public int getBookings() {
            return bookings;
        }
    }
}