package co.edu.uniquindio.poo.parcial2.Services;

import co.edu.uniquindio.poo.parcial2.model.ElementoServicio;
import co.edu.uniquindio.poo.parcial2.model.ProveedorTecnicos;
import co.edu.uniquindio.poo.parcial2.model.Solicitud;
import co.edu.uniquindio.poo.parcial2.model.Tecnico;

public class SolicitudService {

    private ProveedorTecnicos proveedorTecnicos;

    /**
     * Metodo constructor de la clase SolicitudService
     * @param proveedorTecnicos
     */
    public SolicitudService(ProveedorTecnicos proveedorTecnicos){
        this.proveedorTecnicos = proveedorTecnicos;
    }

    /**
     * Metodo que calcula el costo total de la solicitud
     * @param solicitud
     * @return
     */
    public double calcularCostoTotal(Solicitud solicitud){
        double total = 0;
        for(ElementoServicio elemento : solicitud.getListaElementos()){
            total += elemento.costo();
        }
        if(solicitud.isPresencial()){
            Tecnico tecnico = proveedorTecnicos.consultarTecnico(solicitud.getEspecialidad());
            if(tecnico.isDisponible()){
                total += tecnico.getCostoDesplazamiento();
            }
        }
        return total;
    }

    /**
     * Metodo que confirma la solicitud,si ees presencial y no hay tecnico, la solicitud no se confirma
     * @param solicitud
     * @return
     */
    public boolean confirmarSolicitud(Solicitud solicitud){
        if(solicitud.isPresencial()){
            Tecnico tecnico = proveedorTecnicos.consultarTecnico(solicitud.getEspecialidad());
            if(!tecnico.isDisponible()){
                System.out.println("No hay tecnicos disponibles para " + solicitud.getEspecialidad()
                        + ", la solicitud " + solicitud.getCodigo() + " no se puede confirmar");
                return false;
            }
        }
        solicitud.costoTotal(calcularCostoTotal(solicitud));
        solicitud.confirmada(true);
        System.out.println("Solicitud " + solicitud.getCodigo() + " confirmada. Costo total: $" + solicitud.getCostoTotal());
        return true;
    }

}