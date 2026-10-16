package co.edu.uniquindio.poo.parcial2.model;

public class MantenimientoCorrectivoFactory extends ServicioFactory{
    @Override
    public Servicio crearServicio(String codigo) {
        return new MantenimientoCorrectivo(codigo, "Mantenimiento correctivo",
                "Diagnóstico y reparación de equipos con fallas",
                120000);
    }
}
