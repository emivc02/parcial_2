package co.edu.uniquindio.poo.parcial2.model;

public abstract class DecoradorServicio implements ElementoServicio{
    protected final ElementoServicio elementoServicio;

    /**
     * Constructor de la clase DecoradorServicio
     * @param elementoServicio
     */
    public DecoradorServicio(ElementoServicio elementoServicio){
        this.elementoServicio = elementoServicio;
    }
    @Override
    public void mostrarElemento (String identacion){
        System.out.println(identacion + "-" + nombre() + " $" + costo());
    }
}
