package co.edu.uniquindio.poo.parcial2.model;

import java.util.List;
import java.util.ArrayList;

public class PaqueteTecnologico implements  ElementoServicio{

    private String nombre;
    private List<ElementoServicio> listaElementos;

    public PaqueteTecnologico(String nombre, List<ElementoServicio> listaElementos) {
        this.nombre = nombre;
        this.listaElementos = new ArrayList<>(listaElementos);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<ElementoServicio> getListaElementos() {
        return listaElementos;
    }

    @Override
    public String toString() {
        return "PaqueteTecnologico{" +
                "nombre='" + nombre + '\'' +
                ", listaElementos=" + listaElementos +
                '}';
    }

    @Override
    public String nombre() {
        return nombre;
    }

    @Override
    public double costo() {
        double total = 0;
        for (ElementoServicio e : listaElementos) {
            total += e.costo();
        }
        return total;
    }

    @Override
    public String mostrarElemento() {
        return "Paquete " + nombre + " - Costo: $" + costo();
    }

    public void agregarElemento(ElementoServicio elemento) {
        if (elemento == this || elemento instanceof PaqueteTecnologico
                && ((PaqueteTecnologico) elemento).contiene(this)) {
            throw new IllegalArgumentException("Un paquete no puede contenerse a sí mismo");
        }
        listaElementos.add(elemento);
    }

    public boolean contiene(ElementoServicio elemento) {
        for (ElementoServicio e : listaElementos) {
            if (e == elemento) return true;
            if (e instanceof PaqueteTecnologico && ((PaqueteTecnologico) e).contiene(elemento)) return true;
        }
        return false;
    }
}
