package co.edu.uniquindio.poo.parcial2.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class PaqueteTecnologicoTest {

    private static final Logger LOG = Logger.getLogger(PaqueteTecnologicoTest.class.getName());

    /**
     * Test para verificar que mostrarElemento muestre el nombre del paquete
     * y la suma del costo de sus elementos
     */
    @Test
    void mostrarElemento() {
        LOG.info("Inicio Test Mostrar Elemento");

        List<ElementoServicio> elementos = new ArrayList<>();

        elementos.add(new ElementoServicio() {
            @Override
            public String nombre() {
                return "Soporte";
            }

            @Override
            public double costo() {
                return 30000;
            }

            @Override
            public String mostrarElemento() {
                return "Soporte - Costo: $30000.0";
            }
        });

        elementos.add(new ElementoServicio() {
            @Override
            public String nombre() {
                return "Instalacion";
            }

            @Override
            public double costo() {
                return 20000;
            }

            @Override
            public String mostrarElemento() {
                return "Instalacion - Costo: $20000.0";
            }
        });

        PaqueteTecnologico paquete = new PaqueteTecnologico("Basico", elementos);
        assertEquals("Paquete Basico - Costo: $50000.0", paquete.mostrarElemento());

        LOG.info("Fin Test Mostrar Elemento");
    }
}