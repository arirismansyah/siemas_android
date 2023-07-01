package com.example.siemas.Adapter;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.siemas.RoomDatabase.Entities.Dsbs;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.ViewModel;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class DsrtPencacahanAdapter extends RecyclerView.Adapter<DsrtPencacahanAdapter.ViewHolder> {
    private List<Dsrt> dsrtList = new ArrayList<>();
    private onItemCLickListener listener;
    private ViewModel viewModel;

    public DsrtPencacahanAdapter(ViewModel viewModel) {
        this.viewModel = viewModel;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dsrt_pencacahan_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Dsrt currentDsrt = dsrtList.get(position);

        if (!currentDsrt.getNama_krt_cacah().isEmpty() && !currentDsrt.getNama_krt_cacah().equals("null")){
            holder.tvNamaKrt.setText("Nama KRT: "+currentDsrt.getNama_krt_cacah());
        } else {
            holder.tvNamaKrt.setText("Nama KRT: "+currentDsrt.getNama_krt_prelist());
        }
        holder.tvNuRt.setText("No Urut Ruta: "+String.valueOf(currentDsrt.getNu_rt()));
        holder.tvNks.setText("NKS: "+currentDsrt.getNks());

//        Dsrt fotodsrt = viewModel.getdsrtfoto(dsrtList.get(position).getId());
//        if (!fotodsrt.getFoto().equals("null")) {
//            try {
//                byte[] imageBytes = fotodsrt.getFoto();
//                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//                holder.ivRumah.setImageBitmap(bitmap);
//            } catch (Exception e) {
//                Log.d("Failed Load Image", "Failed Load Image");
//            }
//        } else {
//            holder.ivRumah.setImageResource(R.drawable.ic_home);
//        }

        int statusPencacahan = currentDsrt.getStatus_pencacahan();

        if (statusPencacahan<1){
            holder.statusDsrt.setImageResource(R.drawable.ic_tag_notyet);
            holder.tvStatusPencacahan.setText("Belum");
            holder.tvStatusPencacahan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPencacahan.getContext(), R.color.red));
        }

        if (statusPencacahan>=1){
            holder.statusDsrt.setImageResource(R.drawable.ic_tag_savedlocal);
            holder.tvStatusPencacahan.setText("Sudah");
            holder.tvStatusPencacahan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPencacahan.getContext(), R.color.orange));
        }

        if (statusPencacahan>3){
            holder.statusDsrt.setImageResource(R.drawable.ic_tag_saved);
            holder.tvStatusPencacahan.setText("Terupload");
            holder.tvStatusPencacahan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPencacahan.getContext(), R.color.teal_200));
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
        private TextView tvNks, tvNuRt, tvNamaKrt, tvStatusPencacahan;
        private ImageView statusDsrt, ivRumah;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNks = itemView.findViewById(R.id.nks);
            tvNuRt = itemView.findViewById(R.id.nuRt);
            tvNamaKrt = itemView.findViewById(R.id.namaKrt);
            statusDsrt = itemView.findViewById(R.id.tagStatusDsrt);
            ivRumah = itemView.findViewById(R.id.ivRumah);
            tvStatusPencacahan = itemView.findViewById(R.id.tvStatusPencacahan);

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
