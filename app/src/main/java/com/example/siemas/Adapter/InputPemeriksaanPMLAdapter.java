package com.example.siemas.Adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsart;
import com.example.siemas.RoomDatabase.ViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class InputPemeriksaanPMLAdapter extends RecyclerView.Adapter<InputPemeriksaanPMLAdapter.ViewHolder> {
    List<Dsart> dsartList = new ArrayList<>();
    private ViewModel viewModel;

    public void saveadapter(ViewModel viewModel){
        viewModel.insertDsart(dsartList);
    }
    public InputPemeriksaanPMLAdapter(ViewModel viewModel, String id_bs, String tahun, int semester, int nu_rt){
        this.viewModel = viewModel;
        this.dsartList = viewModel.getDsartbyId(id_bs, tahun, semester, nu_rt);
    }

    @NonNull
    @Override
    public InputPemeriksaanPMLAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_input_pemeriksaan_pml_listitem, parent,false);
        return new ViewHolder(view, new NamaArtTextListener(),
                new PendidikanArtTextListener(), new PekerjaanTextListener(), new PendapatanTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull InputPemeriksaanPMLAdapter.ViewHolder holder, int position) {
        Dsart currentdsart = dsartList.get(position);

        holder.namaArtTextListener.updatePosition(holder.getAdapterPosition());
        holder.pendidikanArtTextListener.updatePosition(holder.getAdapterPosition());
        holder.pekerjaanTextListener.updatePosition(holder.getAdapterPosition());
        holder.pendapatanTextListener.updatePosition(holder.getAdapterPosition());

        holder.nuART.setText(Integer.toString(currentdsart.getNu_art()));
        holder.namaART.setText(currentdsart.getNama_art());
        holder.pendidikanART.setText(currentdsart.getPendidikan());
        holder.pekerjaanART.setText(currentdsart.getPekerjaan());
        holder.pendapatanART.setText(currentdsart.getPendapatan());
    }

    @Override
    public int getItemCount() {
        return dsartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextInputEditText nuART, namaART, pendidikanART, pekerjaanART,pendapatanART;
        public NamaArtTextListener namaArtTextListener;
        public PendidikanArtTextListener pendidikanArtTextListener;
        public PekerjaanTextListener pekerjaanTextListener;
        public PendapatanTextListener pendapatanTextListener;

        public ViewHolder(@NonNull View itemView, NamaArtTextListener namaArtTextListener, PendidikanArtTextListener pendidikanArtTextListener, PekerjaanTextListener pekerjaanTextListener, PendapatanTextListener pendapatanTextListener) {
            super(itemView);

            nuART = itemView.findViewById(R.id.nuART);
            namaART = itemView.findViewById(R.id.namaART);
            pendidikanART = itemView.findViewById(R.id.pendidikanART);
            pekerjaanART = itemView.findViewById(R.id.pekerjaanART);
            pendapatanART = itemView.findViewById(R.id.pendapatanART);

            this.namaArtTextListener = namaArtTextListener;
            this.namaART.addTextChangedListener(namaArtTextListener);

            this.pendidikanArtTextListener = pendidikanArtTextListener;
            this.pendidikanART.addTextChangedListener(pendidikanArtTextListener);

            this.pekerjaanTextListener = pekerjaanTextListener;
            this.pekerjaanART.addTextChangedListener(pekerjaanTextListener);

            this.pendapatanTextListener = pendapatanTextListener;
            this.pendapatanART.addTextChangedListener(pendapatanTextListener);
        }
    }

    private class NamaArtTextListener implements TextWatcher {
        private int position;
        public void updatePosition(int position) {
            this.position = position;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
        @Override
        public void afterTextChanged(Editable s) {
            dsartList.get(position).setNama_art(s.toString());
        }
    }

    private class PendidikanArtTextListener implements  TextWatcher{
        private int position;
        public void updatePosition(int position) {
            this.position = position;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
        @Override
        public void afterTextChanged(Editable s) {
            dsartList.get(position).setPendidikan(s.toString());
        }
    }

    private class PekerjaanTextListener implements TextWatcher{
        private int position;
        public void updatePosition(int position) {
            this.position = position;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
        @Override
        public void afterTextChanged(Editable s) {
            dsartList.get(position).setPekerjaan(s.toString());
        }
    }

    private class PendapatanTextListener implements TextWatcher{
        private int position;
        public void updatePosition(int position) {
            this.position = position;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            dsartList.get(position).setPendapatan(s.toString());
        }
    }


}
