package com.hotelreservation.view;



import com.hotelreservation.dao.SearchService;
import com.hotelreservation.entity.Statics;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PieCharts1Controller {
    @FXML
    private PieChart pieChart1, pieChart2, pieChart3;

    @FXML
    public void initialize() {
        populatePieChart(pieChart1, "城市");
        populatePieChart(pieChart2, "区域");
        populatePieChart(pieChart3, "酒店");
    }

    private void populatePieChart(PieChart chart, String chartTitle) {
        String proName = null;
        List<Statics> list =new ArrayList<>();
        LocalDate begin=LocalDate.of(2024,1,1);
        LocalDate end=LocalDate.of(2024,12,31);

        if (chartTitle.equals("城市")){
            proName="CountCitiesByOrder";

        }
        if (chartTitle.equals("酒店")){
            proName="CountHotelsByOrder";

        }
        if (chartTitle.equals("区域")){
            proName="CountDistrictsByOrder";

        }
        list= SearchService.getData(proName,begin,end);
        for (Statics data:list){
            chart.getData().add(new Data(data.getName()+" "+data.getValue(),data.getValue()));
        }
        chart.setTitle(chartTitle);
        chart.setLabelsVisible(true);
    }
}