package com.example.clinicmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ShowTests activity;
    private List<Model> mList;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    public MyAdapter(ShowTests activity, List<Model> mList){
        this.activity=activity;
        this.mList=mList;
    }

    public void updateData(int position){
        Model item = mList.get(position);
        Bundle bundle=new Bundle();
        bundle.putString("uId",item.getId());
        bundle.putString("uAddress",item.getAddress());
        bundle.putString("uPhone",item.getPhone());
        bundle.putString("uEmail",item.getEmail());
        bundle.putString("testMode",item.getTestMode());
        bundle.putString("paymentMethod",item.getPaymentMethod());
        Intent intent=new Intent(activity, PatientDetails.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }
    
    public void deleteData(int position){
        Model item=mList.get(position);
//        db.collection("tests").document(item.getId()).delete()
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){
//                            notifyRemoved(position);
//                            Toast.makeText(activity, "Data deleted", Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(activity, "Errow !!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
        Intent intent=new Intent(activity, ReviewTest.class);
        intent.putExtra("uId",item.getId());
        activity.startActivity(intent);
        
    }

    private void notifyRemoved(int position){
        mList.remove(position);
        notifyItemRemoved(position);
        activity.showData();
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(activity).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.address.setText(mList.get(position).getAddress());
        holder.phone.setText(mList.get(position).getPhone());
        holder.email.setText(mList.get(position).getEmail());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //MyView holder class
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView address,email,phone;


        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            address=itemView.findViewById(R.id.tv_addressView);
            email=itemView.findViewById(R.id.tv_emailView);
            phone=itemView.findViewById(R.id.tv_phoneView);
        }
    }



}
