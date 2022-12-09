module drilldawn {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires javafx.media;
    requires java.sql;
    requires jsoup;
    requires org.apache.logging.log4j;
    requires java.prefs;
    requires javafx.graphics;
    requires eu.mihosoft.monacofx;
    requires java.base;
	requires javafx.base;

    opens com.drilldawn.controller to javafx.fxml;
    opens com.drilldawn.ib.client to javafx.fxml;
    opens com.drilldawn.ib.controller to javafx.fxml;
    opens com.drilldawn.ib.contracts to javafx.fxml;
    opens com.drilldawn.main to javafx.fxml;
    opens com.drilldawn.model to javafx.fxml;
    opens com.drilldawn.util.contracts to javafx.fxml;
    opens com.drilldawn.util to javafx.fxml;

    exports com.drilldawn.controller;
    exports com.drilldawn.ib.client;
    exports com.drilldawn.ib.controller;
    exports com.drilldawn.ib.contracts;
    exports com.drilldawn.main;
    exports com.drilldawn.model;
    exports com.drilldawn.util.contracts;
    exports com.drilldawn.util;
}
