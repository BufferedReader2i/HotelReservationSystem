package com.hotelreservation.view;



import com.hotelreservation.dao.SearchService;
import com.hotelreservation.entity.Statics;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PieCharts2Controller {
    @FXML
    private PieChart pieChart1, pieChart2, pieChart3,pieChart4;

    @FXML
    public void initialize() {
        populatePieChart(pieChart1, "性别");
        populatePieChart(pieChart2, "职业");
        populatePieChart(pieChart3, "受教育程度");
        populatePieChart(pieChart4, "收入状况");
    }

    private void populatePieChart(PieChart chart, String chartTitle) {
        String proName = null;
        List<Statics> list =new ArrayList<>();
        LocalDate begin=LocalDate.of(2024,1,1);
        LocalDate end=LocalDate.of(2024,12,31);

        if (chartTitle.equals("性别")){
            proName="CountGuestsByGender";

        }
        if (chartTitle.equals("职业")){
            proName="CountGuestsByOccupation";

        }
        if (chartTitle.equals("受教育程度")){
            proName="CountGuestsByEducationLevel";

        }
        if (chartTitle.equals("收入状况")){
            proName="CountGuestsByIncomeStatus";

        }
        list= SearchService.getData2(proName);
        System.out.println(list.size());
        for (Statics data:list){
            chart.getData().add(new Data(data.getName()+" "+data.getValue(),data.getValue()));
        }
        chart.setTitle(chartTitle);
        chart.setLabelsVisible(true);
    }
}