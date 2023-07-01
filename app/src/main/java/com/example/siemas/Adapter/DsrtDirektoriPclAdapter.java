package com.example.siemas.Adapter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.location.Location;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siemas.Activities.DirectoryDsrtActivity;
import com.example.siemas.Activities.InputPencacahanActivity;
import com.example.siemas.ImageResizer;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

public class DsrtDirektoriPclAdapter extends RecyclerView.Adapter<DsrtDirektoriPclAdapter.ViewHolder> {
    private List<Dsrt> dsrtList = new ArrayList<>();
    private ViewModel viewModel;

    public DsrtDirektoriPclAdapter(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dsrt_direktori_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

        if (!currentDsrt.getLatitude().isEmpty() && !currentDsrt.getLatitude().equals("null")) {
            Double latitude = Double.parseDouble(currentDsrt.getLatitude());
            Double longitude = Double.parseDouble(currentDsrt.getLongitude());

            String lokasi = convert(latitude, longitude);
            holder.tvLokasi.setText(lokasi);

        } else {
            holder.tvLokasi.setText("-");
        }

        if (!currentDsrt.getDurasi_pencacahan().isEmpty() && !currentDsrt.getDurasi_pencacahan().equals("null")) {
            holder.tvDurasi.setText(currentDsrt.getDurasi_pencacahan());
        } else {
            holder.tvDurasi.setText("-");
        }


        if (statusPencacahan < 3) {
            holder.statusDsrt.setImageResource(R.drawable.ic_tag_notyet);
            holder.tvStatusPemeriksaan.setText("Belum");
            holder.tvStatusPemeriksaan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPemeriksaan.getContext(), R.color.red));
            holder.uploadBtn.setBackgroundColor(ContextCompat.getColor(holder.uploadBtn.getContext(), R.color.dark));
        }

        if (statusPencacahan == 3) {
            holder.tvStatusPemeriksaan.setText("Sudah");
            holder.tvStatusPemeriksaan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPemeriksaan.getContext(), R.color.orange));
            holder.uploadBtn.setBackgroundColor(ContextCompat.getColor(holder.uploadBtn.getContext(), R.color.teal_200));
        }

        if (statusPencacahan > 3) {
            holder.statusDsrt.setImageResource(R.drawable.ic_tag_saved);
            holder.tvStatusPemeriksaan.setText("Terupload");
            holder.tvStatusPemeriksaan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPemeriksaan.getContext(), R.color.teal_200));
            holder.tvStatusPencacahan.setText("Terupload");
            holder.tvStatusPencacahan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPencacahan.getContext(), R.color.teal_200));
            holder.uploadBtn.setBackgroundColor(ContextCompat.getColor(holder.uploadBtn.getContext(), R.color.teal_200));
        }


        if (statusPencacahan > 0 && statusPencacahan <= 3) {
            holder.tvStatusPencacahan.setText("Sudah");
            holder.tvStatusPencacahan.getBackground().setTint(ContextCompat.getColor(holder.tvStatusPencacahan.getContext(), R.color.orange));
        }

        if (statusPencacahan < 1) {
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNks, tvNuRt, tvNamaKrt, tvStatusPencacahan, tvStatusPemeriksaan, tvLokasi, tvDurasi;
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
            ivRumah = itemView.findViewById(R.id.ivRumah);
            uploadBtn = itemView.findViewById(R.id.uploadBtn);
            tvLokasi = itemView.findViewById(R.id.lokasi);
            tvDurasi = itemView.findViewById(R.id.durasi);

            uploadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dsrt.getStatus_pencacahan() < 3) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(itemView.getContext());
                        alertDialogBuilder.setTitle("SIEMAS 2022");
                        alertDialogBuilder.setMessage("Untuk dapat melakukan upload selesaikan input pencacahan & pemeriksaan terlebih dulu!");
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
                            // update data dsrt
                            ProgressDialog progressDialog = new ProgressDialog(itemView.getContext());
                            progressDialog.setMessage("Mengirim Data DSRT");
                            progressDialog.show();
                            user = viewModel.getUser().get(0);
                            Log.d(TAG, "onClick data dsrt: " + dsrt.getMakanan_sebulan_bypml());
                            JsonElement dsrtJson = new Gson().toJsonTree(dsrt);
//                            String fileName = dsrt.getId_bs() + "_" + dsrt.getNks() + "_" + String.valueOf(dsrt.getNu_rt()) + "_" + String.valueOf(dsrt.getId()) + ".jpg";

                            checkAndRequestForPermission(itemView.getContext());
                            String[] proj = {MediaStore.Images.Media.DATA};

                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                            byte[] imageBytes = dsrt.getFoto();
                            byte[] imageBytes = null ;
