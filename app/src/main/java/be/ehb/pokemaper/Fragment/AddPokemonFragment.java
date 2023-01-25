package be.ehb.pokemaper.Fragment;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import be.ehb.pokemaper.CustomLatlng;
import be.ehb.pokemaper.MainActivity;
import be.ehb.pokemaper.Pokemon;
import be.ehb.pokemaper.R;
import be.ehb.pokemaper.databinding.FragmentAddPokemonBinding;
import be.ehb.pokemaper.databinding.FragmentPokemonBinding;

public class AddPokemonFragment extends Fragment {

    private FragmentAddPokemonBinding binding;
    FusedLocationProviderClient fusedLocationProviderClient;
    PokemonFragment pokemonFragment = new PokemonFragment();
    private final static int REQUEST_CODE = 100;
    DatabaseReference databaseReference;
    EditText editText_Pokemon_name;
    Button btn_addPokemon;
    private double latitude, longitude;
    private CustomLatlng locations;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddPokemonBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        editText_Pokemon_name = root.findViewById(R.id.et_pokemonName);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Pokemons");

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());


        btn_addPokemon = root.findViewById(R.id.btn_addPokemon);
        btn_addPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertPokemonData();
            }
        });


        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
    }
    private void insertPokemonData() {
        String pokemonName = editText_Pokemon_name.getText().toString().trim();

        if (pokemonName.isEmpty()) {
            editText_Pokemon_name.setError("Please fill in pokemon name");
            editText_Pokemon_name.requestFocus();
            return;
        }

        if (pokemonName.length() < 2) {
            editText_Pokemon_name.setError("Pokemon name must be longer then 2 char");
            editText_Pokemon_name.requestFocus();
            return;
        }

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        askPermission();
                        Log.d("Location", "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());

                        Pokemon pokemon = new Pokemon(pokemonName, location.getLatitude(), location.getLongitude());
                        String key = databaseReference.push().getKey();
                        databaseReference.child(key).setValue(pokemon);
                        Toast.makeText(requireContext(), "Pokemon added", Toast.LENGTH_SHORT).show();
                        editText_Pokemon_name.setText("");
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.pokemon_container, new PokemonFragment());
                        fragmentTransaction.commit();
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
    }
    private void askPermission() {
        ActivityCompat.requestPermissions(requireActivity(),new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        } else {
            // Permission has already been granted, proceed with getting location
            getLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                return;
            }else{
                Toast.makeText(getContext(),"Request Failed", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
