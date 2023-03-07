package com.example.siemas.Adapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsrt;

import java.util.ArrayList;
import java.util.List;

public class DsrtPemeriksaanPclAdapter extends RecyclerView.Adapter<DsrtPemeriksaanPclAdapter.ViewHolder> {
    private List<Dsrt> dsrtList = new ArrayList<>();
    private onItemCLickListener listener;

    @NonNull
    @Override
    public DsrtPemeriksaanPclAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dsrt_pemeriksaan_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DsrtPemeriksaanPclAdapter.ViewHolder holder, int position) {
        Dsrt currentDsrt = dsrtList.get(position);

        if (!currentDsrt.getNama_krt2().isEmpty() && !currentDsrt.getNama_krt2().equals("null")){
            holder.tvNamaKrt.setText("Nama KRT: "+currentDsrt.getNama_krt2());
        } else {
            holder.tvNamaKrt.setText("Nama KRT: "+currentDsrt.getNama_krt());
        }
        holder.tvNuRt.setText("No Urut Ruta: "+String.valueOf(currentDsrt.getNu_rt()));
        holder.tvNks.setText("NKS: "+currentDsrt.getNks());

        int statusPencacahan = currentDsrt.getStatus_pencacahan();
        String fotoRumahPath = currentDsrt.getFoto();
        if (!fotoRumahPath.isEmpty() && !fotoRumahPath.equals("null")) {
//            holder.ivRumah.setImageURI(Uri.parse(fotoRumahPath));
            try {
                Uri imageUri = Uri.parse(currentDsrt.getFoto());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(holder.ivRumah.getContext().getContentResolver() , imageUri);
                holder.ivRumah.setImageBitmap(bitmap);
            } catch (Exception e) {
                Log.d("Failed Load Image", "Failed Load Image");
            }
        } else {
            holder.ivRumah.setImageResource(R.drawable.ic_home);
        }
        if (statusPencacahan<3){
            holder.statusDsrt.setImageResource(R.drawable.ic_tag_notyet);
            holder.tvStatusPemeriksaan.setText("Belum");
            holder.tvStatusPemeriksaan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPemeriksaan.getContext(), R.color.red));

        }

        if (statusPencacahan==3){
            holder.statusDsrt.setImageResource(R.drawable.ic_tag_savedlocal);
            holder.tvStatusPemeriksaan.setText("Sudah");
            holder.tvStatusPemeriksaan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPemeriksaan.getContext(), R.color.orange));
        }

        if (statusPencacahan>3){
            holder.statusDsrt.setImageResource(R.drawable.ic_tag_saved);
            holder.tvStatusPemeriksaan.setText("Terupload");
            holder.tvStatusPemeriksaan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPemeriksaan.getContext(), R.color.teal_200));
            holder.tvStatusPencacahan.setText("Terupload");
            holder.tvStatusPencacahan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPencacahan.getContext(), R.color.teal_200));
        }


        if (statusPencacahan>0 && statusPencacahan<=3){
            holder.tvStatusPencacahan.setText("Sudah");
            holder.tvStatusPencacahan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPencacahan.getContext(), R.color.orange));
        }

        if (statusPencacahan<1){
            holder.tvStatusPencacahan.setText("Belum");
            holder.tvStatusPencacahan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPencacahan.getContext(), R.color.red));
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNks, tvNuRt, tvNamaKrt, tvStatusPencacahan, tvStatusPemeriksaan;
        private ImageView statusDsrt, ivRumah;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNks = itemView.findViewById(R.id.nks);
            tvNuRt = itemView.findViewById(R.id.nuRt);
            tvNamaKrt = itemView.findViewById(R.id.namaKrt);
            statusDsrt = itemView.findViewById(R.id.tagStatusDsrt);
            tvStatusPencacahan = itemView.findViewById(R.id.tvStatusPencacahan);
            tvStatusPemeriksaan = itemView.findViewById(R.id.tvStatusPemeriksaan);
            ivRumah = itemView.findViewById(R.id.ivRumah);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(dsrtList.get(position));
                    }
                }
            });
        }
    }


    public interface onItemCLickListener {
        void onItemClick(Dsrt dsrt);
    }

    public void setOnItemClickListener(onItemCLickListener listener) {
        this.listener = listener;
    }

}
