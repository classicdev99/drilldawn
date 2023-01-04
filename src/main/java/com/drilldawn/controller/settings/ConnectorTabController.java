/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.drilldawn.controller.settings;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.drilldawn.dialog.DialogManager;
import com.drilldawn.util.DBHandler;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
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
		HttpResponse<JsonNode> apiResponse;
		try {
			apiResponse = Unirest.get(taRequest.getText()).asJson();
			dialogManager.showAlert(apiResponse.getBody().toString());
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
