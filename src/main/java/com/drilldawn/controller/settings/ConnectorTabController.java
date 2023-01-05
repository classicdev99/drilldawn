/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.drilldawn.controller.settings;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.drilldawn.dialog.DialogManager;
import com.drilldawn.util.DBHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;

public class ConnectorTabController implements Initializable{
	
	private static final Logger logger = LogManager.getLogger(ConnectorTabController.class);
	DialogManager dialogManager = DialogManager.getInstance();
	DBHandler dbHandler = DBHandler.getInstance();
	@FXML
	private TextArea taRequest;

	@Override
	public void initialize(URL location, ResourceBundle resources) {		
	
	}

    @FXML
    private void onConnect(ActionEvent event) {
		HttpResponse<com.mashape.unirest.http.JsonNode> apiResponse;

		dbHandler.createAndConnectDynamicDB("databases//temp.db");
		try {
			apiResponse = Unirest.get(taRequest.getText()).asJson();
			// dialogManager.showAlert(apiResponse.getBody().toString().substring(0, 100));
			parseJsonResponse(apiResponse.getBody().toString());
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void parseJsonResponse(String body){
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode;
        try {
			rootNode = mapper.readTree(body);
			if(rootNode.isArray())
			{
				parseJsonArray(rootNode, "root");
				return;
			}
			parseRootJsonObject(rootNode);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // if(!rootNode.has("data") || !rootNode.has("status"))
        // {
        //     fileContent.setText("Invalid Json");
        //     return;
        // }
        // if(!rootNode.get("status").textValue().equals("ok"))
        // {
        //     dialogManager.showAlert(body);("Json Status is not good");
        //     return;
        // }
        // dataNode = rootNode.get("data");
        // if(!dataNode.isArray())
        // {
        //     fileContent.setText("Data is not sufficent");
        //     return;
        // }
        // if(dataNode.size() == 0)
        // {
        //     fileContent.setText("Data is not sufficent");
        //     return;
        // }
        // JsonNode oneNode = dataNode.get(0);
        // var fieldIt = oneNode.fieldNames();
        // fileContent.appendText("-Field Names:\n");
        // while(fieldIt.hasNext())
        // {
        //     var fieldName = fieldIt.next();
        //     fileContent.appendText(fieldName.toString());
        //     fileContent.appendText(" ");
        //     fieldList.add(fieldName.toString());
        // }
        // fileContent.appendText("\n-Data Array:\n");
        // for (JsonNode one : dataNode) {
        //     fieldIt = one.fieldNames();
        //     while(fieldIt.hasNext())
        //     {
        //             fileContent.appendText(one.get(fieldIt.next()).textValue());
        //             fileContent.appendText(" ");
        //     }
        //     fileContent.appendText("\n");
        // }
    }

	private void parseJsonArray(JsonNode array,String tableName)
	{
		ArrayList<String> fieldList = new ArrayList<>();
		JsonNode oneNode = array.get(0);
		// dialogManager.showAlert(oneNode.toString());
        var fieldIt = oneNode.fieldNames();
        while(fieldIt.hasNext())
        {
            fieldList.add(fieldIt.next().toString());
        }
		dbHandler.createNewTableInDynamicDB(tableName, fieldList);
		for (JsonNode one : array) {
            var it = one.fieldNames();
            ArrayList<String> valueList = new ArrayList<>();
            while(it.hasNext())
            {
               valueList.add(one.get(it.next()).textValue()) ;
            }

            dbHandler.insertTableDataInDynamicDB(tableName, fieldList, valueList);
        }
	}

	private void parseRootJsonObject(JsonNode rootNode)
	{
		ArrayList<String> tableNames = new ArrayList<>();
		var fieldIt = rootNode.fieldNames();
		while(fieldIt.hasNext())
        {
			var tableName = fieldIt.next().toString();
            tableNames.add(tableName);
			JsonNode node = rootNode.get(tableName);
			if(node.isArray())
			{
				parseJsonArray(node, tableName);
			}
        }
		for(String name : tableNames)
		{
		//	dbHandler.createNewTableInDynamicDB(name, tableNames);
		}
	}
}
