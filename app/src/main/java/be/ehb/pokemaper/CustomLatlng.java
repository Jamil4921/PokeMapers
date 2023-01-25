package be.ehb.pokemaper;

import com.google.android.gms.maps.model.LatLng;

public class CustomLatlng {

    public double latitude;
    public double longitude;

    public CustomLatlng() {
    }

    public CustomLatlng(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LatLng toLatLng() {
        return new LatLng(latitude, longitude);
    }
}