//                            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//                            bitmap.compress(Bitmap.CompressFormat.PNG, 90, bos);
                            try {
                                imageBytes = compressImageToMaxSize(imageBytes, 30720 );
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), imageBytes);
                            MultipartBody.Part body = MultipartBody.Part.createFormData("file_foto", "image.png", requestBody);

//                            RequestBody file = RequestBody.create(MultipartBody.FORM,"");
//                            MultipartBody.Part body = MultipartBody.Part.createFormData("file","",file);
//                            try {
//                                cursorFile.moveToFirst();
//                                int column_index = cursorFile.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                                String filePath = cursorFile.getString(column_index);
//                                Log.d(TAG, "onClick: "+ filePath);
//                                checkAndRequestForPermission(itemView.getContext());
//                                Bitmap fullSizeBitmap = BitmapFactory.decodeFile(filePath);
//                                Bitmap reducedBitmap = ImageResizer.reduceBitmapSize(fullSizeBitmap, 2073600);
//                                File reducedFile = getBitmapReduced(reducedBitmap, fileName, itemView.getContext());
//                                RequestBody requestFile = RequestBody.create(reducedFile, MediaType.parse("image/*"));
//                                body = MultipartBody.Part.createFormData("file_foto", reducedFile.getName(), requestFile);
//
//                            } catch (Exception e) {
//                                e.printStackTrace()
//                                ;
//                            }

                            RequestBody dsrtBody = RequestBody.create(dsrtJson.toString(), MediaType.parse("text/plain"));
                            InterfaceApi interfaceApi = RetrofitClientInstance.getClient().create(InterfaceApi.class);
                            Call<ResponseBody> call = interfaceApi.upload_data("Bearer " + user.getToken(), body, dsrtBody);

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
                                                Toast.makeText(itemView.getContext(), "Data RT berhasil dikirim", Toast.LENGTH_SHORT).show();
                                                kirim_art(dsrt, itemView,user,interfaceApi);
                                                viewModel.updateStatusPencacahan(dsrt.getId(), 4);
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
                                        Toast.makeText(itemView.getContext(), "Ada kesalahan DSRT di server", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    progressDialog.dismiss();
                                    Toast.makeText(itemView.getContext(), "Ada kesalahan DSRT di server", Toast.LENGTH_SHORT).show();
                                }
                            });

