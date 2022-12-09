/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drilldawn.controller;

import com.drilldawn.util.CandleChart;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Work
 */
public class ChartController implements Initializable{

    @FXML
    private GridPane rootGridPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CandleChart ad = new CandleChart();
        ad.getStylesheets().add(getClass().getResource("/styles/AdvCandleStickChart.css").toString());
        rootGridPane.getChildren().add(ad);
        GridPane.setColumnSpan(ad, GridPane.REMAINING);
        GridPane.setRowSpan(ad, GridPane.REMAINING);
    }
        
}
