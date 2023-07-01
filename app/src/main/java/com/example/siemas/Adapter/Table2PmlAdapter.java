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
import com.example.siemas.RoomDatabase.Entities.Laporan212;
import com.example.siemas.RoomDatabase.Entities.Periode;
import com.example.siemas.RoomDatabase.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class Table2PmlAdapter extends RecyclerView.Adapter<Table2PmlAdapter.ViewHolder> {
    private ViewModel viewModel;
    private List<Dsbs> dsbsList = new ArrayList<>();
    private List<Dsrt> dsrtList = new ArrayList<>();
    private List<Laporan212> laporanEntriList = new ArrayList<>();
    private List<Laporan212> laporanUploadList = new ArrayList<>();
    private List<Periode> periodeList;
    public Table2PmlAdapter(ViewModel viewModel) {
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
            dsrtList = viewModel.getListDsrtByIdBs(periodeList.get(0).getTahun(), periodeList.get(0).getSemester(),dsbsCurrent.getKd_kab(), dsbsCurrent.getKd_kec(),dsbsCurrent.getKd_desa(),dsbsCurrent.getKd_bs());
            laporanEntriList = viewModel.getListLaporanByIdBsStatusUp(0,  periodeList.get(0).getTahun(), periodeList.get(0).getSemester(),dsbsCurrent.getKd_kab(), dsbsCurrent.getKd_kec(),dsbsCurrent.getKd_desa(),dsbsCurrent.getKd_bs());
            laporanUploadList = viewModel.getListLaporanByIdBsStatus( 2, periodeList.get(0).getTahun(), periodeList.get(0).getSemester(), dsbsCurrent.getKd_kab(), dsbsCurrent.getKd_kec(),dsbsCurrent.getKd_desa(),dsbsCurrent.getKd_bs());

            holder.tvNbs.setText("16"+dsbsCurrent.getKd_kab()+ dsbsCurrent.getKd_kec()+dsbsCurrent.getKd_desa()+dsbsCurrent.getKd_bs());
            holder.tvTarget.setText(String.valueOf(dsrtList.size()));
            holder.tvEntri.setText(String.valueOf(laporanEntriList.size()));
            holder.tvUpload.setText(String.valueOf(laporanUploadList.size()));
            holder.tvTotal.setText(String.valueOf((laporanEntriList.size()*100)/dsrtList.size()));
            holder.tvPersenUpload.setText(String.valueOf((laporanUploadList.size()*100)/dsrtList.size()));
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNbs, tvTarget, tvEntri, tvUpload, tvTotal, tvPersenUpload;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNbs = itemView.findViewById(R.id.nbs);
            tvTarget = itemView.findViewById(R.id.target);
            tvEntri = itemView.findViewById(R.id.entri);
            tvUpload = itemView.findViewById(R.id.upload);
            tvTotal = itemView.findViewById(R.id.persenEntri);
            tvPersenUpload = itemView.findViewById(R.id.persenUpload);

        }
    }
}
