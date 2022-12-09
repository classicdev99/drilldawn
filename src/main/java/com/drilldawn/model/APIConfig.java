package com.drilldawn.model;

public class APIConfig {

    private String accountNum;
    private int port;
    private int clientId;
    private String host;

    public APIConfig(String accountNum, int port, int clientId, String host) {
        this.accountNum = accountNum;
        this.port = port;
        this.clientId = clientId;
        this.host = host;
    }

    public APIConfig() {
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
