package co.edu.uniquindio.poo.parcial2.model;

import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class LegacySupportAdapterTest {

    private static final Logger LOG = Logger.getLogger(LegacySupportAdapterTest.class.getName());

    /**
     * Test para verificar que "MANTENIMIENTO" se convierta en el codigo 1
     */
    @Test
    void obtenerCodigoEspecialidad() {
        LOG.info("Inicio Test Obtener Codigo Especialidad");

        LegacySupportAdapter adapter = new LegacySupportAdapter(new LegacySupport() {
            @Override
            public String consultarTecnico(int codigo) {
                return codigo == 1 ? "TEC-105|DISPONIBLE|30000" : "SIN_TECNICO|NO_DISPONIBLE|0";
            }
        });

        assertTrue(adapter.consultarTecnico("MANTENIMIENTO").isDisponible());

        LOG.info("Fin Test Obtener Codigo Especialidad");
    }

}