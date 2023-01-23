package be.ehb.pokemaper.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import be.ehb.pokemaper.databinding.FragmentPokemonBinding;
import be.ehb.pokemaper.databinding.FragmentRaidsBinding;

public class RaidFragment extends Fragment {
    private FragmentRaidsBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRaidsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
}
