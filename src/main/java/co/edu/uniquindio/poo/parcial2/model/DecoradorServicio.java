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

    /**
     * Metodo que muestra el nombre y el costo del elemento decorado
     * @return
     */
    @Override
    public String mostrarElemento (){
        return nombre() + " - Costo: $" + costo();
    }
}