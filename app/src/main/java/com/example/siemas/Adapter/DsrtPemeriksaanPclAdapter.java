package com.example.siemas.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siemas.InterfaceApi;
import com.example.siemas.R;
import com.example.siemas.RetrofitClientInstance;
import com.example.siemas.RoomDatabase.Entities.Dsart;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.Entities.User;
import com.example.siemas.RoomDatabase.ViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DsrtPemeriksaanPclAdapter extends RecyclerView.Adapter<DsrtPemeriksaanPclAdapter.ViewHolder> {
    private List<Dsrt> dsrtList = new ArrayList<>();
    private onItemCLickListener listener;
    private ViewModel viewModel;

    public DsrtPemeriksaanPclAdapter(ViewModel viewModel) {
        this.viewModel = viewModel;
    }
    @NonNull
    @Override
    public DsrtPemeriksaanPclAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dsrt_pemeriksaan_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DsrtPemeriksaanPclAdapter.ViewHolder holder, int position) {
        Dsrt currentDsrt = dsrtList.get(position);
        holder.dsrt = currentDsrt;

        if (!currentDsrt.getNama_krt_cacah().isEmpty() && !currentDsrt.getNama_krt_cacah().equals("null")) {
            holder.tvNamaKrt.setText("Nama KRT: " + currentDsrt.getNama_krt_cacah());
        } else {
            holder.tvNamaKrt.setText("Nama KRT: " + currentDsrt.getNama_krt_prelist());
        }
        holder.tvNuRt.setText("No Urut Ruta: " + String.valueOf(currentDsrt.getNu_rt()));
        holder.tvNks.setText("NKS: " + currentDsrt.getNks());

        int statusPencacahan = currentDsrt.getStatus_pencacahan();

//        Dsrt fotodsrt = viewModel.getdsrtfoto(currentDsrt.getId());
//        if (!fotodsrt.getFoto().equals("null")) {
//            try {
//                byte[] imageBytes = currentDsrt.getFoto();
//                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//                holder.ivRumah.setImageBitmap(bitmap);
//            } catch (Exception e) {
//                Log.d("Failed Load Image", "Failed Load Image");
//            }
//        } else {
//            holder.ivRumah.setImageResource(R.drawable.ic_home);
//        }

        if(statusPencacahan == 0){
            holder.statusDsrt.setImageResource(R.drawable.ic_tag_notyet);
            holder.tvStatusPencacahan.setText("belum");
            holder.tvStatusPencacahan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPencacahan.getContext(), R.color.red));
            holder.tvStatusPemeriksaan.setText("belum");
            holder.tvStatusPemeriksaan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPemeriksaan.getContext(), R.color.red));
            holder.tvStatusUpload.setText("belum");
            holder.tvStatusUpload.getBackground().setTint(ContextCompat.getColor(holder.tvStatusUpload.getContext(), R.color.red));
            holder.uploadBtn.setBackgroundColor(ContextCompat.getColor(holder.uploadBtn.getContext(), R.color.dark));
        }
        if(statusPencacahan == 1){
            holder.statusDsrt.setImageResource(R.drawable.ic_tag_notyet);
            holder.tvStatusPencacahan.setText("Sudah");
            holder.tvStatusPencacahan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPencacahan.getContext(), R.color.orange));
            holder.tvStatusPemeriksaan.setText("belum");
            holder.tvStatusPemeriksaan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPemeriksaan.getContext(), R.color.red));
            holder.tvStatusUpload.setText("belum");
            holder.tvStatusUpload.getBackground().setTint(ContextCompat.getColor(holder.tvStatusUpload.getContext(), R.color.red));
            holder.uploadBtn.setBackgroundColor(ContextCompat.getColor(holder.uploadBtn.getContext(), R.color.dark));
        }
        if(statusPencacahan == 2){
            holder.statusDsrt.setImageResource(R.drawable.ic_tag_notyet);
            holder.tvStatusPencacahan.setText("Sudah");
            holder.tvStatusPencacahan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPencacahan.getContext(), R.color.orange));
            holder.tvStatusPemeriksaan.setText("belum");
            holder.tvStatusPemeriksaan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPemeriksaan.getContext(), R.color.red));
            holder.tvStatusUpload.setText("belum");
            holder.tvStatusUpload.getBackground().setTint(ContextCompat.getColor(holder.tvStatusUpload.getContext(), R.color.red));
            holder.uploadBtn.setBackgroundColor(ContextCompat.getColor(holder.uploadBtn.getContext(), R.color.dark));
        }
        if(statusPencacahan == 3){
            holder.statusDsrt.setImageResource(R.drawable.ic_tag_savedlocal);
            holder.tvStatusPencacahan.setText("Sudah");
            holder.tvStatusPencacahan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPencacahan.getContext(), R.color.orange));
            holder.tvStatusPemeriksaan.setText("Sudah");
            holder.tvStatusPemeriksaan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPemeriksaan.getContext(), R.color.orange));
            holder.tvStatusUpload.setText("belum");
            holder.tvStatusUpload.getBackground().setTint(ContextCompat.getColor(holder.tvStatusUpload.getContext(), R.color.red));
            holder.uploadBtn.setBackgroundColor(ContextCompat.getColor(holder.uploadBtn.getContext(), R.color.dark));
        }

        if(statusPencacahan == 4){
            holder.statusDsrt.setImageResource(R.drawable.ic_tag_saved);
            holder.tvStatusPencacahan.setText("Terupload");
            holder.tvStatusPencacahan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPencacahan.getContext(), R.color.teal_200));
            holder.tvStatusPemeriksaan.setText("Terupload");
            holder.tvStatusPemeriksaan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPemeriksaan.getContext(), R.color.teal_200));
            holder.tvStatusUpload.setText("Terupload");
            holder.tvStatusUpload.getBackground().setTint(ContextCompat.getColor(holder.tvStatusUpload.getContext(), R.color.teal_200));
            holder.uploadBtn.setBackgroundColor(ContextCompat.getColor(holder.uploadBtn.getContext(), R.color.teal_200));
        }
        if(statusPencacahan == 5){
            holder.statusDsrt.setImageResource(R.drawable.ic_tag_saved);
            holder.tvStatusPencacahan.setText("Terupload");
            holder.tvStatusPencacahan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPencacahan.getContext(), R.color.teal_200));
            holder.tvStatusPemeriksaan.setText("Terupload");
            holder.tvStatusPemeriksaan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPemeriksaan.getContext(), R.color.teal_200));
            holder.tvStatusUpload.setText("Terupload");
            holder.tvStatusUpload.getBackground().setTint(ContextCompat.getColor(holder.tvStatusUpload.getContext(), R.color.teal_200));
            holder.uploadBtn.setBackgroundColor(ContextCompat.getColor(holder.uploadBtn.getContext(), R.color.teal_200));
        }
        if(statusPencacahan == 6){
            holder.statusDsrt.setImageResource(R.drawable.ic_tag_saved);
            holder.tvStatusPencacahan.setText("Terupload");
            holder.tvStatusPencacahan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPencacahan.getContext(), R.color.teal_200));
            holder.tvStatusPemeriksaan.setText("Terupload");
            holder.tvStatusPemeriksaan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPemeriksaan.getContext(), R.color.teal_200));
            holder.tvStatusUpload.setText("Terupload");
            holder.tvStatusUpload.getBackground().setTint(ContextCompat.getColor(holder.tvStatusUpload.getContext(), R.color.teal_200));
            holder.uploadBtn.setBackgroundColor(ContextCompat.getColor(holder.uploadBtn.getContext(), R.color.teal_200));
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
        private TextView tvNks, tvNuRt, tvNamaKrt, tvStatusPencacahan, tvStatusPemeriksaan, tvStatusUpload;
        private ImageView statusDsrt, ivRumah;
        private ImageButton uploadBtn;
        Dsrt dsrt;
        private User user;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNks = itemView.findViewById(R.id.nks);
            tvNuRt = itemView.findViewById(R.id.nuRt);
            tvNamaKrt = itemView.findViewById(R.id.namaKrt);
            statusDsrt = itemView.findViewById(R.id.tagStatusDsrt);
            tvStatusPencacahan = itemView.findViewById(R.id.tvStatusPencacahan);
            tvStatusPemeriksaan = itemView.findViewById(R.id.tvStatusPemeriksaan);
            tvStatusUpload = itemView.findViewById(R.id.tvStatusUpload);
            ivRumah = itemView.findViewById(R.id.ivRumah);
            uploadBtn = itemView.findViewById(R.id.uploadBtn);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(dsrtList.get(position));
                    }
                }
            });
            uploadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dsrt.getStatus_pencacahan() < 3) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(itemView.getContext());
                        alertDialogBuilder.setTitle("SIEMAS 2022");
                        alertDialogBuilder.setMessage("Untuk dapat melakukan upload selesaikan input pemeriksaan terlebih dulu!");
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else {
                        boolean status_net = getConnectivityStatusString(itemView.getContext());
                        if (!status_net) {
                            Toast.makeText(itemView.getContext(), "Koneksi terputus, periksa koneksi internet anda.", Toast.LENGTH_SHORT).show();
                        } else {
                            ProgressDialog progressDialog = new ProgressDialog(itemView.getContext());
                            progressDialog.setMessage("Mengirim Data");
                            progressDialog.show();

                            user = viewModel.getUser().get(0);
                            JsonElement dsrtJson = new Gson().toJsonTree(dsrt);

                            InterfaceApi interfaceApi = RetrofitClientInstance.getClient().create(InterfaceApi.class);

                            Call<ResponseBody> call = interfaceApi.updateDsrt(dsrtJson.toString(), "Bearer " + user.getToken());
                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.code() == 200) {
                                        try {
                                            String result = response.body().string();
                                            JSONObject jo = new JSONObject(result);
                                            String message = jo.getString("message");
                                            if (message.equals("success")) {
                                                progressDialog.dismiss();
                                                Toast.makeText(itemView.getContext(), "Data berhasil dikirim", Toast.LENGTH_SHORT).show();
                                                viewModel.updateStatusPencacahan(dsrt.getId(), 6);
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

                            List<Dsart> dsartList = viewModel.getDsartbyId(dsrt.getTahun(), dsrt.getSemester(), dsrt.getKd_kab(), dsrt.getKd_kec(), dsrt.getKd_desa(), dsrt.getKd_bs(), dsrt.getNu_rt());
                            for (Dsart dsart : dsartList) {
                                progressDialog.setMessage("Mengirim Data ART");
                                progressDialog.show();
                                JsonElement dsartJson = new Gson().toJsonTree(dsart);
                                Call<ResponseBody> calldsart = interfaceApi.updateDsart(dsartJson.toString(), "Bearer " + user.getToken());
                                calldsart.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.code() == 200) {
                                            try {
                                                String result = response.body().string();
                                                JSONObject jo = new JSONObject(result);
                                                String message = jo.getString("message");
                                                if (message.equals("success")) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(itemView.getContext(), "Data ART berhasil dikirim", Toast.LENGTH_SHORT).show();

                                                } else {
                                                    progressDialog.dismiss();
                                                    Toast toast = Toast.makeText(itemView.getContext(),
                                                            jo.getString("message"),
                                                            Toast.LENGTH_SHORT);
                                                    toast.show();
                                                }
                                            } catch (JSONException | IOException e) {
                                                progressDialog.dismiss();
                                                e.printStackTrace();
                                            }
                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(itemView.getContext(), "Ada kesalahan ART di server", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        progressDialog.dismiss();
                                        Toast.makeText(itemView.getContext(), "Ada kesalahan ART di server", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
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
