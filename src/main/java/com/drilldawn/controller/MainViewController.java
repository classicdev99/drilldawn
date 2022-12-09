package com.drilldawn.controller;

import com.drilldawn.model.FilterResult;
import com.drilldawn.util.CandleChart;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainViewController implements Initializable {

    @FXML
    public TableView<FilterResult> filterResultTable;

    @FXML
    public TableColumn<FilterResult, String> idRootCol, securityTypeCol, symbolCol, isinCol, descriptionCol, sectorCol, industryCol, initialCol, maintenanceCol;

    public ObservableList<FilterResult> filterResults;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        ArrayList<FilterResult> results = new ArrayList<>();
//        FilterResult filterResult = new FilterResult("id", "type", "symbol", "isin", "desc" ,"sec", "indus" ,"", "" );
//        results.add(filterResult);
//        results.add(filterResult);
//        results.add(filterResult);
//        filterResults = FXCollections.observableArrayList(results);
//        filterResultTable.setItems(filterResults);

    }

    @FXML
    private void onSubmitButtonClick(ActionEvent event) {
       
    }
}
