/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.drilldawn.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class SettingController implements Initializable{

	@FXML
	CheckBox enableActiveXAndSocketClientsCB;
	
	@FXML
	CheckBox readOnlyApiCB;
	
	@FXML
	TextField socketPortTF;
	
	@FXML
	ComboBox<String> loggingLevelComboBox;
	
	private static final Logger logger = LogManager.getLogger(SettingController.class);
	
	File propertiesFile;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		
		loggingLevelComboBox.setItems(FXCollections.observableArrayList("ERROR", "INFO"));
		loggingLevelComboBox.setValue("ERROR");
		
		//This properties file is generated in the project root folder
		propertiesFile = new File("settings.properties");
		
		//Check if the properties file exist or not. If not, create it
		if (!propertiesFile.exists()) {
			try {
				propertiesFile.createNewFile();
			} catch (IOException e) {
				logger.error("Unable to create properties file.\n" + e);
			}
			
			//code to load the properties into the file
			//Use properties class
			//use constants defined in the com.drilldawn.model.SettingConstants class 
		}
	}

	@FXML
	private void onCloseButtonClicked(ActionEvent event) {				
	    close(event);
	}
	
	@FXML
	private void onApplyButtonClicked(ActionEvent event) {	
		apply(event);
	}
	
	@FXML
	private void onApplyAndCloseButtonClicked(ActionEvent event) {	
		apply(event);
		close(event);
	}
	
	private void apply(ActionEvent event) {
		//Code to save the values to the property file goes here
		//Read the current values and save it in the properties file
		//using Properties class
	}
	
	private void close(ActionEvent event) {
		Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();	    
	    stage.close();
	}
}
