package be.ehb.pokemaper.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import be.ehb.pokemaper.Raid;

public class RaidViewModel extends AndroidViewModel {
    DatabaseReference databaseReferenceRaid;
    private MutableLiveData<List<Raid>> allRaids;
    public RaidViewModel(@NonNull Application application) {
        super(application);
        allRaids = new MutableLiveData<>();
        databaseReferenceRaid = FirebaseDatabase.getInstance().getReference("Raids");
    }

    public MutableLiveData<List<Raid>> getAllRaids(){
        databaseReferenceRaid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Raid> raidList = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    Raid raid = postSnapshot.getValue(Raid.class);
                    raidList.add(raid);
                }
                allRaids.setValue(raidList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        System.out.println(allRaids.toString());
        return allRaids;
    }
}
