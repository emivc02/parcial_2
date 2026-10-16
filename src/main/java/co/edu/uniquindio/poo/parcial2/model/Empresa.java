package co.edu.uniquindio.poo.parcial2.model;

import java.util.ArrayList;
import java.util.List;

public class Empresa {

    private String nombre;
    private List<ElementoServicio> elementos = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    private List<Solicitud> solicitudes = new ArrayList<>();

    public Empresa(String nombre, List<ElementoServicio> elementos, List<Cliente> clientes, List<Solicitud> solicitudes) {
        this.nombre = nombre;
        this.elementos = elementos;
        this.clientes = clientes;
        this.solicitudes = solicitudes;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setElementos(List<ElementoServicio> elementos) {
        this.elementos = elementos;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public void setSolicitudes(List<Solicitud> solicitudes) {
        this.solicitudes = solicitudes;
    }


    @Override
    public String toString() {
        return "Empresa{" +
                "nombre='" + nombre + '\'' +
                ", elementos=" + elementos +
                ", clientes=" + clientes +
                ", solicitudes=" + solicitudes +
                '}';
    }



    public void agregarElemento(ElementoServicio elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("El elemento no puede ser nulo");
        }
        elementos.add(elemento);
    }

    public void agregarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        if (buscarCliente(cliente.getDocumento()) != null) {
            throw new IllegalArgumentException("Ya existe un cliente con documento " + cliente.getDocumento());
        }
        clientes.add(cliente);
    }

    public void agregarSolicitud(Solicitud solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula");
        }
        solicitudes.add(solicitud);
    }

}
