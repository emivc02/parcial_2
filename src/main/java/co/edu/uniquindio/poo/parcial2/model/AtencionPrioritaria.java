package co.edu.uniquindio.poo.parcial2.model;

public class AtencionPrioritaria extends  DecoradorServicio{

    /**
     * Metodo Constructor de la clase AtencionPrioritaria
     * @param elementoServicio
     */
    public AtencionPrioritaria (ElementoServicio elementoServicio) {
        super(elementoServicio);
    }

    /**
     * Sobreescritura del metodo nombre ( ) para la clase AtencionPrioritaria
     * @return
     */
    @Override
    public String nombre() {
        return elementoServicio.nombre() +" + atencion prioritaria";
    }
    /**
     * Sobreescritura del metodo costo ( ) para la clase AtencionPrioritaria
     * @return
     */
    @Override
    public double costo() {
        return elementoServicio.costo() + 35000;
    }
}
