package be.ehb.pokemaper.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import be.ehb.pokemaper.R;
import be.ehb.pokemaper.databinding.FragmentMapBinding;
import be.ehb.pokemaper.databinding.FragmentPokemonBinding;

public class MapsFragment extends Fragment {

    private FragmentMapBinding binding;
    private GoogleMap mGoogleMap;

    private OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            mGoogleMap = googleMap;
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            LatLng coordGrûteMet =  new LatLng(50.846777, 4.352360);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(coordGrûteMet, 15);
            mGoogleMap.animateCamera(cameraUpdate);

            drawAnnotations();
        }
    };

    private void drawAnnotations() {
        LatLng coordGrûteMet =  new LatLng(50.846777, 4.352360);

        mGoogleMap.addMarker(new MarkerOptions()
                .position(coordGrûteMet)
                .title("Grote Markt")
                .icon(BitmapDescriptorFactory.defaultMarker())
        );
    }
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.mapView.getMapAsync(onMapReadyCallback);
        binding.mapView.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.mapView.onDestroy();
        binding = null;
    }
}
