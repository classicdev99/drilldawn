/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drilldawn.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Work
 */
public class HomeController implements Initializable{

    @FXML
    private TabPane mainTabPane;
    @FXML
    private Tab queryTab;
    @FXML
    private Tab chartsTab;
    @FXML
    private Tab editorTab;
    
    private static final Logger logger = LogManager.getLogger(HomeController.class);
//    @FXML
//    private Button settingsButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            queryTab.setContent((Node) FXMLLoader.load(this.getClass().getResource("/view/MainView.fxml")));
            chartsTab.setContent((Node) FXMLLoader.load(this.getClass().getResource("/view/Charts.fxml")));
            editorTab.setContent((Node) FXMLLoader.load(this.getClass().getResource("/view/Editor.fxml")));
            
            
        } catch (IOException ex) {
            logger.error(ex);            
        }
    }
    
    @FXML
    private void onSettingsButtonClicked(ActionEvent event) {
    	try {
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ConfigScreen.fxml"));
          Parent root1 = fxmlLoader.load();
          Stage stage = new Stage();
          stage.initModality(Modality.APPLICATION_MODAL);
          stage.setTitle("Settings");
          stage.setScene(new Scene(root1));
          stage.show();
      } catch (Exception e) {
          logger.error(e.getMessage());          
      }
    }
    
//    @FXML
//    private void onSettingsButtonClicked(ActionEvent event) {
//         try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Settings.fxml"));
//            Parent root1 = fxmlLoader.load();
//            Stage stage = new Stage();
//            stage.initModality(Modality.NONE);
//            stage.setTitle("Settings");
//            stage.setScene(new Scene(root1));
//            stage.show();
//        } catch (Exception e) {
//            //logger.error(e.getMessage());
//        }
//    }
}
