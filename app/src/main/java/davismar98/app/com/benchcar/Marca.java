package davismar98.app.com.benchcar;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DavidM on 13/04/2017.
 */

public class Marca implements Serializable {

    @SerializedName("idMarca") public  int idMarca;
    @SerializedName("marca") public String marca;
    @SerializedName("logo") public String logo;

    @Override
    public String toString() {
        return "Marca{" +
                "idMarca=" + idMarca +
                ", marca='" + marca + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }

    public Marca(int idMarca, String marca, String logo) {
        this.idMarca = idMarca;
        this.marca = marca;
        this.logo = logo;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
