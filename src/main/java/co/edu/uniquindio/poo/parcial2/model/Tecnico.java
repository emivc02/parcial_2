package co.edu.uniquindio.poo.parcial2.model;

public class Tecnico {
    private String idTecnico;
    private boolean disponible;
    private double costoDesplazamiento;

    /**
     * Metodo Constructor de la clase Tecnico
     * @param idTecnico
     * @param disponible
     * @param costoDesplazamiento
     */
    public Tecnico (String idTecnico, boolean disponible, double costoDesplazamiento){
        this.idTecnico = idTecnico;
        this.disponible = disponible;
        this.costoDesplazamiento = costoDesplazamiento;
    }
/*
Getter y Setter
 */
    public String getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(String idTecnico) {
        this.idTecnico = idTecnico;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public double getCostoDesplazamiento() {
        return costoDesplazamiento;
    }

    public void setCostoDesplazamiento(double costoDesplazamiento) {
        this.costoDesplazamiento = costoDesplazamiento;
    }

    /**
     * Metodo toString de la clase Tecnico
     * @return
     */
    @Override
    public String toString() {
        return "Tecnico{" +
                "idTecnico='" + idTecnico + '\'' +
                ", disponible=" + disponible +
                ", costoDesplazamiento=" + costoDesplazamiento +
                '}';
    }
}