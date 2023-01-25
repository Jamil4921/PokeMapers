package be.ehb.pokemaper.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import be.ehb.pokemaper.Adapter.PokemonAdapter;
import be.ehb.pokemaper.Pokemon;
import be.ehb.pokemaper.R;
import be.ehb.pokemaper.ViewModel.PokemonViewModel;
import be.ehb.pokemaper.databinding.FragmentPokemonBinding;

public class PokemonFragment extends Fragment {
    private FragmentPokemonBinding binding;
    private FloatingActionButton btn_add_pokemon;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPokemonBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btn_add_pokemon = root.findViewById(R.id.btn_add_pokemon);
        btn_add_pokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPokemonFragment addPokemonFragment = new AddPokemonFragment();
                FragmentManager fragmentManager = getChildFragmentManager();
                fragmentManager.popBackStack();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.pokemon_container, addPokemonFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }

        });
        PokemonViewModel pokemonViewModel = new ViewModelProvider(getActivity()).get(PokemonViewModel.class);
        PokemonAdapter pokemonAdapter = new PokemonAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rvPokemonList.setAdapter(pokemonAdapter);
        binding.rvPokemonList.setLayoutManager(layoutManager);

        pokemonViewModel.getAllPokemon().observe(getViewLifecycleOwner(), new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemonList) {
                pokemonAdapter.addItems(pokemonList);
            }
        });
        return root;



    }
}
