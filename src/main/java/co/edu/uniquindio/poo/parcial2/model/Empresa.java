package co.edu.uniquindio.poo.parcial2.model;

import java.util.ArrayList;
import java.util.List;

public class Empresa {

    private String nombre;
    private List<ElementoServicio> elementos = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    private List<Solicitud> solicitudes = new ArrayList<>();

    /**
     * Metodo constructor de la clase Empresa
     * @param nombre
     * @param elementos
     * @param clientes
     * @param solicitudes
     */
    public Empresa(String nombre, List<ElementoServicio> elementos, List<Cliente> clientes, List<Solicitud> solicitudes) {
        this.nombre = nombre;
        this.elementos = elementos;
        this.clientes = clientes;
        this.solicitudes = solicitudes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<ElementoServicio> getElementos() {
        return elementos;
    }

    public void setElementos(List<ElementoServicio> elementos) {
        this.elementos = elementos;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Solicitud> getSolicitudes() {
        return solicitudes;
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

    /**
     * Metodo que agrega un elemento al catalogo
     * @param elemento
     */
    public void agregarElemento(ElementoServicio elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("El elemento no puede ser nulo");
        }
        elementos.add(elemento);
    }

    /**
     * Metodo que agrega un cliente, el documento no se puede repetir
     * @param cliente
     */
    public void agregarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        if (buscarCliente(cliente.getDocumento()) != null) {
            throw new IllegalArgumentException("Ya existe un cliente con documento " + cliente.getDocumento());
        }
        clientes.add(cliente);
    }

    /**
     * Metodo que agrega una solicitud, el codigo no se puede repetir
     * @param solicitud
     */
    public void agregarSolicitud(Solicitud solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula");
        }
        if (buscarSolicitud(solicitud.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe una solicitud con codigo " + solicitud.getCodigo());
        }
        solicitudes.add(solicitud);
    }

    /**
     * Metodo que busca un cliente por su documento
     * @param documento
     * @return el cliente o null si no existe
     */
    public Cliente buscarCliente(String documento) {
        for (Cliente cliente : clientes) {
            if (cliente.getDocumento().equals(documento)) {
                return cliente;
            }
        }
        return null;
    }

    /**
     * Metodo que busca una solicitud por su codigo
     * @param codigo
     * @return la solicitud o null si no existe
     */
    public Solicitud buscarSolicitud(String codigo) {
        for (Solicitud solicitud : solicitudes) {
            if (solicitud.getCodigo().equals(codigo)) {
                return solicitud;
            }
        }
        return null;
    }

}