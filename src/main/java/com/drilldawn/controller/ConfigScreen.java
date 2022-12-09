package com.drilldawn.controller;

import com.drilldawn.ib.client.EClientSocket;
import com.drilldawn.model.APIConfig;
import com.drilldawn.util.APIConnector;
import com.drilldawn.util.DBHandler;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ConfigScreen implements Initializable {

    private DBHandler dbHandler;
    private static final Logger logger = LogManager.getLogger(ConfigScreen.class);

    @FXML
    private TextField accountNumInp, hostInp, clientIdInp, portInp;

    @FXML
    private Label errorMsg;

    private APIConfig configPort;
    private APIConnector apiConnector;
    private EClientSocket client;
    private APIConnectionService apiConnectionService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apiConnector = new APIConnector();
        dbHandler = new DBHandler();
        dbHandler.createTables();
        getPreviousConfigs();
    }

    /**
     * Setup previous configs
     */
    private void getPreviousConfigs() {
        configPort = dbHandler.getAPIConfig();
        if (configPort != null) {
            accountNumInp.setText(configPort.getAccountNum());
            hostInp.setText(configPort.getHost());
            clientIdInp.setText(String.valueOf(configPort.getClientId()));
            portInp.setText(String.valueOf(configPort.getPort()));
        }
    }

    /**
     * On connect button click
     *
     * @param mouseEvent
     */
    @FXML
    public void onConnectToAPI(MouseEvent mouseEvent) {
        boolean isValidConfig = validateConfigInputs();
        if (isValidConfig) {
            if (apiConnectionService == null) {
                apiConnectionService = new APIConnectionService();
            }            
//            try {
//                if (!apiConnectionService.getState().equals("FAILED")) {
                    apiConnectionService.start();
//                }
//                
//            } catch (Exception e) {
//                logger.error(e.getMessage());
//            }
            

            openMainView();

        }


    }

    private void openMainView(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Drill Down");
            stage.setScene(new Scene(root1));
            stage.onCloseRequestProperty().setValue(e -> Platform.exit());
            stage.show();
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Validate config inputs
     *
     * @return
     */
    private boolean validateConfigInputs() {
        String accountNumber = accountNumInp.getText();
        String port = portInp.getText();
        String clientId = clientIdInp.getText();
        String host = hostInp.getText();

        if (accountNumber.isEmpty()) {
            errorMsg.setText("Account Number is Required");
            return false;
        }
        if (host.isEmpty()) {
            errorMsg.setText("Host is Required");
            return false;
        }

        if (clientId.isEmpty()) {
            errorMsg.setText("Client Id is Required");
            return false;
        } else if (!Pattern.compile("^\\d*$").matcher(port).matches()) {
            errorMsg.setText("Client Id should be a number");
            return false;
        }


        if (port.isEmpty()) {
            errorMsg.setText("Port is Required");
            return false;
        } else if (!Pattern.compile("^\\d*$").matcher(port).matches()) {
            errorMsg.setText("Port should be a number");
            return false;
        }


        int pt = 0;
        int cl = 0;

        try {
            pt = Integer.parseInt(port);
            cl = Integer.parseInt(clientId);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }

        int result = dbHandler.insertAPIConfigs(accountNumber, pt, cl, host);

        if (result == 1) {
            configPort = new APIConfig(accountNumber, pt, cl, host);
            return true;
        }

        return false;
    }


    /**
     * Establish IB API connection
     */
    private class APIConnectionService extends Service<String> {

        private APIConnectionService() {
            setOnSucceeded(event -> {
                if (client.isConnected()) {
                    logger.info("Connection successful ");
                }
            });

            setOnFailed(event -> logger.error("Connection Failed "));
        }

        @Override
        protected Task<String> createTask() {
            return new Task<>() {
                @Override
                protected String call() throws Exception {
                    try {
                        if (configPort == null) throw new Exception();
                        client = apiConnector.createConnection(configPort.getHost(), configPort.getPort(), configPort.getClientId());
                        if (!client.isConnected()) throw new Exception();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "Successfully";
                }
            };
        }
    }
}
