package com.example.siemas.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsbs;
import com.example.siemas.RoomDatabase.Entities.Dsrt;

import java.util.ArrayList;
import java.util.List;

public class Table2Adapter extends RecyclerView.Adapter<Table2Adapter.ViewHolder> {
    private List<Dsrt> dsrtList = new ArrayList<>();


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.table2_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (dsrtList != null && dsrtList.size()>0){
            Dsrt currentDsrt = dsrtList.get(position);

            if (currentDsrt.getStatus_pencacahan()<1){
                holder.tvIdBs.setText(currentDsrt.getId_bs());
                holder.tvNks.setText(currentDsrt.getNks());
                holder.tvNuRt.setText(String.valueOf(currentDsrt.getNu_rt()));
                if (!currentDsrt.getNama_krt2().isEmpty() && !currentDsrt.getNama_krt2().equals("null")){
                    holder.tvNamaKrt.setText(currentDsrt.getNama_krt2());
                } else {
                    holder.tvNamaKrt.setText(currentDsrt.getNama_krt());
                }
            } else {
                holder.tvIdBs.setVisibility(View.GONE);
                holder.tvNks.setVisibility(View.GONE);
                holder.tvNuRt.setVisibility(View.GONE);
                holder.tvNamaKrt.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dsrtList.size();
    }

    public void setListDsrt(List<Dsrt> dsrtList) {
        this.dsrtList = dsrtList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIdBs, tvNks, tvNuRt, tvNamaKrt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdBs = itemView.findViewById(R.id.nbs);
            tvNks = itemView.findViewById(R.id.nks);
            tvNuRt = itemView.findViewById(R.id.nuRt);
            tvNamaKrt = itemView.findViewById(R.id.namaKrt);

        }
    }
}
