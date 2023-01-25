package be.ehb.pokemaper.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import be.ehb.pokemaper.Pokemon;
import be.ehb.pokemaper.R;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder>{
    private ArrayList<Pokemon> pokemonList;

    public PokemonAdapter (){
        pokemonList = new ArrayList<>();
    }

    public void addItems(List<Pokemon> pokemonList){
        this.pokemonList = new ArrayList<>(pokemonList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView pokemonName;
        public ViewHolder(View view){
            super(view);

            pokemonName = view.findViewById(R.id.rv_pokemonName);

        }


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Pokemon pokemons = pokemonList.get(position);
        holder.pokemonName.setText(pokemons.getPokemon());

    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }
}
