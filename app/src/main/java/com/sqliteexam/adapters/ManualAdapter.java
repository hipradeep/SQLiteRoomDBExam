package com.sqliteexam.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sqliteexam.R;
import com.sqliteexam.models.ManualCustomerModel;

import java.util.List;

public class ManualAdapter extends RecyclerView.Adapter<ManualAdapter.MyViewHolder> {
    List<ManualCustomerModel> list;
    Context context;


    public ManualAdapter(List<ManualCustomerModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_manual_item, parent, false);
        return new MyViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull ManualAdapter.MyViewHolder holder, int position) {

        holder.bind(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_customer_name,tv_customer_age,tv_active_inactive, tv_database_id;
        public MyViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            tv_customer_name=itemView.findViewById(R.id.tv_customer_name);
            tv_customer_age=itemView.findViewById(R.id.tv_customer_age);
            tv_active_inactive=itemView.findViewById(R.id.tv_active_inactive);
            tv_database_id=itemView.findViewById(R.id.tv_database_id);
        }

        public void bind(ManualCustomerModel manualCustomerModel) {

            tv_customer_name.setText(manualCustomerModel.getCustomerName());
            tv_customer_age.setText(""+manualCustomerModel.getCustomerAge());
            tv_database_id.setText(""+manualCustomerModel.getId());
            if (manualCustomerModel.isActive()){
                tv_active_inactive.setText("Active");
            }else {
                tv_active_inactive.setText("Inactive");
            }

        }
    }
}
