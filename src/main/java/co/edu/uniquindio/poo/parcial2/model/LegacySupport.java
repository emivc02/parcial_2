package co.edu.uniquindio.poo.parcial2.model;

public class LegacySupport {
    public String consultarTecnico (int codigoEspecialidad) {
        String respuesta = "";
        switch (codigoEspecialidad) {
            case 1:
                respuesta = "TEC-105|DISPONIBLE|30000";
                break;
            case 2:
                respuesta = "TEC-210|DISPONIBLE|25400";
                break;
            case 3:
                respuesta = "SIN_TECNICO|NO_DISPONIBLE|0";
                break;
            default:
                respuesta = "SIN_TECNICO|NO_DISPONIBLE|0";
                break;
        }
        return respuesta;
    }
}
