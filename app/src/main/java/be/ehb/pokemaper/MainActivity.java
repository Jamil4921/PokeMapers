package be.ehb.pokemaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import be.ehb.pokemaper.Fragment.MapsFragment;
import be.ehb.pokemaper.Fragment.PokemonFragment;
import be.ehb.pokemaper.Fragment.RaidFragment;

public class MainActivity extends AppCompatActivity {

    public FloatingActionButton addPokemon;
    private FirebaseAuth mAuth;
    private GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();



        Button btnSignOut = findViewById(R.id.btnSignOut);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        

        BottomNavigationView nav_bottom = findViewById(R.id.bottomNav);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new MapsFragment()).commit();

        nav_bottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navPokemon:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new PokemonFragment()).commit();
                        return true;
                    case R.id.navRaids:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new RaidFragment()).commit();
                        return true;
                    case R.id.navMaps:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new MapsFragment()).commit();
                        return true;
                }
                return false;
            }
        });

    }


    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }


}