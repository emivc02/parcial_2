package co.edu.uniquindio.poo.parcial2.model;

public class AtencionPrioritaria extends  DecoradorServicio{

    public AtencionPrioritaria (ElementoServicio elementoServicio) {
        super(elementoServicio);
    }
    @Override
    public String nombre() {
        return elementoServicio.nombre() +" + atencion prioritaria";
    }
    @Override
    public double costo() {
        return elementoServicio.costo() + 35000;
    }
}
