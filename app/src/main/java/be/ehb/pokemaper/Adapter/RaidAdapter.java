package be.ehb.pokemaper.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import be.ehb.pokemaper.R;
import be.ehb.pokemaper.Raid;

public class RaidAdapter extends RecyclerView.Adapter<RaidAdapter.ViewHolder>{
    private ArrayList<Raid> raidList;

    public RaidAdapter(){
        this.raidList = new ArrayList<>();
    }

    public void addItems(List<Raid> raidList){
        this.raidList = new ArrayList<>(raidList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView pokemonRaidName;
        TextView raidLevel;
        TextView raidLocation;
        TextView raidAddress;
        TextView trainerCode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonRaidName = itemView.findViewById(R.id.rv_pokemon_raid_name);
            raidLevel = itemView.findViewById(R.id.rv_raid_level);
            raidLocation = itemView.findViewById(R.id.rv_raid_location);
            raidAddress = itemView.findViewById(R.id.rv_raid_address);
            trainerCode = itemView.findViewById(R.id.rv_trainer_code);
        }
    }

    @NonNull
    @Override
    public RaidAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_raid, parent, false);
        return new RaidAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RaidAdapter.ViewHolder holder, int position) {
        Raid raid = raidList.get(position);
        holder.pokemonRaidName.setText(raid.getPokemon());
        holder.raidLevel.setText(raid.getRaidLevel());
        holder.raidLocation.setText(Double.toString(raid.getLati())+","+Double.toString(raid.getLongti()));
        holder.raidAddress.setText(raid.getAddress());
        holder.trainerCode.setText(raid.getTrainerCode());
    }

    @Override
    public int getItemCount() {
        return raidList.size();
    }
}
