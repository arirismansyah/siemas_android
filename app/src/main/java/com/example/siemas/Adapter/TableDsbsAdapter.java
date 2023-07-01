package com.example.siemas.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsbs;

import java.util.ArrayList;
import java.util.List;

public class TableDsbsAdapter extends RecyclerView.Adapter<TableDsbsAdapter.ViewHolder> {

    private List<Dsbs> dsbsList = new ArrayList<>();



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.dsbs_table_item, parent, false);
        return new TableDsbsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (dsbsList != null && dsbsList.size()>0){
            Dsbs dsbsCurrent = dsbsList.get(position);
            holder.tvKec.setText(dsbsCurrent.getNama_kec());
            holder.tvDesa.setText(dsbsCurrent.getNama_desa());
            holder.tvNbs.setText(dsbsCurrent.getKd_bs());
            holder.tvNks.setText(dsbsCurrent.getNks());
        }
    }

    @Override
    public int getItemCount() {
        return dsbsList.size() ;
    }

    public void setListDsbs(List<Dsbs> dsbsList) {
        this.dsbsList = dsbsList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvKec, tvDesa, tvNbs, tvNks;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKec = itemView.findViewById(R.id.kec);
            tvDesa = itemView.findViewById(R.id.desa);
            tvNbs = itemView.findViewById(R.id.nbs);
            tvNks = itemView.findViewById(R.id.nks);
        }
    }
}
