package com.drilldawn.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Drilldawn");
        Scene scene = new Scene(root, 800, 550);
        primaryStage.setScene(scene);
        primaryStage.setOnHidden(e -> {
            Platform.exit();
            System.exit(1);
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
