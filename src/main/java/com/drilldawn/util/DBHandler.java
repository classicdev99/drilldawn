package com.drilldawn.util;

import com.drilldawn.model.APIConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DBHandler {

    private Connection con;
    private static final Logger logger = LogManager.getLogger(DBHandler.class);


    public DBHandler() {
        this.con = Connector.getConnection();
    }

    public void createTables() {
        String api = "CREATE TABLE IF NOT EXISTS api_config (\n"
                + "	config_id INTEGER PRIMARY KEY NOT NULL,\n"
                + "	account_num Text NOT NULL,\n"
                + "	port INTEGER NOT NULL ,\n"
                + "	client_id INTEGER NOT NULL ,\n"
                + "	host Text NOT NULL \n"
                + " );";

        try (Statement stmt = this.con.createStatement()) {
            stmt.execute(api);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public int insertAPIConfigs(String acc, int port, int clientId, String host) {
        String sql = " INSERT OR REPLACE INTO api_config (config_id, account_num, port, client_id, host)\n" +
                " VALUES(?,?,?,?,?)";

        int i = 0;

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, 1);
            stmt.setString(2, acc);
            stmt.setInt(3, port);
            stmt.setInt(4, clientId);
            stmt.setString(5, host);
            i = stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
        }

        return i;
    }

    public APIConfig getAPIConfig() {
        String sql = "Select * from api_config";
        APIConfig APIConfig = null;

        try (Statement stmt = this.con.createStatement()) {

            ResultSet result = stmt.executeQuery(sql);

            if (result.next()) {
                String accountNum = result.getString("account_num");
                int portNum = result.getInt("port");
                int clientId = result.getInt("client_id");
                String host = result.getString("host");
                APIConfig = new APIConfig(accountNum, portNum, clientId, host);
            }
        } catch (SQLException throwable) {
            logger.error(throwable.getMessage());
        }
        return APIConfig;

    }
}
