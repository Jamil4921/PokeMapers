package be.ehb.pokemaper.DataBase;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import be.ehb.pokemaper.Pokemon;
import be.ehb.pokemaper.Raid;

public class Database {
    private MutableLiveData<List<Pokemon>> allPokemon = new MutableLiveData<>();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Pokemons");
    private MutableLiveData<List<Raid>> allRaids = new MutableLiveData<>();
    private DatabaseReference databaseReferenceRaid = FirebaseDatabase.getInstance().getReference("Raids");

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

            }
        });
        return allPokemon;
    }

    public MutableLiveData<List<Raid>> getAllRaids(){
        databaseReferenceRaid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Raid> raidList = new ArrayList<>();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    Raid raid = postSnapshot.getValue(Raid.class);
                    raidList.add(raid);
                }
                allRaids.setValue(raidList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return allRaids;
    }
}
