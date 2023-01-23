package com.example.siemas.Adapter;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsbs;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.Entities.Periode;
import com.example.siemas.RoomDatabase.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class Table1PencacahanAdapter extends RecyclerView.Adapter<Table1PencacahanAdapter.ViewHolder> {
    private List<Dsbs> dsbsList = new ArrayList<>();
    private ViewModel viewModel;
    private List<Dsrt> dsrtList = new ArrayList<>();
    private List<Dsrt> dsrtListEntri = new ArrayList<>();
    private List<Dsrt> dsrtListUpload = new ArrayList<>();
    private List<Periode> periodeList;

    public Table1PencacahanAdapter(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.table1_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (dsbsList != null && dsbsList.size()>0){
            Dsbs dsbsCurrent = dsbsList.get(position);
            periodeList = viewModel.getPeriode();
            dsrtList = viewModel.getListDsrtByIdBs(dsbsCurrent.getId_bs(),periodeList.get(0).getTahun(), periodeList.get(0).getSemester());

            dsrtListEntri = viewModel.getListDsrtByIdBsStatusUp(dsbsCurrent.getId_bs(), 0, periodeList.get(0).getTahun(), periodeList.get(0).getSemester());
            dsrtListUpload = viewModel.getListDsrtByIdBsStatusUp(dsbsCurrent.getId_bs(), 3, periodeList.get(0).getTahun(), periodeList.get(0).getSemester());

            holder.tvNbs.setText(dsbsCurrent.getId_bs());
            holder.tvTarget.setText(String.valueOf(dsrtList.size()));
            holder.tvEntri.setText(String.valueOf(dsrtListEntri.size()));
            holder.tvUpload.setText(String.valueOf(dsrtListUpload.size()));
            holder.tvPersenEntri.setText(String.valueOf((dsrtListEntri.size()*100)/dsrtList.size()));
            holder.tvPersenUpload.setText(String.valueOf((dsrtListUpload.size()*100)/dsrtList.size()));
        }
    }

    @Override
    public int getItemCount() {
        return dsbsList.size();
    }

    public void setListDsbs(List<Dsbs> dsbsList) {
        this.dsbsList = dsbsList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNbs, tvTarget, tvEntri, tvUpload, tvPersenEntri, tvPersenUpload;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNbs = itemView.findViewById(R.id.nbs);
            tvTarget = itemView.findViewById(R.id.target);
            tvEntri = itemView.findViewById(R.id.entri);
            tvUpload = itemView.findViewById(R.id.upload);
            tvPersenEntri = itemView.findViewById(R.id.persenEntri);
            tvPersenUpload = itemView.findViewById(R.id.persenUpload);

        }
    }
}
