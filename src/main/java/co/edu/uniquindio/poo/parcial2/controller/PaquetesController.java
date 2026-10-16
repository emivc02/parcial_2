package co.edu.uniquindio.poo.parcial2.controller;

import co.edu.uniquindio.poo.parcial2.model.ElementoServicio;
import co.edu.uniquindio.poo.parcial2.model.Empresa;
import co.edu.uniquindio.poo.parcial2.model.PaqueteTecnologico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.StringConverter;

import java.util.ArrayList;

public class PaquetesController {

    @FXML
    private TextField txtNombre;
    @FXML
    private ComboBox<PaqueteTecnologico> cmbPaquetes;
    @FXML
    private ComboBox<ElementoServicio> cmbElementos;
    @FXML
    private TreeView<String> arbol;
    @FXML
    private Label lblCostoTotal;

    private Empresa empresa;

    @FXML
    private void initialize() {
        empresa = AppContext.getInstance().getEmpresa();

        cmbPaquetes.setConverter(new StringConverter<PaqueteTecnologico>() {
            @Override
            public String toString(PaqueteTecnologico paquete) {
                if (paquete == null) {
                    return "";
                }
                return paquete.nombre();
            }

            @Override
            public PaqueteTecnologico fromString(String texto) {
                return null;
            }
        });
        cmbElementos.setConverter(Ventanas.convertidorElemento());
        Ventanas.textoVacio(cmbPaquetes, "Selecciona un paquete");
        Ventanas.textoVacio(cmbElementos, "Servicio, servicio personalizado o paquete");

        cargarCombos();
        mostrarPaquete();
    }

    /**
     * Metodo que carga los paquetes y los elementos que se pueden agregar
     */
    private void cargarCombos() {
        PaqueteTecnologico seleccionado = cmbPaquetes.getValue();
        ObservableList<PaqueteTecnologico> paquetes = FXCollections.observableArrayList();
        ObservableList<ElementoServicio> elementos = FXCollections.observableArrayList();
        for (ElementoServicio elemento : empresa.getElementos()) {
            elementos.add(elemento);
            if (elemento instanceof PaqueteTecnologico) {
                paquetes.add((PaqueteTecnologico) elemento);
            }
        }
        cmbPaquetes.setItems(paquetes);
        cmbElementos.setItems(elementos);
        if (seleccionado != null) {
            cmbPaquetes.setValue(seleccionado);
        }
    }

    @FXML
    private void crearPaquete() {
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            Ventanas.mostrarAlerta(Alert.AlertType.WARNING, "Falta el nombre", "Escribe un nombre para el paquete.");
            return;
        }
        for (ElementoServicio elemento : empresa.getElementos()) {
            if (elemento instanceof PaqueteTecnologico && elemento.nombre().equalsIgnoreCase(nombre)) {
                Ventanas.mostrarAlerta(Alert.AlertType.ERROR, "Nombre repetido", "Ya existe un paquete llamado " + nombre + ".");
                return;
            }
        }
        PaqueteTecnologico paquete = new PaqueteTecnologico(nombre, new ArrayList<ElementoServicio>());
        empresa.agregarElemento(paquete);
        cargarCombos();
        cmbPaquetes.setValue(paquete);
        txtNombre.clear();
        mostrarPaquete();
    }

    @FXML
    private void agregarAlPaquete() {
        PaqueteTecnologico paquete = cmbPaquetes.getValue();
        ElementoServicio elemento = cmbElementos.getValue();
        if (paquete == null) {
            Ventanas.mostrarAlerta(Alert.AlertType.WARNING, "Falta el paquete", "Selecciona el paquete al que quieres agregar el componente.");
            return;
        }
        if (elemento == null) {
            Ventanas.mostrarAlerta(Alert.AlertType.WARNING, "Falta el componente", "Selecciona el servicio o paquete que quieres agregar.");
            return;
        }
        if (paquete.getListaElementos().contains(elemento)) {
            Ventanas.mostrarAlerta(Alert.AlertType.WARNING, "Componente repetido", "El paquete ya contiene ese componente.");
            return;
        }
        try {
            paquete.agregarElemento(elemento);
            cmbElementos.getSelectionModel().clearSelection();
            mostrarPaquete();
        } catch (IllegalArgumentException e) {
            Ventanas.mostrarAlerta(Alert.AlertType.ERROR, "No se puede agregar", e.getMessage());
        }
    }

    @FXML
    private void mostrarPaquete() {
        PaqueteTecnologico paquete = cmbPaquetes.getValue();
        if (paquete == null) {
            arbol.setRoot(null);
            lblCostoTotal.setText("Costo total: selecciona un paquete");
        } else {
            arbol.setRoot(construirArbol(paquete));
            lblCostoTotal.setText("Costo total: " + Ventanas.dinero(paquete.costo()));
        }
    }

    /**
     * Metodo recursivo que arma el arbol de componentes de un paquete
     * @param elemento
     * @return
     */
    private TreeItem<String> construirArbol(ElementoServicio elemento) {
        TreeItem<String> nodo = new TreeItem<String>(Ventanas.textoElemento(elemento));
        if (elemento instanceof PaqueteTecnologico) {
            PaqueteTecnologico paquete = (PaqueteTecnologico) elemento;
            for (ElementoServicio hijo : paquete.getListaElementos()) {
                nodo.getChildren().add(construirArbol(hijo));
            }
            nodo.setExpanded(true);
        }
        return nodo;
    }

    @FXML
    private void volverInicio() {
        Ventanas.mostrar("Inicio");
    }
}
