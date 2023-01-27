package be.ehb.pokemaper;

import com.google.android.gms.maps.model.LatLng;

public class Raid {
    public String pokemon;
    public String raidLevel;
    public double lati;
    public double longti;
    public String trainerCode;
    public String address;

    public Raid() {
    }

    public Raid(String pokemon, String raidLevel, double lati, double longti, String trainerCode, String address) {
        this.pokemon = pokemon;
        this.raidLevel = raidLevel;
        this.lati = lati;
        this.longti = longti;
        this.trainerCode = trainerCode;
        this.address = address;
    }

    public String getPokemon() {
        return pokemon;
    }

    public void setPokemon(String pokemon) {
        this.pokemon = pokemon;
    }

    public String getRaidLevel() {
        return raidLevel;
    }

    public void setRaidLevel(String raidLevel) {
        this.raidLevel = raidLevel;
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

    public String getTrainerCode() {
        return trainerCode;
    }

    public void setTrainerCode(String trainerCode) {
        this.trainerCode = trainerCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
