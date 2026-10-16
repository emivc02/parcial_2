package co.edu.uniquindio.poo.parcial2.model;

public abstract class Servicio implements ElementoServicio{

    private String codigo;
    private String nombre;
    private String descripcion;
    private double costoBase;

    /**
     * Metodo constructor de la clase Servicio
     * @param codigo
     * @param nombre
     * @param descripcion
     * @param costoBase
     */
    public Servicio(String codigo, String nombre, String descripcion, double costoBase) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costoBase = costoBase;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCostoBase() {
        return costoBase;
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", costoBase=" + costoBase +
                '}';
    }

    @Override
    public String nombre() {
        return nombre;
    }

    @Override
    public double costo() {
        return costoBase;
    }

    /**
     * Metodo que muestra la informacion del servicio
     * @return
     */
    @Override
    public String mostrarElemento() {
        return codigo + " - " + nombre + " (" + descripcion + "): $" + costo();
    }
}