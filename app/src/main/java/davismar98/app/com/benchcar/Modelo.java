package davismar98.app.com.benchcar;

/**
 * Created by DavidM on 13/04/2017.
 */

public class Modelo {

    public int idVehiculo;
    public String modelo;
    public String foto;

    @Override
    public String toString() {
        return modelo;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
