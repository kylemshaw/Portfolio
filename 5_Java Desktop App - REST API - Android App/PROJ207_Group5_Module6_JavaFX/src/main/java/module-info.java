module com.te.dbmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens com.te.dbmanager to javafx.fxml;
    exports com.te.dbmanager;
    opens com.te.dbmanager.gui to javafx.fxml;
    exports com.te.dbmanager.gui;
    opens com.te.dbmanager.model to javafx.fxml;
    exports com.te.dbmanager.model;
    opens com.te.dbmanager.data to javafx.fxml;
    exports com.te.dbmanager.data;

}