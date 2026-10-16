package co.edu.uniquindio.poo.parcial2.model;

public class InstalacionRedFactory extends ServicioFactory{
    @Override
    public Servicio crearServicio(String codigo) {
        return new InstalacionRed(codigo, "Instalación de redes",
                "Instalación y configuración de redes de datos",
                180000);
    }
}
