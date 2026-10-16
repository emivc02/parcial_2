package co.edu.uniquindio.poo.parcial2.controller;

import co.edu.uniquindio.poo.parcial2.model.Cliente;
import co.edu.uniquindio.poo.parcial2.model.ElementoServicio;
import co.edu.uniquindio.poo.parcial2.model.Empresa;
import co.edu.uniquindio.poo.parcial2.model.NivelPrioridad;
import co.edu.uniquindio.poo.parcial2.model.Solicitud;
import co.edu.uniquindio.poo.parcial2.services.SolicitudService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.ArrayList;

public class SolicitudesController {

    @FXML
    private TextField txtCodigo;
    @FXML
    private ComboBox<Cliente> cmbCliente;
    @FXML
    private CheckBox chkPresencial;
    @FXML
    private ComboBox<String> cmbEspecialidad;
    @FXML
    private DatePicker dpFechaProgramada;
    @FXML
    private ComboBox<NivelPrioridad> cmbPrioridad;
    @FXML
    private TextArea txtObservaciones;
    @FXML
    private ListView<ElementoServicio> listaDisponibles;
    @FXML
    private ListView<ElementoServicio> listaContratados;
    @FXML
    private Label lblSubtotal;
    @FXML
    private TableView<Solicitud> tablaSolicitudes;
    @FXML
    private TableColumn<Solicitud, String> colCodigo;
    @FXML
    private TableColumn<Solicitud, String> colCliente;
    @FXML
    private TableColumn<Solicitud, String> colElementos;
    @FXML
    private TableColumn<Solicitud, String> colEstado;
    @FXML
    private TableColumn<Solicitud, String> colCosto;
    @FXML
    private TextArea txtDetalle;

    private Empresa empresa;
    private SolicitudService solicitudService;

    @FXML
    private void initialize() {
        empresa = AppContext.getInstance().getEmpresa();
        solicitudService = AppContext.getInstance().getSolicitudService();

        cmbCliente.setItems(FXCollections.observableArrayList(empresa.getClientes()));
        cmbCliente.setConverter(new StringConverter<Cliente>() {
            @Override
            public String toString(Cliente cliente) {
                if (cliente == null) {
                    return "";
                }
                return cliente.getNombre() + " (" + cliente.getDocumento() + ")";
            }

            @Override
            public Cliente fromString(String texto) {
                return null;
            }
        });

        cmbEspecialidad.setItems(FXCollections.observableArrayList("MANTENIMIENTO", "REDES", "RECUPERACION"));
        cmbEspecialidad.setDisable(true);
        cmbPrioridad.setItems(FXCollections.observableArrayList(NivelPrioridad.values()));
        Ventanas.textoVacio(cmbCliente, "Cliente registrado");
        Ventanas.textoVacio(cmbEspecialidad, "Solo si es presencial");
        Ventanas.textoVacio(cmbPrioridad, "Opcional");

        listaDisponibles.setItems(FXCollections.observableArrayList(empresa.getElementos()));
        listaDisponibles.setCellFactory(TextFieldListCell.forListView(Ventanas.convertidorElemento()));
        listaDisponibles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        listaContratados.setItems(FXCollections.observableArrayList());
        listaContratados.setCellFactory(TextFieldListCell.forListView(Ventanas.convertidorElemento()));
        listaContratados.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        txtCodigo.setText(AppContext.getInstance().generarCodigoSolicitud());

        colCodigo.setCellValueFactory(dato -> new SimpleStringProperty(dato.getValue().getCodigo()));
        colCliente.setCellValueFactory(dato -> new SimpleStringProperty(dato.getValue().getCliente().getNombre()));
        colElementos.setCellValueFactory(dato -> new SimpleStringProperty("" + dato.getValue().getListaElementos().size()));
        colEstado.setCellValueFactory(dato -> {
            String estado = "Pendiente";
            if (dato.getValue().isConfirmada()) {
                estado = "Confirmada";
            }
            return new SimpleStringProperty(estado);
        });
        colCosto.setCellValueFactory(dato -> new SimpleStringProperty(
                Ventanas.dinero(solicitudService.calcularCostoTotal(dato.getValue()))));

        tablaSolicitudes.getSelectionModel().selectedItemProperty().addListener((obs, anterior, nueva) -> mostrarDetalle(nueva));

        cargarSolicitudes();
        actualizarSubtotal();
    }

    private void cargarSolicitudes() {
        tablaSolicitudes.setItems(FXCollections.observableArrayList(empresa.getSolicitudes()));
    }

