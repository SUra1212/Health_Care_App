package com.example.clinicmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewPatientChanneling extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore db;

    private ChannelingMyAdapter adapter;
    private List<ChannelingModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_channeling);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        adapter = new ChannelingMyAdapter(this, list);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(new ChannelingTouchHeper(adapter));
        touchHelper.attachToRecyclerView(recyclerView);

        showData();

    }

    public void showData(){
        db.collection("patient-channeling").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for(DocumentSnapshot snapshot  :task.getResult()) {
                            ChannelingModel model = new ChannelingModel(snapshot.getString("id") ,
                                    snapshot.getString("country"),
                                    snapshot.getString("name"),
                                    snapshot.getString("phone"),
                                    snapshot.getString("email"),
                                    snapshot.getString("address"));
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ViewPatientChanneling.this, "Oops! Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}