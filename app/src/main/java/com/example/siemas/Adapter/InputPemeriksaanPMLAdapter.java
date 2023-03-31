package com.example.siemas.Adapter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsart;
import com.example.siemas.RoomDatabase.Entities.KegiatanUtama;
import com.example.siemas.RoomDatabase.Entities.Pendidikan;
import com.example.siemas.RoomDatabase.ViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InputPemeriksaanPMLAdapter extends RecyclerView.Adapter<InputPemeriksaanPMLAdapter.ViewHolder> {
    List<Dsart> dsartList = new ArrayList<>();
    List<Dsart> dsartListlama = new ArrayList<>();
    private ViewModel viewModel;

    private List<Pendidikan> pendidikanList;
    private List<KegiatanUtama> kegiatanUtamaList;

    ArrayAdapter<String> spinnerIjazahAdapter;
    ArrayAdapter<String> spinnerKegiatanAdapter;

    public void saveadapter(ViewModel viewModel){
        viewModel.insertDsart(dsartList);
    }
    public InputPemeriksaanPMLAdapter(ViewModel viewModel, String id_bs, String tahun, int semester, int nu_rt){
        this.viewModel = viewModel;
        this.dsartList = viewModel.getDsartbyId(id_bs, tahun, semester, nu_rt);
        String tahunlama = String.valueOf(Integer.parseInt(tahun)-1) ;
        try{
            this.dsartListlama = viewModel.getDsartbyId(id_bs,tahunlama,semester,nu_rt);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public InputPemeriksaanPMLAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_input_pemeriksaan_pml_listitem, parent,false);
        return new ViewHolder(view, new NamaArtTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull InputPemeriksaanPMLAdapter.ViewHolder holder, int position) {
        Dsart currentdsart = dsartList.get(position);
        holder.namaArtTextListener.updatePosition(holder.getAdapterPosition());
//        holder.pendapatanTextListener.updatePosition(holder.getAdapterPosition());
        holder.nuART.setText(Integer.toString(currentdsart.getNu_art()));
        holder.namaART.setText(currentdsart.getNama_art());
        holder.pendidikanART.setSelection(spinnerIjazahAdapter.getPosition(currentdsart.getPendidikan()));
        holder.pekerjaanART.setSelection(spinnerKegiatanAdapter.getPosition(currentdsart.getPekerjaan()));
//            holder.pendapatanART.setText(currentdsart.getPendapatan());
        if (!currentdsart.getPendapatan().isEmpty() && !currentdsart.getPendapatan().equals("null")) {
            holder.pendapatanART.setText(currentdsart.getPendapatan());
        }
        holder.pendidikanART.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentdsart.setPendidikan(holder.pendidikanART.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        holder.pekerjaanART.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentdsart.setPekerjaan( holder.pekerjaanART.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        holder.pendapatanART.addTextChangedListener(new TextWatcher() {
            private String setEditRupiah = holder.pendapatanART.getText().toString().trim();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(setEditRupiah)) {
                    holder.pendapatanART.removeTextChangedListener(this);
                    String replace = s.toString().replaceAll("[Rp. ]", "");
                    if (!replace.isEmpty()){
                        setEditRupiah = formatrupiah(Double.parseDouble(replace));
                    }else{
                        setEditRupiah = "";
                    }
                    holder.pendapatanART.setText(setEditRupiah);
                    holder.pendapatanART.setSelection(setEditRupiah.length());
                    holder.pendapatanART.addTextChangedListener( this);
                    dsartList.get(holder.getAdapterPosition()).setPendapatan(s.toString());
                }
            }
        });

        try {
            Dsart currentdsartlama = dsartListlama.get(position);
            holder.nuARTlama.setText(Integer.toString(currentdsartlama.getNu_art()));
            holder.namaARTlama.setText(currentdsartlama.getNama_art());
            holder.pendidikanARTlama.setText(currentdsartlama.getPendidikan());
            holder.pekerjaanARTlama.setText(currentdsartlama.getPekerjaan());
            holder.pendapatanARTlama.setText(formatrupiah( Double.parseDouble(currentdsartlama.getPendapatan())));

        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public int getItemCount() {
        return dsartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextInputEditText nuART, namaART ,pendapatanART;
        private Spinner pendidikanART, pekerjaanART;
        private TextView nuARTlama, namaARTlama, pendidikanARTlama, pekerjaanARTlama,pendapatanARTlama;
        public NamaArtTextListener namaArtTextListener;
        public PendidikanArtTextListener pendidikanArtTextListener;
        public PekerjaanTextListener pekerjaanTextListener;
        public PendapatanTextListener pendapatanTextListener;

        public ViewHolder(@NonNull View itemView, NamaArtTextListener namaArtTextListener) {
            super(itemView);

            nuART = itemView.findViewById(R.id.nuART);
            namaART = itemView.findViewById(R.id.namaART);
            pendidikanART = (Spinner) itemView.findViewById(R.id.pendidikanART);
            pekerjaanART = (Spinner) itemView.findViewById(R.id.pekerjaanART);
            pendapatanART = itemView.findViewById(R.id.pendapatanART);

            pendidikanList = viewModel.getAllPendidikan();
            List<String> namaPendidikan = new ArrayList<String>();
            for (int i = 0; i < pendidikanList.size(); i++) {
                namaPendidikan.add(pendidikanList.get(i).getPendidikan());
            }
            spinnerIjazahAdapter = new ArrayAdapter<String>(itemView.getContext(), R.layout.multi_line_spinner_support, namaPendidikan);
            pendidikanART.setAdapter(spinnerIjazahAdapter);

            kegiatanUtamaList = viewModel.getAllKegiatan();
            List<String> namaKegiatan = new ArrayList<>();
            for (int i = 0; i < kegiatanUtamaList.size(); i++) {
                namaKegiatan.add(kegiatanUtamaList.get(i).getKegiatan_utama());
            }
            spinnerKegiatanAdapter = new ArrayAdapter<String>(itemView.getContext(), R.layout.spinner_textview, namaKegiatan);
            pekerjaanART.setAdapter(spinnerKegiatanAdapter);


            nuARTlama = itemView.findViewById(R.id.nuARTlama);
            namaARTlama = itemView.findViewById(R.id.namaARTlama);
            pendidikanARTlama = itemView.findViewById(R.id.pendidikanARTlama);
            pekerjaanARTlama = itemView.findViewById(R.id.pekerjaanARTlama);
            pendapatanARTlama = itemView.findViewById(R.id.pendapatanARTlama);

            this.namaArtTextListener = namaArtTextListener;
            this.namaART.addTextChangedListener(namaArtTextListener);

//            this.pendapatanTextListener = pendapatanTextListener;
//            this.pendapatanART.addTextChangedListener(pendapatanTextListener);
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
    private String formatrupiah(Double number){
        Locale locale = new Locale("IND", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        String formatrupiah = numberFormat.format(number);
        String[] split = formatrupiah.split(",");
        int length = split[0].length();
        return split[0].substring(0,2)+". "+split[0]. substring(2,length);
    }


}
