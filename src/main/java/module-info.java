module co.edu.uniquindio.poo.parcial2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    opens co.edu.uniquindio.poo.parcial2.controller to javafx.fxml;
    opens co.edu.uniquindio.poo.parcial2.model to org.junit.platform.commons;

    exports co.edu.uniquindio.poo.parcial2.app;
    exports co.edu.uniquindio.poo.parcial2.model;
}