package co.edu.uniquindio.poo.parcial2.controller;

import co.edu.uniquindio.poo.parcial2.model.DecoradorServicio;
import co.edu.uniquindio.poo.parcial2.model.ElementoServicio;
import co.edu.uniquindio.poo.parcial2.model.Empresa;
import co.edu.uniquindio.poo.parcial2.model.PaqueteTecnologico;
import co.edu.uniquindio.poo.parcial2.model.Servicio;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class InicioController {

    @FXML
    private Label lblResumen;

    @FXML
    private void initialize() {
        Empresa empresa = AppContext.getInstance().getEmpresa();
        int servicios = 0;
        int personalizados = 0;
        int paquetes = 0;
        for (ElementoServicio elemento : empresa.getElementos()) {
            if (elemento instanceof Servicio) {
                servicios++;
            } else if (elemento instanceof DecoradorServicio) {
                personalizados++;
            } else if (elemento instanceof PaqueteTecnologico) {
                paquetes++;
            }
        }
        lblResumen.setText("Servicios: " + servicios + ". Servicios personalizados: " + personalizados
                + ". Paquetes: " + paquetes + ". Clientes: " + empresa.getClientes().size()
                + ". Solicitudes: " + empresa.getSolicitudes().size() + ".");
    }

    @FXML
    private void abrirServicios() {
        Ventanas.mostrar("Servicios");
    }

    @FXML
    private void abrirPaquetes() {
        Ventanas.mostrar("Paquetes");
    }

    @FXML
    private void abrirSolicitudes() {
        Ventanas.mostrar("Solicitudes");
    }

    @FXML
    private void abrirTecnicos() {
        Ventanas.mostrar("Tecnicos");
    }
}
