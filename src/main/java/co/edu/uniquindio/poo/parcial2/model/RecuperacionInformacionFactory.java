package co.edu.uniquindio.poo.parcial2.model;

public class RecuperacionInformacionFactory extends ServicioFactory{
    @Override
    public Servicio crearServicio(String codigo) {
        return new RecuperacionInformacion(codigo, "Recuperación de información",
                "Recuperación de datos perdidos o dañados",
                150000);
    }
}
