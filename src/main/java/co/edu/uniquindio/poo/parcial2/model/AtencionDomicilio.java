package co.edu.uniquindio.poo.parcial2.model;

public class AtencionDomicilio extends  DecoradorServicio{

    /**
     * Metodo constructor de la clase AtencionDomicilio
     * @param elementoServicio
     */
    public AtencionDomicilio(ElementoServicio elementoServicio) {
        super(elementoServicio);
    }
    /**
     * Sobreescritura del metodo nombre ( ) para la clase AtencioDomicilio
     * @return
     */
    @Override
    public String nombre() {
        return elementoServicio.nombre() + " + atencion a domicilio";
    }
    /**
     * Sobreescritura del metodo costo ( ) para la clase AtencioDomicilio
     * @return
     */
    @Override
    public double costo() {
        return elementoServicio.costo() +25000;
    }

}
