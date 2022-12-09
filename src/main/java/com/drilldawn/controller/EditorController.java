/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drilldawn.controller;

import eu.mihosoft.monacofx.MonacoFX;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.scene.Node;

public class EditorController implements Initializable {

	MonacoFX monacoFX;
	
    @FXML
    private GridPane rootGridPane;
//    private WebView webView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        WebEngine webEngine = webView.getEngine();
//        webEngine.load(getClass().getResource("/ace/editor.html").toExternalForm());
//        webEngine.getDocument().getElementById("editor").get;

        // create a new monaco editor node
        monacoFX = new MonacoFX();        

        // set initial text
        monacoFX.getEditor().getDocument().setText(
                "public void formula()\n"
                + "{\n"
                + "   System.out.println(\"Hello World\")\n"                
                + "   //comment;\n"
                + "}");
                

        // use a predefined language like 'c'
        monacoFX.getEditor().setCurrentLanguage("java");
        monacoFX.getEditor().setCurrentTheme("vs-dark");
        
        rootGridPane.getChildren().add(monacoFX);
        GridPane.setColumnSpan(monacoFX, GridPane.REMAINING);
        GridPane.setRowSpan(monacoFX, GridPane.REMAINING);
    }

    @FXML
    private void onOpenButtonClicked(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Template File");
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text", "*.txt"),
                new FileChooser.ExtensionFilter("JAVA", "*.java")
        );
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        
        
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();                
            }
            String everything = sb.toString();
            monacoFX.getEditor().getDocument().setText(everything);
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    
    @FXML
    private void onSaveButtonClicked(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Excel File Output Destination");
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Java", "*.java")
        );
        File outputFile = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());
        if (outputFile != null) {
        	String text = monacoFX.getEditor().getDocument().getText();        	
        	try (PrintWriter out = new PrintWriter(outputFile)) {
        	    out.println(text);
        	} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    	
    	
    }
}
