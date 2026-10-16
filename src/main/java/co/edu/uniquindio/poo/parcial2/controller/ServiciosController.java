package co.edu.uniquindio.poo.parcial2.controller;

import co.edu.uniquindio.poo.parcial2.model.AtencionDomicilio;
import co.edu.uniquindio.poo.parcial2.model.AtencionPrioritaria;
import co.edu.uniquindio.poo.parcial2.model.DecoradorServicio;
import co.edu.uniquindio.poo.parcial2.model.ElementoServicio;
import co.edu.uniquindio.poo.parcial2.model.Empresa;
import co.edu.uniquindio.poo.parcial2.model.InstalacionRedFactory;
import co.edu.uniquindio.poo.parcial2.model.MantenimientoCorrectivoFactory;
import co.edu.uniquindio.poo.parcial2.model.MantenimientoPreventivoFactory;
import co.edu.uniquindio.poo.parcial2.model.RecuperacionInformacionFactory;
import co.edu.uniquindio.poo.parcial2.model.Servicio;
import co.edu.uniquindio.poo.parcial2.model.ServicioFactory;
import co.edu.uniquindio.poo.parcial2.model.SoporteTecnico30Dias;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ServiciosController {

    @FXML
    private TableView<Servicio> tablaCatalogo;
    @FXML
    private TableColumn<Servicio, String> colCodigo;
    @FXML
    private TableColumn<Servicio, String> colNombre;
    @FXML
    private TableColumn<Servicio, String> colDescripcion;
    @FXML
    private TableColumn<Servicio, String> colCosto;
    @FXML
    private ListView<String> listaPersonalizados;
    @FXML
    private ComboBox<String> cmbTipo;
    @FXML
    private TextField txtCodigo;
    @FXML
    private CheckBox chkPrioritaria;
    @FXML
    private CheckBox chkSoporte;
    @FXML
    private CheckBox chkDomicilio;
    @FXML
    private Label lblCostoBase;
    @FXML
    private Label lblIncrementos;
    @FXML
    private Label lblCostoFinal;

    private Empresa empresa;

    @FXML
    private void initialize() {
        empresa = AppContext.getInstance().getEmpresa();

        colCodigo.setCellValueFactory(dato -> new SimpleStringProperty(dato.getValue().getCodigo()));
        colNombre.setCellValueFactory(dato -> new SimpleStringProperty(dato.getValue().getNombre()));
        colDescripcion.setCellValueFactory(dato -> new SimpleStringProperty(dato.getValue().getDescripcion()));
        colCosto.setCellValueFactory(dato -> new SimpleStringProperty(Ventanas.dinero(dato.getValue().getCostoBase())));

        cmbTipo.setItems(FXCollections.observableArrayList("Mantenimiento preventivo",
                "Mantenimiento correctivo", "Instalación de redes", "Recuperación de información"));

        Ventanas.textoVacio(cmbTipo, "Tipo de servicio");

        // Las celdas de la lista de personalizados se ajustan al ancho para que el texto no se corte
        listaPersonalizados.setCellFactory(lista -> new ListCell<String>() {
            @Override
            protected void updateItem(String texto, boolean vacio) {
                super.updateItem(texto, vacio);
                if (vacio || texto == null) {
                    setText(null);
                } else {
                    setText(texto);
                    setWrapText(true);
                    setPrefWidth(0);
                }
            }
        });

        tablaCatalogo.getSelectionModel().selectedItemProperty().addListener((obs, anterior, nuevo) -> actualizarCostos());

        cargarCatalogo();
        cargarPersonalizados();
        txtCodigo.setText(siguienteCodigo());
        actualizarCostos();
    }

    /**
     * Metodo que muestra en la tabla los servicios del catalogo
     */
    private void cargarCatalogo() {
        ObservableList<Servicio> servicios = FXCollections.observableArrayList();
        for (ElementoServicio elemento : empresa.getElementos()) {
            if (elemento instanceof Servicio) {
                servicios.add((Servicio) elemento);
            }
        }
        tablaCatalogo.setItems(servicios);
    }

    /**
     * Metodo que muestra los servicios personalizados guardados
     */
    private void cargarPersonalizados() {
        ObservableList<String> textos = FXCollections.observableArrayList();
        for (ElementoServicio elemento : empresa.getElementos()) {
            if (elemento instanceof DecoradorServicio) {
                textos.add(elemento.nombre() + "\nCosto final: " + Ventanas.dinero(elemento.costo()));
            }
        }
        listaPersonalizados.setItems(textos);
    }

    /**
     * Metodo que entrega la fabrica que corresponde al tipo de servicio
     * @param tipo nombre del tipo de servicio
     * @return
     */
    private ServicioFactory obtenerFabrica(String tipo) {
        ServicioFactory fabrica = null;
        switch (tipo) {
            case "Mantenimiento preventivo":
                fabrica = new MantenimientoPreventivoFactory();
                break;
            case "Mantenimiento correctivo":
                fabrica = new MantenimientoCorrectivoFactory();
                break;
            case "Instalación de redes":
                fabrica = new InstalacionRedFactory();
                break;
            case "Recuperación de información":
                fabrica = new RecuperacionInformacionFactory();
                break;
        }
        return fabrica;
    }

    private boolean existeCodigo(String codigo) {
        for (ElementoServicio elemento : empresa.getElementos()) {
            if (elemento instanceof Servicio) {
                Servicio servicio = (Servicio) elemento;
                if (servicio.getCodigo().equalsIgnoreCase(codigo)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String siguienteCodigo() {
        int cantidad = 0;
        for (ElementoServicio elemento : empresa.getElementos()) {
            if (elemento instanceof Servicio) {
                cantidad++;
            }
        }
        int numero = cantidad + 1;
        String codigo = "SRV-00" + numero;
        while (existeCodigo(codigo)) {
            numero++;
            codigo = "SRV-00" + numero;
        }
        return codigo;
    }

    @FXML
    private void crearServicio() {
        String tipo = cmbTipo.getValue();
        String codigo = txtCodigo.getText().trim();
        if (tipo == null) {
            Ventanas.mostrarAlerta(Alert.AlertType.WARNING, "Falta el tipo", "Selecciona el tipo de servicio que quieres crear.");
            return;
        }
        if (codigo.isEmpty()) {
            Ventanas.mostrarAlerta(Alert.AlertType.WARNING, "Falta el código", "Escribe un código para el servicio.");
            return;
        }
        if (existeCodigo(codigo)) {
            Ventanas.mostrarAlerta(Alert.AlertType.ERROR, "Código repetido", "Ya existe un servicio con el código " + codigo + ".");
            return;
        }
        ServicioFactory fabrica = obtenerFabrica(tipo);
        Servicio servicio = fabrica.crearServicio(codigo);
        empresa.agregarElemento(servicio);
        cargarCatalogo();
        tablaCatalogo.getSelectionModel().select(servicio);
        cmbTipo.getSelectionModel().clearSelection();
        txtCodigo.setText(siguienteCodigo());
        Ventanas.mostrarAlerta(Alert.AlertType.INFORMATION, "Servicio creado",
                "Se creó " + servicio.nombre() + " con el código " + codigo + ".");
    }

    /**
     * Metodo que envuelve el servicio con los decoradores marcados.
     * El servicio original del catalogo no se modifica.
     * @param servicio
     * @return
     */
    private ElementoServicio construirPersonalizado(Servicio servicio) {
        ElementoServicio resultado = servicio;
        if (chkPrioritaria.isSelected()) {
            resultado = new AtencionPrioritaria(resultado);
        }
        if (chkSoporte.isSelected()) {
            resultado = new SoporteTecnico30Dias(resultado);
        }
        if (chkDomicilio.isSelected()) {
            resultado = new AtencionDomicilio(resultado);
        }
        return resultado;
    }

    @FXML
    private void actualizarCostos() {
        Servicio servicio = tablaCatalogo.getSelectionModel().getSelectedItem();
        if (servicio == null) {
            lblCostoBase.setText("Costo base: sin selección");
            lblIncrementos.setText("Selecciona un servicio del catálogo para ver los incrementos.");
            lblCostoFinal.setText("Costo final: sin selección");
            return;
        }
        String incrementos = "";
        if (chkPrioritaria.isSelected()) {
            incrementos += "Atención prioritaria: +"
                    + Ventanas.dinero(new AtencionPrioritaria(servicio).costo() - servicio.costo()) + "\n";
        }
        if (chkSoporte.isSelected()) {
            incrementos += "Soporte técnico 30 días: +"
                    + Ventanas.dinero(new SoporteTecnico30Dias(servicio).costo() - servicio.costo()) + "\n";
        }
        if (chkDomicilio.isSelected()) {
            incrementos += "Atención a domicilio: +"
                    + Ventanas.dinero(new AtencionDomicilio(servicio).costo() - servicio.costo()) + "\n";
        }
        if (incrementos.isEmpty()) {
            incrementos = "Sin características complementarias";
        }
        ElementoServicio personalizado = construirPersonalizado(servicio);
        lblCostoBase.setText("Costo base: " + Ventanas.dinero(servicio.costo()));
        lblIncrementos.setText(incrementos.trim());
        lblCostoFinal.setText("Costo final: " + Ventanas.dinero(personalizado.costo()));
    }

    @FXML
    private void guardarPersonalizado() {
        Servicio servicio = tablaCatalogo.getSelectionModel().getSelectedItem();
        if (servicio == null) {
            Ventanas.mostrarAlerta(Alert.AlertType.WARNING, "Falta el servicio", "Selecciona un servicio del catálogo.");
            return;
        }
        if (!chkPrioritaria.isSelected() && !chkSoporte.isSelected() && !chkDomicilio.isSelected()) {
            Ventanas.mostrarAlerta(Alert.AlertType.WARNING, "Faltan características",
                    "Marca al menos una característica complementaria.");
            return;
        }
        ElementoServicio personalizado = construirPersonalizado(servicio);
        for (ElementoServicio elemento : empresa.getElementos()) {
            if (elemento instanceof DecoradorServicio && elemento.nombre().equals(personalizado.nombre())) {
                Ventanas.mostrarAlerta(Alert.AlertType.ERROR, "Servicio repetido",
                        "Ese servicio personalizado ya está guardado.");
                return;
            }
        }
        empresa.agregarElemento(personalizado);
        String mensaje = personalizado.nombre() + "\nCosto final: " + Ventanas.dinero(personalizado.costo())
                + "\nEl servicio original del catálogo no cambió.";
        cargarPersonalizados();
        chkPrioritaria.setSelected(false);
        chkSoporte.setSelected(false);
        chkDomicilio.setSelected(false);
        actualizarCostos();
        Ventanas.mostrarAlerta(Alert.AlertType.INFORMATION, "Servicio personalizado guardado", mensaje);
    }

    @FXML
    private void volverInicio() {
        Ventanas.mostrar("Inicio");
    }
}
