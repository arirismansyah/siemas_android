package com.example.siemas.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsbs;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class DsbsPencacahanAdapter extends RecyclerView.Adapter<DsbsPencacahanAdapter.ViewHolder> {
    private List<Dsbs> dsbsList = new ArrayList<>();
    private List<Dsrt> dsrtListBelum = new ArrayList<>();
    private List<Dsrt> dsrtListSudah = new ArrayList<>();
    private onItemCLickListener listener;
    private ViewModel viewModel;

    public DsbsPencacahanAdapter(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.dsbs_pencacahan_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Dsbs currentDsbs = dsbsList.get(position);

        holder.tvIdBs.setText("BS: "+currentDsbs.getId_bs());
        holder.tvKdKab.setText("["+currentDsbs.getKd_kab()+"]");
        holder.tvNamaKab.setText(currentDsbs.getNama_kab());
        holder.tvKdKec.setText("["+currentDsbs.getKd_kec()+"]");
        holder.tvNamaKec.setText(currentDsbs.getNama_kec());
        holder.tvKdDesa.setText("["+currentDsbs.getKd_desa()+"]");
        holder.tvNamaDesa.setText(currentDsbs.getNama_desa());
        holder.tvDsrtBelum.setText(String.valueOf(dsrtListBelum.size()));
        holder.tvDsrtSudah.setText(String.valueOf(dsrtListSudah.size()));

        dsrtListBelum = viewModel.getListDsrtByIdBsStatusLw(currentDsbs.getId_bs(), 1);
        dsrtListSudah = viewModel.getListDsrtByIdBsStatusUp(currentDsbs.getId_bs(), 0);

        holder.tvDsrtBelum.setText(String.valueOf(dsrtListBelum.size()));
        holder.tvDsrtSudah.setText(String.valueOf(dsrtListSudah.size()));

        if (dsrtListSudah.size() == (dsrtListSudah.size()+dsrtListBelum.size())){
            holder.tagStatusBs.setImageResource(R.drawable.ic_tag_saved);
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
        private TextView tvIdBs, tvKdKab, tvNamaKab, tvKdKec, tvNamaKec, tvKdDesa, tvNamaDesa, tvDsrtBelum, tvDsrtSudah;
        private ImageView tagStatusBs;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdBs = itemView.findViewById(R.id.idBS);
            tvKdKab = itemView.findViewById(R.id.idKab);
            tvNamaKab = itemView.findViewById(R.id.NamaKab);
            tvKdKec = itemView.findViewById(R.id.idKec);
            tvNamaKec = itemView.findViewById(R.id.NamaKec);
            tvKdDesa = itemView.findViewById(R.id.idDesa);
            tvNamaDesa = itemView.findViewById(R.id.NamaDesa);
            tagStatusBs = itemView.findViewById(R.id.tagStatusBs);
            tvDsrtBelum = itemView.findViewById(R.id.dsrtBelum);
            tvDsrtSudah = itemView.findViewById(R.id.dsrtSudah);
            tagStatusBs = itemView.findViewById(R.id.tagStatusBs);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(dsbsList.get(position));
                    }
                }
            });

        }
    }

    public interface onItemCLickListener {
        void onItemClick(Dsbs dsbs);
    }

    public void setOnItemClickListener(onItemCLickListener listener) {
        this.listener = listener;
    }
}
