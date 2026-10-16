package co.edu.uniquindio.poo.parcial2.model;

public class LegacySupportAdapter implements  ProveedorTecnicos{
    private LegacySupport legacySupport;

    public LegacySupportAdapter( LegacySupport legacySupport){
        this.legacySupport = legacySupport;
    }
    @Override
    public Tecnico consultarTecnico (String especialidad){
        int codigo = obtenerCodigoEspecialdiad(especialidad);
        String respuesta = legacySupport.consultarTecnico(codigo);
        String[] partes  = respuesta.split("\\|");
        String idTecnico = partes[0];
        boolean disponible = partes [1].equals("DISPONIBLE");
        double costoDesplazamiento = Double.parseDouble (partes[2]);
        return new Tecnico (idTecnico, disponible, costoDesplazamiento);
    }

    private int obtenerCodigoEspecialidad (String especialidad){
        int codigo;
        switch (especialidad.toUpperCase()){
            case "MANTENIMIENTO":
                codigo = 1;
                break;
            case "REDES":
                codigo = 2;
                break;
            case "RECUPERACION":
                codigo = 3;
                break;
            default:
                codigo = 0;
                break;
        }
        return codigo;
    }
}
