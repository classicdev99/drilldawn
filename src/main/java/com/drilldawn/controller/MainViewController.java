package com.drilldawn.controller;

import com.drilldawn.dialog.DialogManager;
import com.drilldawn.model.FilterResult;
import com.drilldawn.util.DBHandler;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.util.Callback;

public class MainViewController implements Initializable  {

    @FXML
    private TextArea queryText;
    @FXML
    public TableView<ObservableList> filterResultTable;
    @FXML
    private ComboBox tablesCombo;

        
    private static MainViewController INSTANCE = null;
    private DBHandler dbHandler;
    private DialogManager dialogManager = null;

    // @FXML
    // public TableColumn<FilterResult, String> idRootCol, securityTypeCol, symbolCol, isinCol, descriptionCol, sectorCol, industryCol, initialCol, maintenanceCol;
    

    public ObservableList<FilterResult> filterResults;

    public static MainViewController getInstance() {
        return INSTANCE;
      }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        ArrayList<FilterResult> results = new ArrayList<>();
//        FilterResult filterResult = new FilterResult("id", "type", "symbol", "isin", "desc" ,"sec", "indus" ,"", "" );
//        results.add(filterResult);
//        results.add(filterResult);
//        results.add(filterResult);
//        filterResults = FXCollections.observableArrayList(results);
//        filterResultTable.setItems(filterResults);
        MainViewController.INSTANCE = this;
        dbHandler = DBHandler.getInstance();
        dialogManager = DialogManager.getInstance();
        //tablesCombo.getItems().addAll("A","B","C");comboBox.setOnAction((event) -> {
            tablesCombo.setOnAction((event) -> {
                int selectedIndex = tablesCombo.getSelectionModel().getSelectedIndex();
                Object selectedItem = tablesCombo.getSelectionModel().getSelectedItem();
                updateQueryText(selectedItem.toString());
            });
    }

    @FXML
    private void onSubmitButtonClick(ActionEvent event) {
        filterResultTable.getColumns().clear();
        filterResultTable.getItems().clear();
        filterResultTable.refresh();

        if(!dbHandler.checkCustomConnectivity())
        {
            dialogManager.showAlert("DB Error", "Database not connected");
        }
        setTableColumns();
    }

    public void addTableNames(){
        tablesCombo.getItems().clear();
        tablesCombo.getItems().addAll(dbHandler.getTableNames());
        tablesCombo.getSelectionModel().select(0);
    }

    public void setTableColumns() {
        ResultSetMetaData rs = dbHandler.getAllColumnFromQuery(queryText.getText());
        if(rs == null)
        {
            dialogManager.showAlert("Query Error", "Something went wrong with query");
            return;
        }
        try {
            for (int i = 0; i < rs.getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getColumnName(i + 1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        var item = param.getValue().get(j);
                        if(item == null) return new SimpleStringProperty("");
                        return new SimpleStringProperty(item.toString());
                    }
                });
                filterResultTable.getColumns().addAll(col);
                //System.out.println("Column [" + i + "] ");
            }
            setTableItems();
        } catch (SQLException e) {
            dialogManager.showAlert("Query Error", "Something went wrong with query");
        }
    }

    public void setTableItems() {
        filterResultTable.setItems(dbHandler.getDataFromQuery(queryText.getText()));
    }

    public void updateQueryText(String tableName){
        String query = "SELECT * FROM " + tableName;
        queryText.setText(query);
    }
}
