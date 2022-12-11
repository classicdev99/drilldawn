package com.drilldawn.dialog;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;

public class DialogManager {

    public static DialogManager instance = null;
    private String resString = "";
    public static DialogManager getInstance(){
        if(instance == null)
            instance = new DialogManager();
        return instance;
    }

    public void showAlert(String title, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
		alert.showAndWait();
	}

    public void showAlert(String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(content);
		alert.showAndWait();
	}

    public String showStringInputDialog(String defaultString, String content){
        TextInputDialog dialog = new TextInputDialog(defaultString);

        dialog.setTitle("Input");
        dialog.setHeaderText(null);
        dialog.setContentText(content);

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            this.resString = name;
        });
        return resString;
    }
}
