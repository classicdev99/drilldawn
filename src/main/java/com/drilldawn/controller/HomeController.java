/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drilldawn.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.drilldawn.util.DBHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
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
    
    private DBHandler dbHandler;
    private static final Logger logger = LogManager.getLogger(HomeController.class);
//    @FXML
//    private Button settingsButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbHandler = DBHandler.getInstance();
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
    
    @FXML
    private void onImportJsonClicked(ActionEvent event) {
    	try {
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ImportDatabaseScreen.fxml"));
          Parent root1 = fxmlLoader.load();
          Stage stage = new Stage();
          stage.initModality(Modality.APPLICATION_MODAL);
          stage.setTitle("Import Database");
          stage.setScene(new Scene(root1));
          stage.show();
      } catch (Exception e) {
          logger.error(e.getMessage());          
      }
    }

    @FXML
    private void onImportDBClicked(ActionEvent event) throws SQLException {
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Sqlite DB File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sqlite DB Files", "*.db"));
        fileChooser.setInitialDirectory(new File("."));

        File file = fileChooser.showOpenDialog(null);
        if(file != null)
        {
            String dbFileName;
            dbFileName = file.getPath();
            dbFileName = dbFileName.replace("\\", "\\\\");
            dbHandler.connectCustomDb(dbFileName);
            MainViewController mViewController = MainViewController.getInstance();
            if(dbHandler.checkCustomConnectivity())
            {
                mViewController.addTableNames();
            }
            // mViewController.setTableColumns();
            // mViewController.setTableItems();
        }
    }
}
