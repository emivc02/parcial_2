package co.edu.uniquindio.poo.parcial2.model;

public class MantenimientoPreventivoFactory extends ServicioFactory {
    @Override
    public Servicio crearServicio(String codigo) {
        return new MantenimientoPreventivo(codigo, "Mantenimiento preventivo",
                "Revisión y limpieza periódica de equipos para evitar fallas",
                80000);
    }
}
