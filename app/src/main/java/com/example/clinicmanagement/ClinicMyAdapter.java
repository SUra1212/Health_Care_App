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

public class ClinicMyAdapter extends RecyclerView.Adapter <ClinicMyAdapter.MyViewHolder> {
    private ShowClinicChannelingActivity activity;
    private List<ClinicModel> mList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public ClinicMyAdapter(ShowClinicChannelingActivity activity , List<ClinicModel> mList){
        this.activity = activity;
        this.mList = mList;
    }

    public void updateData(int position){
        ClinicModel item = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uId", item.getId());
        bundle.putString("uName", item.getName());
        bundle.putString("uAge", item.getAge());
        bundle.putString("uGender", item.getGender());
        bundle.putString("uAddress", item.getAddress());
        bundle.putString("uDiagnosis", item.getDiagnosis());
        bundle.putString("uPhone", item.getPhone());
        bundle.putString("uPhone2", item.getPhone2());

        Intent intent = new Intent(activity , ClinicChannelingMainActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void deleteData(int position) {
        ClinicModel item = mList.get(position);
        db.collection("clinic-channeling").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            notifyRemoved(position);
                            Toast.makeText(activity, "Data Deleted Successfully!", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(activity, "Error : " +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
        View v = LayoutInflater.from(activity).inflate(R.layout.itemc , parent , false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(mList.get(position).getName());
        holder.age.setText(mList.get(position).getAge());
        holder.gender.setText(mList.get(position).getGender());
        holder.address.setText(mList.get(position).getAddress());
        holder.diagnosis.setText(mList.get(position).getDiagnosis());
        holder.phone.setText(mList.get(position).getPhone());
        holder.phone2.setText(mList.get(position).getPhone2());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, age, gender ,address, diagnosis, phone, phone2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.cname);
            age = itemView.findViewById(R.id.cage);
            gender = itemView.findViewById(R.id.cgender);
            address = itemView.findViewById(R.id.caddress);
            diagnosis = itemView.findViewById(R.id.cdiagnosis);
            phone = itemView.findViewById(R.id.cphone);
            phone2 = itemView.findViewById(R.id.cphone2);
        }
    }
}
