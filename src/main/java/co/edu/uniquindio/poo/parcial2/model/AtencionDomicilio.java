package co.edu.uniquindio.poo.parcial2.model;

public class AtencionDomicilio extends  DecoradorServicio{

    public AtencionDomicilio(ElementoServicio elementoServicio) {
        super(elementoServicio);
    }
    @Override
    public String nombre() {
        return elementoServicio.nombre() + " + atencion a domicilio";
    }
    @Override
    public double costo() {
        return elementoServicio.costo() +25000;
    }

}
