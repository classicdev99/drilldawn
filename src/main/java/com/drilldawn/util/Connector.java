package com.drilldawn.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

    private static Connection con = null;
    private static final Logger logger = LogManager.getLogger(Connector.class);

    static {
        String url = "jdbc:sqlite:drilldawn.db";
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(url);
            logger.info("Connection to SQLite has been established.");
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public static Connection getConnection() {
        return con;
    }

}
