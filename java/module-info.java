module com.hotelreservation {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.sql;
    requires jbcrypt;


    opens com.hotelreservation.view to javafx.fxml, javafx.base;
    opens com.hotelreservation.entity to javafx.base,javafx.fxml;
    exports com.hotelreservation.view;
    exports com.hotelreservation;
    exports com.hotelreservation.dbutil;
}