package be.ehb.pokemaper.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Objects;

import be.ehb.pokemaper.DataBase.Database;
import be.ehb.pokemaper.Pokemon;
import be.ehb.pokemaper.R;
import be.ehb.pokemaper.Raid;
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
                        for (Pokemon pokemon : pokemonList) {

                            googleMap.addMarker(new MarkerOptions().position(new LatLng(pokemon.getLati(), pokemon.getLongti())).title("Pokemon spotting : " + pokemon.getPokemon()).icon(pokeBall(getActivity(), R.drawable.ic_baseline_catching_pokemon_24)));
                        }


                    }
                });


            }


        });

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                database.getAllRaids().observe(getViewLifecycleOwner(), new Observer<List<Raid>>() {
                    @Override
                    public void onChanged(List<Raid> raidList) {
                        for(Raid raid : raidList){
                            googleMap.addMarker(new MarkerOptions().position(new LatLng(raid.getLati(),raid.getLongti())).title("Raid Spotting : " + raid.getPokemon()).snippet("Raid level : " + raid.getRaidLevel() + " Trainer Code : " + raid.getTrainerCode()).icon(pokeBall(getActivity(),R.drawable.ic_baseline_fort_24)));

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

    private BitmapDescriptor pokeBall(Context context, int vectorId){
        Drawable vectorDrawble = ContextCompat.getDrawable(context, vectorId);
        vectorDrawble.setBounds(0,0,vectorDrawble.getIntrinsicWidth(), vectorDrawble.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawble.getIntrinsicWidth(),vectorDrawble.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawble.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
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
