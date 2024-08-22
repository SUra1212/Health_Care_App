package com.example.clinicmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ChannelingMyAdapter extends RecyclerView.Adapter<ChannelingMyAdapter.MyViewHolder> {
    private ViewPatientChanneling activity;
    private List<ChannelingModel> mList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ChannelingMyAdapter(ViewPatientChanneling activity, List<ChannelingModel> mList) {
        this.activity = activity;
        this.mList = mList;
    }

    public void updateData(int position) {
        ChannelingModel item = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uId", item.getId());
        bundle.putString("uCountry", item.getCountry());
        bundle.putString("uName", item.getName());
        bundle.putString("uPhone", item.getPhone());
        bundle.putString("uEmail", item.getEmail());
        bundle.putString("uAddress", item.getAddress());
        Intent intent = new Intent(activity, PatientChanneling.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void deleteData(int position){
        ChannelingModel item = mList.get(position);
        db.collection("patient-channeling").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            notifyItemRemoved(position);
                            Toast.makeText(activity, "Data Deleted!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(activity, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void notifyRemoved(int position){
        mList.remove(position);
        notifyItemRemoved(position);
        activity.showData();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.itemp, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.country.setText(mList.get(position).getCountry());
        holder.name.setText(mList.get(position).getName());
        holder.phone.setText(mList.get(position).getPhone());
        holder.email.setText(mList.get(position).getEmail());
        holder.address.setText(mList.get(position).getAddress());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView country, name, phone, email, address;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            country = itemView.findViewById(R.id.pcountry);
            name = itemView.findViewById(R.id.pname);
            phone = itemView.findViewById(R.id.pphone);
            email = itemView.findViewById(R.id.pemail);
            address = itemView.findViewById(R.id.paddress);
        }
    }
}
