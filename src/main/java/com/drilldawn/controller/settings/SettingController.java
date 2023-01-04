/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.drilldawn.controller.settings;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;

public class SettingController implements Initializable{
	
	private static final Logger logger = LogManager.getLogger(SettingController.class);
	
	@FXML
    private Tab globalTab;
    @FXML
    private Tab connectorTab;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		try {
            globalTab.setContent((Node) FXMLLoader.load(this.getClass().getResource("/view/settings/Global.fxml")));
            connectorTab.setContent((Node) FXMLLoader.load(this.getClass().getResource("/view/settings/Connector.fxml")));

        } catch (IOException ex) {
            logger.error(ex);            
        }
	}


}
