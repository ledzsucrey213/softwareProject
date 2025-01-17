module com.autocare.autocare {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires junit;
    requires org.testng;

    opens com.autocare to javafx.fxml;

    exports com.autocare.autocare;
    exports com.autocare.login.test;
    opens com.autocare.subscription to javafx.base;
    opens com.autocare.subscription.service to javafx.base;
    opens com.autocare.user to javafx.base;

    opens com.autocare.transaction to javafx.base;
}