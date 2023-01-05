package com.drilldawn.util;

import com.drilldawn.model.APIConfig;
import com.drilldawn.model.Album;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Console;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class DBHandler {

    private static DBHandler instance = null;
    private  Connection con;
    private  Connection customCon;
    private Connection dynamicCon;;
    private  final Logger logger = LogManager.getLogger(DBHandler.class);


    public static DBHandler getInstance(){
        if(instance == null)
            instance = new DBHandler();
        return instance;
    }

    public DBHandler() {
        con = Connector.getConnection();
    }


    public  void connectCustomDb(String path){
        customCon = CustomConnector.getConnection(path);
    }
    
    public void createAndConnectDynamicDB(String path){
        dynamicCon = CustomConnector.getConnection(path);
    }

    public  void createTables() {
        String api = "CREATE TABLE IF NOT EXISTS api_config (\n"
                + "	config_id INTEGER PRIMARY KEY NOT NULL,\n"
                + "	account_num Text NOT NULL,\n"
                + "	port INTEGER NOT NULL ,\n"
                + "	client_id INTEGER NOT NULL ,\n"
                + "	host Text NOT NULL \n"
                + " );";
   

        try (Statement stmt = con.createStatement()) {
            stmt.execute(api);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public  void createDatabaseInfoTable(){
        String databaseInfo = "CREATE TABLE IF NOT EXISTS database_info (\n"
                + "	id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,\n"
                + "	database_name Text NOT NULL,\n"
                + "	columns TEXT NOT NULL \n"
                + " );";    
        try (Statement stmt = con.createStatement()) {
            stmt.execute(databaseInfo);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } 
    }

    public void createNewTable(String tableName, ArrayList<String> cols){
        String databaseInfo = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
            + "	id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ";
        for(var col : cols){
            databaseInfo += " ,\n";
            databaseInfo += " " + col + " TEXT NOT NULL";
        }
        databaseInfo += "\n );";
        try (Statement stmt = con.createStatement()) {
            stmt.execute(databaseInfo);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } 
    }
    public  boolean checkTableExists(String tableName){
        try {
            DatabaseMetaData md = con.getMetaData();
            ResultSet rs = md.getTables(null, null, tableName, null);
            return rs.next();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    public  int insertAPIConfigs(String acc, int port, int clientId, String host) {
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
    
    public  APIConfig getAPIConfig() {
        String sql = "Select * from api_config";
        APIConfig APIConfig = null;

        try (Statement stmt = con.createStatement()) {

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

    public  Album getSomeData() {
        String sql = "Select * from albums";
        Album album = null;

        try (Statement stmt = customCon.createStatement()) {

            ResultSet result = stmt.executeQuery(sql);

            if (result.next()) {
                int albumId = result.getInt("AlbumId");
                String title = result.getString("title");
                int ArtistId = result.getInt("ArtistId");
                album = new Album(albumId, title, ArtistId);
            }
        } catch (SQLException throwable) {
            logger.error(throwable.getMessage());
        }
        return album;

    }

    public ObservableList<String> getTableNames(){
        DatabaseMetaData metaData;
        ObservableList<String> tableNames = FXCollections.observableArrayList();
        try {
            metaData = customCon.getMetaData();
            String[] types = {"TABLE"};
            //Retrieving the columns in the database
            ResultSet tables = metaData.getTables(null, null, "%", types);
            while (tables.next()) {
                tableNames.add((tables.getString("TABLE_NAME")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tableNames;
    }

    public ResultSetMetaData getAllColumnFromQuery(String query){
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        //String SQL = "SELECT * from albums";
        String SQL = query;

        ResultSet rs;
        try {
            rs = customCon.createStatement().executeQuery(SQL);
            return rs.getMetaData();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public  ObservableList<ObservableList> getDataFromQuery(String query){
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        //String SQL = "SELECT * from albums";
        String SQL = query;
        
        ResultSet rs;
        try {
            rs = customCon.createStatement().executeQuery(SQL);

            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                //System.out.println("Row [1] added " + row);
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
      
        return data;
    }

    public  int insertDatabaseInfo(String name, String cols) {
        String sql = " INSERT OR REPLACE INTO database_info (database_name, columns)\n" +
                " VALUES(?,?)";

        int i = -1;
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, cols);
            i = stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
        }

        return i;
    }

    public  int insertTableData(String tableName, ArrayList<String> fieldList, ArrayList<String> valueList) {
        if(fieldList.size() != valueList.size())
            return -1;
        String sql = " INSERT OR REPLACE INTO " + tableName + " (";

        sql += fieldList.get(0);
        int i = 1;
        for(; i < fieldList.size(); i++){
            sql += ", ";
            var field = fieldList.get(i);
            sql += field;
        }
        sql += ")\n VALUES(?";
        for(i = 1; i < fieldList.size(); i++){
            sql += ",?";
        }
        sql += ")";
        int id = -1;
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            for(i = 0 ; i < fieldList.size(); i++){
                stmt.setString(i + 1, valueList.get(i));
            }
            id = stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
        }

        return id;
    }

    public  boolean checkCustomConnectivity(){
        return customCon != null;
    }

    public void createNewTableInDynamicDB(String tableName, ArrayList<String> cols){
        String databaseInfo = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
            + "	id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ";
        for(var col : cols){
            databaseInfo += " ,\n";
            databaseInfo += " " + col + " TEXT NOT NULL";
        }
        databaseInfo += "\n );";
        try (Statement stmt = dynamicCon.createStatement()) {
            stmt.execute(databaseInfo);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } 
    }

    public  int insertTableDataInDynamicDB(String tableName, ArrayList<String> fieldList, ArrayList<String> valueList) {
        if(fieldList.size() != valueList.size())
            return -1;
        String sql = " INSERT OR REPLACE INTO " + tableName + " (";

        sql += fieldList.get(0);
        int i = 1;
        for(; i < fieldList.size(); i++){
            sql += ", ";
            var field = fieldList.get(i);
            sql += field;
        }
        sql += ")\n VALUES(?";
        for(i = 1; i < fieldList.size(); i++){
            sql += ",?";
        }
        sql += ")";
        int id = -1;
        try (PreparedStatement stmt = dynamicCon.prepareStatement(sql)) {
            for(i = 0 ; i < fieldList.size(); i++){
                stmt.setString(i + 1, valueList.get(i));
            }
            id = stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
        }

        return id;
    }
}
