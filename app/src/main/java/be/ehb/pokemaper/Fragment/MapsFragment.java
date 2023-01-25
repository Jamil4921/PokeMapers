package be.ehb.pokemaper.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import be.ehb.pokemaper.DataBase.Database;
import be.ehb.pokemaper.Pokemon;
import be.ehb.pokemaper.R;
import be.ehb.pokemaper.ViewModel.PokemonViewModel;
import be.ehb.pokemaper.databinding.FragmentMapBinding;
import be.ehb.pokemaper.databinding.FragmentPokemonBinding;

public class MapsFragment extends Fragment {

    private FragmentMapBinding binding;
    private GoogleMap mGoogleMap;
    private SupportMapFragment supportMapFragment;
    private PokemonViewModel pokemonViewModel;
    private Database database = new Database();
    private FusedLocationProviderClient fusedLocationClient;



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

        binding.mapView.onCreate(savedInstanceState);
        supportMapFragment = SupportMapFragment.newInstance();
        getChildFragmentManager().beginTransaction().replace(R.id.mapView, supportMapFragment).commit();
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                database.getAllPokemon().observe(getViewLifecycleOwner(), new Observer<List<Pokemon>>() {
                    @Override
                    public void onChanged(List<Pokemon> pokemonList) {
                        for(Pokemon pokemon : pokemonList){
                            googleMap.addMarker(new MarkerOptions().position(new LatLng(pokemon.getLati(),pokemon.getLongti())).title("Pokemon spotting : " + pokemon.getPokemon()));
                        }
                    }
                });
            }
        });

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 69;
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return;
        }



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
