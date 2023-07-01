package com.example.siemas.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class FotoDsbsAdapter  extends RecyclerView.Adapter<FotoDsbsAdapter.ViewHolder> {
    private List<Dsbs> dsbsList = new ArrayList<>();
    private FotoDsbsAdapter.onItemCLickListener listener;
    private ViewModel viewModel;
    private List<Dsrt> dsrtListBelum = new ArrayList<>();
    private List<Dsrt> dsrtListSudah = new ArrayList<>();
    private List<Periode> periodeList;

    public FotoDsbsAdapter(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.foto_dsbs_list_item, parent, false);
        return new FotoDsbsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FotoDsbsAdapter.ViewHolder holder, int position) {
        Dsbs currentDsbs = dsbsList.get(position);

        holder.tvIdBs.setText("BS: 16"+  currentDsbs.getKd_kab()+ currentDsbs.getKd_kec() + currentDsbs.getKd_desa()+ currentDsbs.getKd_bs());
        holder.tvKdKab.setText("["+currentDsbs.getKd_kab()+"]");
        holder.tvNamaKab.setText(currentDsbs.getNama_kab());
        holder.tvKdKec.setText("["+currentDsbs.getKd_kec()+"]");
        holder.tvNamaKec.setText(currentDsbs.getNama_kec());
        holder.tvKdDesa.setText("["+currentDsbs.getKd_desa()+"]");
        holder.tvNamaDesa.setText(currentDsbs.getNama_desa());
        periodeList = viewModel.getPeriode();
        dsrtListBelum = viewModel.getListDsrtByIdBsStatusLw(4, periodeList.get(0).getTahun(), periodeList.get(0).getSemester(), currentDsbs.getKd_kab(), currentDsbs.getKd_kec() , currentDsbs.getKd_desa(), currentDsbs.getKd_bs());
        dsrtListSudah = viewModel.getListDsrtByIdBsStatusUp(3, periodeList.get(0).getTahun(), periodeList.get(0).getSemester(), currentDsbs.getKd_kab(), currentDsbs.getKd_kec() , currentDsbs.getKd_desa(), currentDsbs.getKd_bs());

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
