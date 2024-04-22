module org.example {
    requires javafx.controls;
    requires javafx.fxml;


    exports org.robots.controlador;
    exports org.robots;

    opens org.robots.vista to javafx.fxml;
}
