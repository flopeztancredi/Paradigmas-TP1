module org.example {
    requires javafx.controls;
    requires javafx.fxml;


    exports org.robots.controlador;
    exports org.robots;

    opens org.robots.controlador to javafx.fxml;
}
