module co.edu.uniquindio.poo.parcial2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens co.edu.uniquindio.poo.parcial2 to javafx.fxml;
    exports co.edu.uniquindio.poo.parcial2;
    exports co.edu.uniquindio.poo.parcial2.model;
    exports co.edu.uniquindio.poo.parcial2.services;
}