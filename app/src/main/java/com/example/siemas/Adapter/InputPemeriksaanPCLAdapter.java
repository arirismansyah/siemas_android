package com.example.siemas.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsart;
import com.example.siemas.RoomDatabase.Entities.Dsbs;
import com.example.siemas.RoomDatabase.ViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class InputPemeriksaanPCLAdapter extends RecyclerView.Adapter<InputPemeriksaanPCLAdapter.ViewHolder> {
    private List<Dsart> dsartList;
    private ViewModel viewModel;

    public InputPemeriksaanPCLAdapter(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public InputPemeriksaanPCLAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_input_pemeriksaan_listitem, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InputPemeriksaanPCLAdapter.ViewHolder holder, int position) {
            Dsart currentdsart = dsartList.get(position);

            holder.nuART.setText(currentdsart.nu_art);
            holder.namaART.setText(currentdsart.nama_art);
            holder.pendidikanART.setText(currentdsart.pendidikan);
            holder.pekerjaanART.setText(currentdsart.pekerjaan);
            holder.pendapatanART.setText(currentdsart.pendapatan);
    }

    @Override
    public int getItemCount() {
        return dsartList.size();
    }
    public void setListDsArt(List<Dsart> dsartList) {
        this.dsartList = dsartList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextInputEditText nuART, namaART, pendidikanART, pekerjaanART,pendapatanART;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nuART = itemView.findViewById(R.id.nuART);
            namaART = itemView.findViewById(R.id.namaART);
            pendidikanART = itemView.findViewById(R.id.pendidikanART);
            pekerjaanART = itemView.findViewById(R.id.pekerjaanART);
            pendapatanART = itemView.findViewById(R.id.pendapatanART);
        }
    }
}
