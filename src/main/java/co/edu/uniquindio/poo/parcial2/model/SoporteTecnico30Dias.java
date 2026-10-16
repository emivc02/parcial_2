package co.edu.uniquindio.poo.parcial2.model;

public class SoporteTecnico30Dias extends DecoradorServicio{
    public SoporteTecnico30Dias (ElementoServicio elementoServicio){
        super (elementoServicio);
    }
    @Override
    public String nombre() {
        return elementoServicio.nombre() + " + soporte tecnico 30 dias";
    }
    @Override
    public double costo() {
        return elementoServicio.costo() + 50000;
    }
}
