package davismar98.app.com.benchcar;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DavidM on 11/04/2017.
 */

public class CarroLista implements Serializable{

    @SerializedName("idVehiculo") public int idVehiculo;
    @SerializedName("modelo") public String modelo;
    @SerializedName("año") public String año;
    @SerializedName("marca") public String marca;
    @SerializedName("foto") public String foto;
    @SerializedName("imagen") public String imagen;
    @SerializedName("logo") public String logo;
    @SerializedName("calificacion") public int calificacion;

    @Override
    public String toString() {
        return "CarroLista{" +
                "idVehiculo=" + idVehiculo +
                ", modelo='" + modelo + '\'' +
                ", año='" + año + '\'' +
                ", marca='" + marca + '\'' +
                ", foto='" + foto + '\'' +
                ", imagen='" + imagen + '\'' +
                ", logo='" + logo + '\'' +
                ", calificacion=" + calificacion +
                '}';
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

    public String getAño() {
        return año;
    }

    public void setAño(String año) {
        this.año = año;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }
}
