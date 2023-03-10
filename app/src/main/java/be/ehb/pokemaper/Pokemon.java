package be.ehb.pokemaper;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Pokemon {
    public String pokemon;
    public String address;
    public double lati;
    public double longti;




    public Pokemon() {
    }

    public Pokemon(String pokemon, double lati, double longti, String address) {
        this.pokemon = pokemon;
        this.lati = lati;
        this.longti = longti;
        this.address = address;
    }

    public String getPokemon() {
        return pokemon;
    }

    public void setPokemon(String pokemon) {
        this.pokemon = pokemon;
    }

    public double getLati() {
        return lati;
    }

    public void setLati(double lati) {
        this.lati = lati;
    }

    public double getLongti() {
        return longti;
    }

    public void setLongti(double longti) {
        this.longti = longti;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
