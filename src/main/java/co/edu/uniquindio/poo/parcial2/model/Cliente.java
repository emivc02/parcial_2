package co.edu.uniquindio.poo.parcial2.model;

public class Cliente {
    private String documento;
    private String nombre;
    private String correo;

    public Cliente (String documento, String  nombre, String correo) {
        this.documento = documento;
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "documento='" + documento + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}
