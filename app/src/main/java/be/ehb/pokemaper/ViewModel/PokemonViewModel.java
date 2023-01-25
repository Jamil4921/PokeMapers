package be.ehb.pokemaper.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import be.ehb.pokemaper.Pokemon;

public class PokemonViewModel extends AndroidViewModel {

    DatabaseReference databaseReference;
    private MutableLiveData<List<Pokemon>> allPokemon;

    public PokemonViewModel(@NonNull Application application) {
        super(application);
        allPokemon = new MutableLiveData<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Pokemons");

    }

    public MutableLiveData<List<Pokemon>> getAllPokemon() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Pokemon> pokemonList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Pokemon pokemon = postSnapshot.getValue(Pokemon.class);
                    pokemonList.add(pokemon);

                }
                allPokemon.setValue(pokemonList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error here
            }
        });
        System.out.println(allPokemon.toString());
        return allPokemon;
    }


}
