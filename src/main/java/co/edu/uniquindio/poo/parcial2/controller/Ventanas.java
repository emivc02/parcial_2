package co.edu.uniquindio.poo.parcial2.controller;

import co.edu.uniquindio.poo.parcial2.model.ElementoServicio;
import co.edu.uniquindio.poo.parcial2.model.PaqueteTecnologico;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Ventanas {

    private static final String RUTA_VISTAS = "/co/edu/uniquindio/poo/parcial2/view/";
    private static Stage stage;

    /**
     * Metodo que guarda la ventana principal de la aplicacion
     * @param ventana
     */
    public static void iniciar(Stage ventana) {
        stage = ventana;
        stage.setTitle("TechSolutions");
        stage.setMinWidth(1000);
        stage.setMinHeight(680);
    }

    /**
     * Metodo que cambia la vista que se muestra en la ventana principal
     * @param vista nombre del archivo fxml sin la extension
     */
    public static void mostrar(String vista) {
        try {
            FXMLLoader loader = new FXMLLoader(Ventanas.class.getResource(RUTA_VISTAS + vista + ".fxml"));
            Parent raiz = loader.load();
            if (stage.getScene() == null) {
                stage.setScene(new Scene(raiz, 1200, 740));
            } else {
                stage.getScene().setRoot(raiz);
            }
            stage.show();
        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la vista " + vista + ": " + e.getMessage());
        }
    }

    /**
     * Metodo que muestra una ventana de mensaje
     * @param tipo de alerta
     * @param titulo de la ventana
     * @param mensaje que se le muestra al usuario
     */
    public static void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Metodo que da formato de pesos a un valor, por ejemplo $80.000
     * @param valor
     * @return
     */
    public static String dinero(double valor) {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setGroupingSeparator('.');
        DecimalFormat formato = new DecimalFormat("#,##0", simbolos);
        return "$" + formato.format(valor);
    }

    /**
     * Metodo que arma el texto con el que se muestra un elemento en las listas
     * @param elemento
     * @return
     */
    public static String textoElemento(ElementoServicio elemento) {
        String texto = elemento.nombre() + " - " + dinero(elemento.costo());
        if (elemento instanceof PaqueteTecnologico) {
            texto = "[Paquete] " + texto;
        }
        return texto;
    }

    /**
     * Metodo que deja visible el texto de ayuda en un ComboBox cuando no tiene nada seleccionado
     * @param combo
     * @param textoVacio texto que se muestra cuando no hay seleccion
     */
    public static <T> void textoVacio(ComboBox<T> combo, String textoVacio) {
        combo.setButtonCell(new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean vacio) {
                super.updateItem(item, vacio);
                if (vacio || item == null) {
                    setText(textoVacio);
                } else if (combo.getConverter() != null) {
                    setText(combo.getConverter().toString(item));
                } else {
                    setText(item.toString());
                }
            }
        });
    }

    /**
     * Metodo que convierte un elemento en texto para los ComboBox y ListView
     * @return
     */
    public static StringConverter<ElementoServicio> convertidorElemento() {
        return new StringConverter<ElementoServicio>() {
            @Override
            public String toString(ElementoServicio elemento) {
                if (elemento == null) {
                    return "";
                }
                return textoElemento(elemento);
            }

            @Override
            public ElementoServicio fromString(String texto) {
                return null;
            }
        };
    }
}