    @FXML
    private void cambiarPresencial() {
        cmbEspecialidad.setDisable(!chkPresencial.isSelected());
        if (!chkPresencial.isSelected()) {
            cmbEspecialidad.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void agregarElementos() {
        ObservableList<ElementoServicio> seleccionados = listaDisponibles.getSelectionModel().getSelectedItems();
        if (seleccionados.isEmpty()) {
            Ventanas.mostrarAlerta(Alert.AlertType.WARNING, "Falta seleccionar", "Selecciona uno o varios elementos de la lista de disponibles.");
            return;
        }
        for (ElementoServicio elemento : new ArrayList<ElementoServicio>(seleccionados)) {
            if (!listaContratados.getItems().contains(elemento)) {
                listaContratados.getItems().add(elemento);
            }
        }
        actualizarSubtotal();
    }

    @FXML
    private void quitarElementos() {
        ObservableList<ElementoServicio> seleccionados = listaContratados.getSelectionModel().getSelectedItems();
        if (seleccionados.isEmpty()) {
            Ventanas.mostrarAlerta(Alert.AlertType.WARNING, "Falta seleccionar", "Selecciona los elementos contratados que quieres quitar.");
            return;
        }
        listaContratados.getItems().removeAll(new ArrayList<ElementoServicio>(seleccionados));
        actualizarSubtotal();
    }

    private void actualizarSubtotal() {
        double suma = 0;
        for (ElementoServicio elemento : listaContratados.getItems()) {
            suma += elemento.costo();
        }
        lblSubtotal.setText("Subtotal de los elementos: " + Ventanas.dinero(suma));
    }

    @FXML
    private void registrarSolicitud() {
        LocalDate fechaProgramada = dpFechaProgramada.getValue();
        if (fechaProgramada != null && fechaProgramada.isBefore(LocalDate.now())) {
            Ventanas.mostrarAlerta(Alert.AlertType.ERROR, "Fecha no válida", "La fecha programada no puede ser anterior a hoy.");
            return;
        }
        try {
            Solicitud.Builder builder = new Solicitud.Builder();
            builder.codigo(txtCodigo.getText().trim());
            builder.fechaCreacion(LocalDate.now());
            builder.cliente(cmbCliente.getValue());
            builder.listaElementos(new ArrayList<ElementoServicio>(listaContratados.getItems()));
            builder.presencial(chkPresencial.isSelected());
            if (chkPresencial.isSelected()) {
                builder.especialidad(cmbEspecialidad.getValue());
            }
            if (fechaProgramada != null) {
                builder.fechaProgramada(fechaProgramada);
            }
            if (!txtObservaciones.getText().trim().isEmpty()) {
                builder.observaciones(txtObservaciones.getText().trim());
            }
            if (cmbPrioridad.getValue() != null) {
                builder.prioridad(cmbPrioridad.getValue());
            }
            Solicitud solicitud = builder.build();
            empresa.agregarSolicitud(solicitud);

            cargarSolicitudes();
            tablaSolicitudes.getSelectionModel().select(solicitud);
            limpiarFormulario();
            Ventanas.mostrarAlerta(Alert.AlertType.INFORMATION, "Solicitud registrada",
                    "La solicitud " + solicitud.getCodigo() + " quedó pendiente. "
                            + "Confírmala en la sección Técnicos y costos.");
        } catch (IllegalArgumentException e) {
            Ventanas.mostrarAlerta(Alert.AlertType.ERROR, "No se pudo registrar", e.getMessage());
        }
    }

    @FXML
    private void limpiarFormulario() {
        txtCodigo.setText(AppContext.getInstance().generarCodigoSolicitud());
        cmbCliente.getSelectionModel().clearSelection();
        chkPresencial.setSelected(false);
        cambiarPresencial();
        dpFechaProgramada.setValue(null);
        cmbPrioridad.getSelectionModel().clearSelection();
        txtObservaciones.clear();
        listaContratados.getItems().clear();
        actualizarSubtotal();
    }

    /**
     * Metodo que muestra toda la informacion de la solicitud seleccionada
     * @param solicitud
     */
    private void mostrarDetalle(Solicitud solicitud) {
        if (solicitud == null) {
            txtDetalle.clear();
            return;
        }
        String texto = "Código: " + solicitud.getCodigo() + "\n";
        texto += "Fecha de creación: " + solicitud.getFechaCreacion() + "\n";
        texto += "Cliente: " + solicitud.getCliente().getNombre() + " (" + solicitud.getCliente().getDocumento() + ")\n";
        texto += "Correo: " + solicitud.getCliente().getCorreo() + "\n\n";
        texto += "Elementos contratados:\n";
        for (ElementoServicio elemento : solicitud.getListaElementos()) {
            texto += "  - " + Ventanas.textoElemento(elemento) + "\n";
        }
        if (solicitud.isPresencial()) {
            texto += "\nAtención: presencial (" + solicitud.getEspecialidad() + ")\n";
        } else {
            texto += "\nAtención: no presencial\n";
        }
        if (solicitud.getFechaProgramada() == null) {
            texto += "Fecha programada: sin definir\n";
        } else {
            texto += "Fecha programada: " + solicitud.getFechaProgramada() + "\n";
        }
        if (solicitud.getObservaciones() == null) {
            texto += "Observaciones: sin observaciones\n";
        } else {
            texto += "Observaciones: " + solicitud.getObservaciones() + "\n";
        }
        if (solicitud.getPrioridad() == null) {
            texto += "Prioridad: sin definir\n";
        } else {
            texto += "Prioridad: " + solicitud.getPrioridad() + "\n";
        }
        if (solicitud.isConfirmada()) {
            texto += "Estado: confirmada\n";
        } else {
            texto += "Estado: pendiente de confirmación\n";
        }
        texto += "\nCosto total: " + Ventanas.dinero(solicitudService.calcularCostoTotal(solicitud));
        txtDetalle.setText(texto);
    }

    @FXML
    private void volverInicio() {
        Ventanas.mostrar("Inicio");
    }
}
