package com.drilldawn.model;

import javafx.beans.property.SimpleStringProperty;

public class FilterResult {


    private SimpleStringProperty idRoot;
    private SimpleStringProperty securityType;
    private SimpleStringProperty symbol;
    private SimpleStringProperty isin;
    private SimpleStringProperty description;
    private SimpleStringProperty sector;
    private SimpleStringProperty industry;
    private SimpleStringProperty initial;
    private SimpleStringProperty maintenance;

    public FilterResult(SimpleStringProperty idRoot, SimpleStringProperty securityType, SimpleStringProperty symbol,
                        SimpleStringProperty isin, SimpleStringProperty description, SimpleStringProperty sector, SimpleStringProperty industry, SimpleStringProperty initial, SimpleStringProperty maintenance) {
        this.idRoot = idRoot;
        this.securityType = securityType;
        this.symbol = symbol;
        this.isin = isin;
        this.description = description;
        this.sector = sector;
        this.industry = industry;
        this.initial = initial;
        this.maintenance = maintenance;
    }

    public FilterResult(String idRoot, String securityType, String symbol,
                        String isin, String description, String sector, String industry, String initial, String maintenance) {
        setIdRoot(idRoot);
        setSecurityType(securityType);
        setSymbol(symbol);
        setIsin(isin);
        setDescription(description);
        setSector(sector);
        setIndustry(industry);
        setInitial(initial);
        setMaintenance(maintenance);
    }

    public String getIdRoot() {
        return idRoot.get();
    }

    public SimpleStringProperty idRootProperty() {
        return idRoot;
    }

    public void setIdRoot(String idRoot) {
        this.idRoot.set(idRoot);
    }

    public String getSecurityType() {
        return securityType.get();
    }

    public SimpleStringProperty securityTypeProperty() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType.set(securityType);
    }

    public String getSymbol() {
        return symbol.get();
    }

    public SimpleStringProperty symbolProperty() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol.set(symbol);
    }

    public String getIsin() {
        return isin.get();
    }

    public SimpleStringProperty isinProperty() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin.set(isin);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getSector() {
        return sector.get();
    }

    public SimpleStringProperty sectorProperty() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector.set(sector);
    }

    public String getIndustry() {
        return industry.get();
    }

    public SimpleStringProperty industryProperty() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry.set(industry);
    }

    public String getInitial() {
        return initial.get();
    }

    public SimpleStringProperty initialProperty() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial.set(initial);
    }

    public String getMaintenance() {
        return maintenance.get();
    }

    public SimpleStringProperty maintenanceProperty() {
        return maintenance;
    }

    public void setMaintenance(String maintenance) {
        this.maintenance.set(maintenance);
    }
}
