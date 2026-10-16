package co.edu.uniquindio.poo.parcial2.model;

import java.time.LocalDate;
import java.util.List;

public class Solicitud {

    private String codigo;
    private LocalDate fechaCreacion;
    private Cliente cliente;
    private List<ElementoServicio> listaElementos;
    private double costoTotal;
    private boolean presencial;
    private String especialidad;
    private boolean confirmada;
    private LocalDate fechaProgramada;
    private String observaciones;
    private NivelPrioridad prioridad;

    /**
     * Metodo constructor que permite crear solicitudes de forma flexible
     * @param builder
     */
    public Solicitud(Builder builder) {
        this.codigo = builder.codigo;
        this.fechaCreacion = builder.fechaCreacion;
        this.cliente = builder.cliente;
        this.listaElementos = builder.listaElementos;
        this.presencial = builder.presencial;
        this.especialidad = builder.especialidad;
        this.fechaProgramada = builder.fechaProgramada;
        this.observaciones = builder.observaciones;
        this.prioridad = builder.prioridad;
        this.confirmada = false;
    }

    /**
     * Metodo que asigna el costo total de la solicitud
     * @param costoTotal de la solicitud
     */
    public void costoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    /**
     * Metodo que asigna el estado de confirmacion de la solicitud
     * @param confirmada true si la solicitud fue confirmada
     */
    public void confirmada(boolean confirmada) {
        this.confirmada = confirmada;
    }

    //Getter
    public String getCodigo() {
        return codigo;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ElementoServicio> getListaElementos() {
        return listaElementos;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public boolean isPresencial() {
        return presencial;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public boolean isConfirmada() {
        return confirmada;
    }

    public LocalDate getFechaProgramada() {
        return fechaProgramada;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public NivelPrioridad getPrioridad() {
        return prioridad;
    }

    @Override
    public String toString() {
        return "Solicitud{" +
                "codigo='" + codigo + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", cliente=" + cliente +
                ", presencial=" + presencial +
                ", especialidad='" + especialidad + '\'' +
                ", confirmada=" + confirmada +
                ", costoTotal=" + costoTotal +
                ", fechaProgramada=" + fechaProgramada +
                ", observaciones='" + observaciones + '\'' +
                ", prioridad=" + prioridad +
                '}';
    }

    public static class Builder {
        private String codigo;
        private LocalDate fechaCreacion;
        private Cliente cliente;
        private List<ElementoServicio> listaElementos;
        private boolean presencial;
        private String especialidad;
        private LocalDate fechaProgramada;
        private String observaciones;
        private NivelPrioridad prioridad;

        /**
         * Metodo que permite construir el codigo de la solicitud
         * @param codigo de la solicitud
         * @return Builder
         */
        public Builder codigo(String codigo) {
            this.codigo = codigo;
            return this;
        }

        public Builder fechaCreacion(LocalDate fechaCreacion) {
            this.fechaCreacion = fechaCreacion;
            return this;
        }

        public Builder cliente(Cliente cliente) {
            this.cliente = cliente;
            return this;
        }

        public Builder listaElementos(List<ElementoServicio> listaElementos) {
            this.listaElementos = listaElementos;
            return this;
        }

        public Builder presencial(boolean presencial) {
            this.presencial = presencial;
            return this;
        }

        public Builder especialidad(String especialidad) {
            this.especialidad = especialidad;
            return this;
        }

        public Builder fechaProgramada(LocalDate fechaProgramada) {
            this.fechaProgramada = fechaProgramada;
            return this;
        }

        public Builder observaciones(String observaciones) {
            this.observaciones = observaciones;
            return this;
        }

        public Builder prioridad(NivelPrioridad prioridad) {
            this.prioridad = prioridad;
            return this;
        }

        /**
         * Metodo que valida los datos obligatorios y construye la solicitud
         * @return Solicitud
         */
        public Solicitud build() {
            if (codigo == null || codigo.isEmpty()) {
                throw new IllegalArgumentException("El codigo es obligatorio");
            }
            if (fechaCreacion == null) {
                throw new IllegalArgumentException("La fecha de creacion es obligatoria");
            }
            if (cliente == null) {
                throw new IllegalArgumentException("El cliente es obligatorio");
            }
            if (listaElementos == null || listaElementos.isEmpty()) {
                throw new IllegalArgumentException("Debe tener al menos un elemento contratado");
            }
            if (presencial && (especialidad == null || especialidad.isEmpty())) {
                throw new IllegalArgumentException("La especialidad es obligatoria si la atencion es presencial");
            }
            return new Solicitud(this);
        }
    }
}