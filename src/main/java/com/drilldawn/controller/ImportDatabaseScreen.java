package com.drilldawn.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.drilldawn.dialog.DialogManager;
import com.drilldawn.util.DBHandler;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImportDatabaseScreen implements Initializable {
    
    @FXML
    private TextArea fileContent;
    
    private static final Logger logger = LogManager.getLogger(HomeController.class);
    private Stage rootStage;
    private ArrayList<String> fieldList;
    private String jsonFileName = "";
    private DBHandler dbHandler;
    private JsonNode dataNode;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fieldList = new ArrayList<>();
        dbHandler = DBHandler.getInstance();
        dbHandler.createDatabaseInfoTable();
    }

    @FXML
    private void onOpenButtonClicked(ActionEvent event) throws StreamReadException, DatabindException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Json File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.json"));
        fileChooser.setInitialDirectory(new File("."));

        File file = fileChooser.showOpenDialog(rootStage);
        if(file != null)
        {
            jsonFileName = file.getName();
            getJsonObjectFromFile(file);
        }
    }

    @FXML
    private void onSaveAsTableClicked(ActionEvent event){
        DialogManager dialogManager = DialogManager.getInstance();
        if(fieldList.size() < 1)
        {
            dialogManager.showAlert("Error", "Open valid database file first!");
            return;
        }
        var dbName = dialogManager.showStringInputDialog(jsonFileName,"Input Database Name:");
        if(dbName.equalsIgnoreCase(""))
            return;
        if(dbHandler.checkTableExists(dbName))
        {
            dialogManager.showAlert("Error", "Table already exists!");
            return;
        }
        var cols = String.join(", ", fieldList);
        var id = dbHandler.insertDatabaseInfo(dbName, cols);
        dbHandler.createNewTable(dbName, fieldList);
        if(id != -1)
        {
            dialogManager.showAlert( "Succesfully added to database table");
        }
        else {
            dialogManager.showAlert( "Failed to save");
        }
        processInputTableData(dbName);
    }
    
    private void getJsonObjectFromFile(File file) throws StreamReadException, DatabindException, IOException{

        ObjectMapper mapper = new ObjectMapper();

        fileContent.setText("");

        JsonNode rootNode = mapper.readValue(file, JsonNode.class);
        if(!rootNode.has("data") || !rootNode.has("status"))
        {
            fileContent.setText("Invalid Json");
            return;
        }
        if(!rootNode.get("status").textValue().equals("ok"))
        {
            fileContent.setText("Json Status is not good");
            return;
        }
        dataNode = rootNode.get("data");
        if(!dataNode.isArray())
        {
            fileContent.setText("Data is not sufficent");
            return;
        }
        if(dataNode.size() == 0)
        {
            fileContent.setText("Data is not sufficent");
            return;
        }
        JsonNode oneNode = dataNode.get(0);
        var fieldIt = oneNode.fieldNames();
        fileContent.appendText("-Field Names:\n");
        while(fieldIt.hasNext())
        {
            var fieldName = fieldIt.next();
            fileContent.appendText(fieldName.toString());
            fileContent.appendText(" ");
            fieldList.add(fieldName.toString());
    }
        fileContent.appendText("\n-Data Array:\n");
        for (JsonNode one : dataNode) {
            fieldIt = one.fieldNames();
            while(fieldIt.hasNext())
            {
                    fileContent.appendText(one.get(fieldIt.next()).textValue());
                    fileContent.appendText(" ");
            }
            fileContent.appendText("\n");
        }
    }

    private void processInputTableData(String dbName){
        if(!dataNode.isArray() || dataNode.size() == 0)
            return;
        for (JsonNode one : dataNode) {
            var fieldIt = one.fieldNames();
            ArrayList<String> valueList = new ArrayList<>();
            while(fieldIt.hasNext())
            {
               valueList.add(one.get(fieldIt.next()).textValue()) ;
            }
            dbHandler.insertTableData(dbName, fieldList, valueList);
        }
    }
}