package co.edu.uniquindio.poo.parcial2.app;

import co.edu.uniquindio.poo.parcial2.controller.AppContext;
import co.edu.uniquindio.poo.parcial2.controller.Ventanas;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * Metodo que inicia la aplicacion: carga los datos y abre la ventana de inicio
     * @param stage ventana principal
     */
    @Override
    public void start(Stage stage) {
        AppContext.getInstance();
        Ventanas.iniciar(stage);
        Ventanas.mostrar("Inicio");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
