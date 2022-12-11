package com.drilldawn.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CustomConnector {

    private static Connection con = null;
    private static final Logger logger = LogManager.getLogger(Connector.class);

    public static Connection getConnection(String path) {
        String url = "jdbc:sqlite:" + path;
		Alert alert = new Alert(AlertType.INFORMATION);
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(url);
            logger.info("Connection to Custom SQLite has been established.");
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(e.getMessage());
        }
        return con;
    }

}
