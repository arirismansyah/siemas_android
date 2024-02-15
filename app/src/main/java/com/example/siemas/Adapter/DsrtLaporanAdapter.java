package com.example.siemas.Adapter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siemas.Activities.MainActivity;
import com.example.siemas.ImageResizer;
import com.example.siemas.InterfaceApi;
import com.example.siemas.R;
import com.example.siemas.RetrofitClientInstance;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.Entities.Laporan212;
import com.example.siemas.RoomDatabase.Entities.Periode;
import com.example.siemas.RoomDatabase.Entities.User;
import com.example.siemas.RoomDatabase.ViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DsrtLaporanAdapter extends RecyclerView.Adapter<DsrtLaporanAdapter.ViewHolder> {
    private List<Laporan212> laporan212List = new ArrayList<>();
    private ViewModel viewModel;
    private List<Periode> periodeList;

    public DsrtLaporanAdapter(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dsrt_laporan_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Laporan212 currentLaporan = laporan212List.get(position);
        holder.laporan212 = currentLaporan;
        periodeList = viewModel.getPeriode();
        holder.tvNbs.setText("ID BS: 16" + currentLaporan.getKd_kab()+ currentLaporan.getKd_kec()+currentLaporan.getKd_desa()+currentLaporan.getKd_bs());
        holder.tvNks.setText("NKS: " + currentLaporan.getNks());
        holder.tvNuRt.setText("NU RT: " + String.valueOf(currentLaporan.getNu_rt()));
        holder.tvNamaKrt.setText("Nama KRT: "+currentLaporan.getNama_krt());
        holder.tvTanggalLaporan.setText("Tanggal Laporan: "+currentLaporan.getTanggal());

        int status = currentLaporan.getStatus();

        if (status == 0) {
            holder.tvStatusLaporan.setText("Belum");
            holder.tvStatusLaporan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusLaporan.getContext(), R.color.red));
            holder.ivTagStatus.setImageResource(R.drawable.ic_tag_notyet);
            holder.uploadBtn.setBackgroundColor(ContextCompat.getColor(holder.uploadBtn.getContext(), R.color.dark));
        }

        if (status == 1) {
            holder.tvStatusLaporan.setText("Tersimpan");
            holder.tvStatusLaporan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusLaporan.getContext(), R.color.orange));
            holder.ivTagStatus.setImageResource(R.drawable.ic_tag_savedlocal);
            holder.uploadBtn.setBackgroundColor(ContextCompat.getColor(holder.uploadBtn.getContext(), R.color.dark));
        }
        if (status == 2) {
            holder.tvStatusLaporan.setText("Terupload");
            holder.tvStatusLaporan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusLaporan.getContext(), R.color.teal_200));
            holder.ivTagStatus.setImageResource(R.drawable.ic_tag_saved);
            holder.uploadBtn.setBackgroundColor(ContextCompat.getColor(holder.uploadBtn.getContext(), R.color.teal_200));
            holder.deleteBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return laporan212List.size();
    }

    public void setListLaporan(List<Laporan212> laporan212List) {
        this.laporan212List = laporan212List;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNbs, tvNks, tvNuRt, tvNamaKrt, tvStatusLaporan, tvTanggalLaporan;
        private ImageView ivTagStatus;
        private AppCompatImageButton uploadBtn, deleteBtn;
        private Laporan212 laporan212;
        private User user;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNbs = itemView.findViewById(R.id.nbs);
            tvNks = itemView.findViewById(R.id.nks);
            tvNuRt = itemView.findViewById(R.id.nuRt);
            tvNamaKrt = itemView.findViewById(R.id.namaKrt);
            tvStatusLaporan = itemView.findViewById(R.id.tvStatusLaporan);
            ivTagStatus = itemView.findViewById(R.id.tagStatusDsrt);
            uploadBtn = itemView.findViewById(R.id.uploadBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            tvTanggalLaporan = itemView.findViewById(R.id.tanggalLaporan);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
                    alertDialogBuilder.setTitle("SIEMAS 2024");
                    alertDialogBuilder.setMessage("Anda yakin ingin menghapus laporan ini?");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            viewModel.deleteLaporan(periodeList.get(0).getTahun(), periodeList.get(0).getSemester(),  laporan212.getKd_kab(), laporan212.getKd_kec(), laporan212.getKd_desa(), laporan212.getKd_bs(), laporan212.getNu_rt());
                        }
                    });
                    alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });

            uploadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean status_net = getConnectivityStatusString(itemView.getContext());
                    if (!status_net) {
                        Toast.makeText(itemView.getContext(), "Koneksi terputus, periksa koneksi internet anda.", Toast.LENGTH_SHORT).show();
                    } else {
                        // update data dsrt
                        ProgressDialog progressDialog = new ProgressDialog(itemView.getContext());
                        progressDialog.setMessage("Mengirim Data");
                        progressDialog.show();
                        Log.d(TAG, "onClick: tahun "+ laporan212.getTahun());
                        user = viewModel.getUser().get(0);
                        JsonElement laporanJson = new Gson().toJsonTree(laporan212);
                        Log.d(TAG, "onClick: json"+laporanJson.toString());
                        InterfaceApi interfaceApi = RetrofitClientInstance.getClient().create(InterfaceApi.class);
                        Call<ResponseBody> call = interfaceApi.insertLaporan(laporanJson.toString(), "Bearer " + user.getToken());
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.code()==200){
                                    try {
                                        String result = response.body().string();
                                        JSONObject jo = new JSONObject(result);
                                        String message = jo.getString("message");
                                        if (message.equals("success")) {

                                            viewModel.updateStatusLaporan( 2, periodeList.get(0).getTahun(), periodeList.get(0).getSemester(),  laporan212.getKd_kab(), laporan212.getKd_kec(), laporan212.getKd_desa(), laporan212.getKd_bs(), laporan212.getNu_rt());
                                            progressDialog.dismiss();
                                            Toast.makeText(itemView.getContext(), "Data berhasil dikirim", Toast.LENGTH_SHORT).show();

                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(itemView.getContext(), "Ada kesalahan di server", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException | IOException e) {
                                        progressDialog.dismiss();
                                        e.printStackTrace();
                                    }
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(itemView.getContext(), "Ada kesalahan di server", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(itemView.getContext(), "Ada kesalahan di server", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

        }
    }

    // Cek Koneksi
    public Boolean getConnectivityStatusString(Context context) {
        String status = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = "Wifi enabled";
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                status = "Mobile data enabled";
                return true;
            }
        } else {
            status = "No internet is available";
            return false;
        }
        return false;
    }

}
