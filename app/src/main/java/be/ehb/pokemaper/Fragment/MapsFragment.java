package be.ehb.pokemaper.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import be.ehb.pokemaper.databinding.FragmentMapBinding;
import be.ehb.pokemaper.databinding.FragmentPokemonBinding;

public class MapsFragment extends Fragment{
    private FragmentMapBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
}