//                            Call<ResponseBody> call = interfaceApi.updateDsrt(dsrtJson.toString(), "Bearer " + user.getToken());
//                            call.enqueue(new Callback<ResponseBody>() {
//                                @Override
//                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                    if (response.code()==200){
//                                        try {
//                                            String result = response.body().string();
//                                            JSONObject jo = new JSONObject(result);
//                                            String message = jo.getString("message");
//                                            if (message.equals("success")) {
//                                                progressDialog.dismiss();
//                                                Toast.makeText(itemView.getContext(), "Data berhasil dikirim", Toast.LENGTH_SHORT).show();
//
//                                                // upload foto
//                                                ProgressDialog progressDialog2 = new ProgressDialog(itemView.getContext());
//                                                progressDialog2.setMessage("Upload Foto");
//                                                progressDialog2.show();
//
//                                                String fileName = dsrt.getId_bs()+"_"+ dsrt.getNks()+"_"+dsrt.getNu_rt()+"_"+String.valueOf(dsrt.getId())+".jpg";
//                                                String [] proj = {MediaStore.Images.Media.DATA};
//                                                Cursor cursorFile = itemView.getContext().getContentResolver().query(Uri.parse(dsrt.getFoto()), proj, null, null, null);
//                                                int column_index = cursorFile.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                                                cursorFile.moveToFirst();
//                                                String filePath = cursorFile.getString(column_index);
//
//                                                Bitmap fullSizeBitmap = BitmapFactory.decodeFile(filePath);
//
//                                                Bitmap reducedBitmap = ImageResizer.reduceBitmapSize(fullSizeBitmap, 2073600);
//
//                                                File reducedFile = getBitmapReduced(reducedBitmap, fileName, itemView.getContext());
//
//                                                RequestBody id_dsrt = RequestBody.create(String.valueOf(dsrt.getId()), MediaType.parse("text/plain"));
//                                                RequestBody requestFile = RequestBody.create(reducedFile, MediaType.parse("image/*"));
//                                                MultipartBody.Part body = MultipartBody.Part.createFormData("file_foto", reducedFile.getName(), requestFile);
//
//                                                Call<ResponseBody> call2 = interfaceApi.upload_foto("Bearer "+user.getToken(), body, id_dsrt);
//                                                call2.enqueue(new Callback<ResponseBody>() {
//                                                    @Override
//                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                                        if (response.code()==200){
//                                                            try {
//                                                                String result = response.body().string();
//                                                                JSONObject jo  = new JSONObject(result);
//                                                                String message = jo.getString("message");
//                                                                if(message.equals("success")){
//                                                                    progressDialog2.dismiss();
//                                                                    Toast.makeText(itemView.getContext(), "Foto berhasil diupload", Toast.LENGTH_SHORT).show();
//                                                                    viewModel.updateStatusPencacahan(dsrt.getId(), 4);
//
//                                                                }else{
//                                                                    progressDialog2.dismiss();
//                                                                    Toast toast = Toast.makeText(itemView.getContext(),
//                                                                            jo.getString("message"),
//                                                                            Toast.LENGTH_SHORT);
//                                                                    toast.show();
//                                                                }
//                                                            } catch (JSONException e){
//                                                                e.printStackTrace();
//                                                            } catch (IOException e) {
//                                                                e.printStackTrace();
//                                                            }
//                                                        } else {
//                                                            progressDialog2.dismiss();
//                                                            Toast.makeText(itemView.getContext(), response.message(), Toast.LENGTH_SHORT).show();
//                                                        }
//                                                    }
//
//                                                    @Override
//                                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                                                    }
//                                                });
//
//                                            } else {
//                                                progressDialog.dismiss();
//                                                Toast.makeText(itemView.getContext(), "Ada kesalahan di server", Toast.LENGTH_SHORT).show();
//                                            }
//                                        } catch (JSONException | IOException e) {
//                                            progressDialog.dismiss();
//                                            e.printStackTrace();
//                                        }
//                                    } else {
//                                        progressDialog.dismiss();
//                                        Toast.makeText(itemView.getContext(), "Ada kesalahan di server", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                    progressDialog.dismiss();
//                                    Toast.makeText(itemView.getContext(), "Ada kesalahan di server", Toast.LENGTH_SHORT).show();
//                                }
//                            });
                        }
                    }
                }
            });
        }
    }

    private void kirim_art(Dsrt dsrt,View itemView, User user, InterfaceApi interfaceApi){
        List<Dsart> dsartList = viewModel.getDsartbyId(dsrt.getTahun(), dsrt.getSemester(),dsrt.getKd_kab(), dsrt.getKd_kec(), dsrt.getKd_desa(), dsrt.getKd_bs() , dsrt.getNu_rt());
        for (Dsart dsart : dsartList) {
            ProgressDialog progressDialog = new ProgressDialog(itemView.getContext());
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
                                viewModel.updateStatusPencacahan(dsrt.getId(), 4);
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

    private void checkAndRequestForPermission(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(context, "Permission Dennied", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        } else {

        }
    }

    public static byte[] compressImageToMaxSize(byte[] imageBytes, int maxSize) throws IOException {
        // Load the image into a Bitmap object
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        // Convert the image to JPEG format with 80% quality
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);

        // Reduce the quality of the image until the size is less than or equal to the maximum size
        int quality = 80;
        while (outputStream.toByteArray().length > maxSize) {
            quality -= 10;
            outputStream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
        }

        // Return the compressed image as byte array
        return outputStream.toByteArray();
    }

    private File getBitmapReduced(Bitmap reducedFoto, String filename, Context context) {
        File imageStorage = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File mFolder = new File(Environment.getExternalStorageDirectory(), "SSN1600");
//        String TARGET_BASE_PATH = mFolder.getAbsolutePath();
//        if (!mFolder.exists()) {
//            boolean b =  mFolder.mkdirs();
//        }
        File file = new File(imageStorage, filename);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        reducedFoto.compress(Bitmap.CompressFormat.JPEG, 60, bos);
        byte[] bitmapData = bos.toByteArray();
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapData);
            fos.flush();
            fos.close();
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
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

    private String convert(double latitude, double longitude) {
        StringBuilder builder = new StringBuilder();
        if (latitude < 0) {
            builder.append("S ");
        } else {
            builder.append("N ");
        }

        String latitudeDegrees = Location.convert(Math.abs(latitude), Location.FORMAT_SECONDS);
        String[] latitudeSplit = latitudeDegrees.split(":");

        builder.append("°");
        builder.append(latitudeSplit[1]);
        builder.append("'");
        builder.append(String.format("%.2f", Double.parseDouble(latitudeSplit[2].replace(",", "."))));
        builder.append("\"");
        builder.append(" ");

        if (longitude < 0) {
            builder.append("W ");
        } else {
            builder.append("E ");
        }

        String longitudeDegrees = Location.convert(Math.abs(longitude), Location.FORMAT_SECONDS);
        String[] longitudeSplit = longitudeDegrees.split(":");
        builder.append(longitudeSplit[0]);
        builder.append("°");
        builder.append(longitudeSplit[1]);
        builder.append("'");
        builder.append(String.format("%.2f", Double.parseDouble(longitudeSplit[2].replace(",", "."))));
        builder.append("\"");

        return builder.toString();
    }
}
