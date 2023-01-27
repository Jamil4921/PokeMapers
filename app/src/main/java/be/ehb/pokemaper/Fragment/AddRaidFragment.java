package be.ehb.pokemaper.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import be.ehb.pokemaper.MainActivity;
import be.ehb.pokemaper.R;
import be.ehb.pokemaper.Raid;
import be.ehb.pokemaper.databinding.FragmentAddRaidBinding;

public class AddRaidFragment extends Fragment {
    private FragmentAddRaidBinding binding;
    FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE = 100;
    DatabaseReference databaseReference;
    EditText editText_PokemonName, editText_RaidLevel, editText_TrainerCode;
    Button btn_addRaid, btn_raidCancel;
    private String address;
    private double latitude, longitude;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddRaidBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Raids");
        editText_PokemonName = root.findViewById(R.id.et_pokemonNameRaid);
        editText_RaidLevel = root.findViewById(R.id.et_raidLevel);
        editText_TrainerCode = root.findViewById(R.id.et_TrainerCode);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

        btn_raidCancel = root.findViewById(R.id.btn_raid_cancel);
        btn_raidCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btn_addRaid = root.findViewById(R.id.btn_addRaid);
        btn_addRaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertRaid();
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

    private void insertRaid() {
        String pokemonName = editText_PokemonName.getText().toString().trim();
        String raidLevel = editText_RaidLevel.getText().toString().trim();
        String trainerCode = editText_TrainerCode.getText().toString().trim();

        if(pokemonName.isEmpty()){
            editText_PokemonName.setError("Please fill in pokemon name");
            editText_PokemonName.requestFocus();
            return;
        }

        if(pokemonName.length() < 2){
            editText_PokemonName.setError("Pokemon mane must be longer the 2 char ");
            editText_PokemonName.requestFocus();
            return;
        }

        if(raidLevel.isEmpty()){
            editText_RaidLevel.setError("Raid level cannot be empty");
            editText_RaidLevel.requestFocus();
            return;
        }

        if(trainerCode.isEmpty()){
            editText_TrainerCode.setError("Trainercode cannot be empty");
            editText_TrainerCode.requestFocus();
            return;
        }

        if(trainerCode.length() > 12){
            editText_TrainerCode.setError("Trainercode cannot be longer then 12 char");
            editText_TrainerCode.requestFocus();
            return;
        }

        if(ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location !=null){
                        askPermission();
                        Log.d("Location", "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            address = addresses.get(0).getAddressLine(0).toString();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Raid raid = new Raid(pokemonName,raidLevel,location.getLatitude(),location.getLongitude(),trainerCode,address);
                        String key = databaseReference.push().getKey();
                        databaseReference.child(key).setValue(raid);
                        Toast.makeText(requireContext(),"Raid Added",Toast.LENGTH_LONG).show();
                        editText_PokemonName.setText("");
                        editText_RaidLevel.setText("");
                        editText_TrainerCode.setText("");
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.raid_container,new RaidFragment());
                        fragmentTransaction.commit();

                    }
                }
            });
        }else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(requireActivity(),new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
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
