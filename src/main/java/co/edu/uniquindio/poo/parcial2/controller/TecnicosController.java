package co.edu.uniquindio.poo.parcial2.controller;

import co.edu.uniquindio.poo.parcial2.model.ElementoServicio;
import co.edu.uniquindio.poo.parcial2.model.Empresa;
import co.edu.uniquindio.poo.parcial2.model.Solicitud;
import co.edu.uniquindio.poo.parcial2.model.Tecnico;
import co.edu.uniquindio.poo.parcial2.services.SolicitudService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class TecnicosController {

    @FXML
    private ComboBox<String> cmbEspecialidad;
    @FXML
    private Label lblTecnicoId;
    @FXML
    private Label lblTecnicoEstado;
    @FXML
    private Label lblTecnicoDesplazamiento;
    @FXML
    private TableView<Solicitud> tablaSolicitudes;
    @FXML
    private TableColumn<Solicitud, String> colCodigo;
    @FXML
    private TableColumn<Solicitud, String> colCliente;
    @FXML
    private TableColumn<Solicitud, String> colAtencion;
    @FXML
    private TableColumn<Solicitud, String> colEstado;
    @FXML
    private TextArea txtDesglose;

    private Empresa empresa;
    private SolicitudService solicitudService;

    @FXML
    private void initialize() {
        empresa = AppContext.getInstance().getEmpresa();
        solicitudService = AppContext.getInstance().getSolicitudService();

        cmbEspecialidad.setItems(FXCollections.observableArrayList("MANTENIMIENTO", "REDES", "RECUPERACION"));
        Ventanas.textoVacio(cmbEspecialidad, "Especialidad");

        colCodigo.setCellValueFactory(dato -> new SimpleStringProperty(dato.getValue().getCodigo()));
        colCliente.setCellValueFactory(dato -> new SimpleStringProperty(dato.getValue().getCliente().getNombre()));
        colAtencion.setCellValueFactory(dato -> {
            String atencion = "No presencial";
            if (dato.getValue().isPresencial()) {
                atencion = "Presencial: " + dato.getValue().getEspecialidad();
            }
            return new SimpleStringProperty(atencion);
        });
        colEstado.setCellValueFactory(dato -> {
            String estado = "Pendiente";
            if (dato.getValue().isConfirmada()) {
                estado = "Confirmada";
            }
            return new SimpleStringProperty(estado);
        });

        tablaSolicitudes.getSelectionModel().selectedItemProperty().addListener((obs, anterior, nueva) -> mostrarDesglose(nueva));
        cargarSolicitudes();
    }

    private void cargarSolicitudes() {
        tablaSolicitudes.setItems(FXCollections.observableArrayList(empresa.getSolicitudes()));
    }

    @FXML
    private void consultarTecnico() {
        String especialidad = cmbEspecialidad.getValue();
        if (especialidad == null) {
            Ventanas.mostrarAlerta(Alert.AlertType.WARNING, "Falta la especialidad", "Selecciona la especialidad que quieres consultar.");
            return;
        }
        Tecnico tecnico = solicitudService.consultarTecnico(especialidad);
        lblTecnicoId.setText("Técnico: " + tecnico.getIdTecnico());
        if (tecnico.isDisponible()) {
            lblTecnicoEstado.setText("Disponibilidad: disponible");
        } else {
            lblTecnicoEstado.setText("Disponibilidad: no disponible");
        }
        lblTecnicoDesplazamiento.setText("Desplazamiento: " + Ventanas.dinero(tecnico.getCostoDesplazamiento()));
    }

    /**
     * Metodo que muestra como se calcula el costo de la solicitud seleccionada
     * @param solicitud
     */
    private void mostrarDesglose(Solicitud solicitud) {
        if (solicitud == null) {
            txtDesglose.clear();
            return;
        }
        String texto = "Solicitud " + solicitud.getCodigo() + "\n\n";
        double subtotal = 0;
        for (ElementoServicio elemento : solicitud.getListaElementos()) {
            texto += "  - " + elemento.nombre() + ": " + Ventanas.dinero(elemento.costo()) + "\n";
            subtotal += elemento.costo();
        }
        texto += "\nSubtotal de servicios: " + Ventanas.dinero(subtotal) + "\n";
        if (solicitud.isPresencial()) {
            Tecnico tecnico = solicitudService.consultarTecnico(solicitud.getEspecialidad());
            if (tecnico.isDisponible()) {
                texto += "Técnico " + tecnico.getIdTecnico() + " disponible para " + solicitud.getEspecialidad() + "\n";
                texto += "Desplazamiento (se cobra una sola vez): " + Ventanas.dinero(tecnico.getCostoDesplazamiento()) + "\n";
            } else {
                texto += "No hay técnicos disponibles para " + solicitud.getEspecialidad()
                        + ". La solicitud no se puede confirmar.\n";
            }
        } else {
            texto += "Atención no presencial: sin costo de desplazamiento\n";
        }
        texto += "\nCosto total: " + Ventanas.dinero(solicitudService.calcularCostoTotal(solicitud));
        if (solicitud.isConfirmada()) {
            texto += "\nEstado: confirmada";
        }
        txtDesglose.setText(texto);
    }

    @FXML
    private void confirmarSolicitud() {
        Solicitud solicitud = tablaSolicitudes.getSelectionModel().getSelectedItem();
        if (solicitud == null) {
            Ventanas.mostrarAlerta(Alert.AlertType.WARNING, "Falta la solicitud", "Selecciona la solicitud que quieres confirmar.");
            return;
        }
        if (solicitud.isConfirmada()) {
            Ventanas.mostrarAlerta(Alert.AlertType.INFORMATION, "Solicitud confirmada", "La solicitud " + solicitud.getCodigo() + " ya fue confirmada.");
            return;
        }
        boolean confirmada = solicitudService.confirmarSolicitud(solicitud);
        cargarSolicitudes();
        tablaSolicitudes.getSelectionModel().select(solicitud);
        if (confirmada) {
            Ventanas.mostrarAlerta(Alert.AlertType.INFORMATION, "Solicitud confirmada",
                    "La solicitud " + solicitud.getCodigo() + " quedó confirmada.\nCosto total: "
                            + Ventanas.dinero(solicitud.getCostoTotal()));
        } else {
            Ventanas.mostrarAlerta(Alert.AlertType.WARNING, "No se puede confirmar",
                    "No hay técnicos disponibles para " + solicitud.getEspecialidad()
                            + ".\nLa solicitud " + solicitud.getCodigo() + " sigue pendiente.");
        }
    }

    @FXML
    private void volverInicio() {
        Ventanas.mostrar("Inicio");
    }
}
