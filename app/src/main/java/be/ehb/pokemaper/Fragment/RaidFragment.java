package be.ehb.pokemaper.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import be.ehb.pokemaper.Adapter.RaidAdapter;
import be.ehb.pokemaper.R;
import be.ehb.pokemaper.Raid;
import be.ehb.pokemaper.ViewModel.RaidViewModel;
import be.ehb.pokemaper.databinding.FragmentPokemonBinding;
import be.ehb.pokemaper.databinding.FragmentRaidsBinding;

public class RaidFragment extends Fragment {
    private FragmentRaidsBinding binding;
    private FloatingActionButton btn_add_raid;
    private SearchView search_view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRaidsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btn_add_raid = root.findViewById(R.id.btn_add_raid);
        btn_add_raid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddRaidFragment addRaidFragment = new AddRaidFragment();
                FragmentManager fragmentManager = getChildFragmentManager();
                fragmentManager.popBackStack();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.raid_container, addRaidFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        RaidViewModel raidViewModel = new ViewModelProvider(getActivity()).get(RaidViewModel.class);
        RaidAdapter raidAdapter = new RaidAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rvRaidList.setAdapter(raidAdapter);
        binding.rvRaidList.setLayoutManager(layoutManager);
        raidViewModel.getAllRaids().observe(getViewLifecycleOwner(), new Observer<List<Raid>>() {
            @Override
            public void onChanged(List<Raid> raidList) {
                raidAdapter.addItems(raidList);
            }
        });

        return root;
    }
}
