package co.edu.uniquindio.poo.parcial2.model;

public class SoporteTecnico30Dias extends DecoradorServicio{
    /**
     * Metodo constructor de la clase SoporteTecnico
     * @param elementoServicio
     */
    public SoporteTecnico30Dias (ElementoServicio elementoServicio){
        super (elementoServicio);
    }
    /**
     * Sobreescritura del metodo nombre ( ) para la clase SoporteTecnico30Dias
     * @return
     */
    @Override
    public String nombre() {
        return elementoServicio.nombre() + " + soporte tecnico 30 dias";
    }
    /**
     * Sobreescritura del metodo costo ( ) para la clase SoporteTecnico30Dias
     * @return
     */
    @Override
    public double costo() {
        return elementoServicio.costo() + 50000;
    }
}
