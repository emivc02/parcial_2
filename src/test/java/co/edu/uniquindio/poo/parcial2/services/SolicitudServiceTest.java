package co.edu.uniquindio.poo.parcial2.services;

import co.edu.uniquindio.poo.parcial2.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
class SolicitudServiceTest {

    private static final Logger LOG = Logger.getLogger(SolicitudServiceTest.class.getName());

    private Solicitud crearSolicitud(ElementoServicio elemento) {
        return new Solicitud.Builder()
                .codigo("SOL.1")
                .fechaCreacion(LocalDate.now())
                .cliente(new Cliente("1090876943", "Emi", "ely@correo.com"))
                .listaElementos(List.of(elemento))
                .presencial(true)
                .especialidad("MANTENIMIENTO")
                .build();
    }

    @Test
    void calcularCostoTotal() {
        LOG.info("Inicio Test Calcular Costo Total");

        SolicitudService service = new SolicitudService(esp -> new Tecnico("TEC-105", true, 30000));
        Solicitud solicitud = crearSolicitud(new MantenimientoCorrectivoFactory().crearServicio("S-1"));

        double resultadoEsperado = 150000; // 120.000 + 30.000
        double resultadoReal = service.calcularCostoTotal(solicitud);
        assertEquals(resultadoEsperado, resultadoReal);

        LOG.info("Fin Test Calcular Costo Total");
    }

    @Test
    void confirmarSolicitud() {
        LOG.info("Inicio Test Confirmar Solicitud");

        SolicitudService service = new SolicitudService(esp -> new Tecnico("TEC-105", true, 30000));
        Solicitud solicitud = crearSolicitud(new MantenimientoPreventivoFactory().crearServicio("S-2"));

        boolean resultadoEsperado = true;
        boolean resultadoReal = service.confirmarSolicitud(solicitud);
        assertEquals(resultadoEsperado, resultadoReal);

        LOG.info("Fin Test Confirmar Solicitud");
    }
  
}