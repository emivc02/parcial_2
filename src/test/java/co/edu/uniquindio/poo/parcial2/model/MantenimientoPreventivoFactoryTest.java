package co.edu.uniquindio.poo.parcial2.model;
import co.edu.uniquindio.poo.parcial2.model.MantenimientoPreventivoFactory;
import co.edu.uniquindio.poo.parcial2.model.Servicio;
import co.edu.uniquindio.poo.parcial2.model.ServicioFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

class MantenimientoPreventivoFactoryTest {
    private static final Logger LOG = Logger.getLogger(MantenimientoPreventivoFactoryTest.class.getName());

    /**
     * Test para verificar que el metodo crearServicio cree un mantenimiento preventivo con su costo base
     */
    @Test
    void crearServicio() {
        LOG.info("Inicio Test Crear Servicio");

        ServicioFactory fabrica = new MantenimientoPreventivoFactory();
        Servicio servicio = fabrica.crearServicio("SRV-001");
        double resultadoEsperado = 80000;
        double resultadoReal = servicio.costo();
        Assertions.assertEquals(resultadoEsperado, resultadoReal);

        LOG.info("Fin Test Crear Servicio");
    }
}