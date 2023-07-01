package com.example.siemas.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsrt;

import java.util.ArrayList;
import java.util.List;

public class Table3PmlAdapter extends RecyclerView.Adapter<Table3PmlAdapter.ViewHolder> {
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

            if (currentDsrt.getStatus_pencacahan()<5){
                holder.tvIdBs.setText("16"+currentDsrt.getKd_kab()+ currentDsrt.getKd_kec()+currentDsrt.getKd_desa()+currentDsrt.getKd_bs());
                holder.tvNks.setText(currentDsrt.getNks());
                holder.tvNuRt.setText(String.valueOf(currentDsrt.getNu_rt()));
                if (!currentDsrt.getNama_krt_cacah().isEmpty() && !currentDsrt.getNama_krt_cacah().equals("null")){
                    holder.tvNamaKrt.setText(currentDsrt.getNama_krt_cacah());
                } else {
                    holder.tvNamaKrt.setText(currentDsrt.getNama_krt_prelist());
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
