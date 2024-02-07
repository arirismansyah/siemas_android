package com.example.siemas.Adapter;

import static android.content.Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siemas.Activities.FotoDsrtActivity;
import com.example.siemas.InterfaceApi;
import com.example.siemas.R;
import com.example.siemas.RetrofitClientInstance;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.Entities.Foto;
import com.example.siemas.RoomDatabase.Entities.User;
import com.example.siemas.RoomDatabase.ViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FotoDsrtAdapter extends RecyclerView.Adapter<FotoDsrtAdapter.ViewHolder> {
    private ViewModel viewModel;
    private Activity mActivity;

    private List<Foto> fotoList = new ArrayList<>();
    private onItemCLickListener listener;
    private AppCompatButton galleryBtn, cameraBtn;
    private Dialog getFotoDialog;

    String currentPhotoPath;
    public static final int CAMERA = 1;
    static final int GALLERY_REQUEST_CODE = 2;
    File photoFile = null;
    public Uri imageUri;

    public int fotoid;

    public FotoDsrtAdapter(ViewModel viewModel, Activity activity) {
        this.viewModel = viewModel;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.foto_dsrt_list_item, parent, false);
        return new FotoDsrtAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Foto currentFoto = fotoList.get(position);
        holder.foto = currentFoto;
        Dsrt dsrt = viewModel.getDsrtById(currentFoto.getId());

        if (!dsrt.getNama_krt_cacah().isEmpty() && !dsrt.getNama_krt_cacah().equals("null")) {
            holder.tvNamaKrt.setText("Nama KRT: " + dsrt.getNama_krt_cacah());
        } else {
            holder.tvNamaKrt.setText("Nama KRT: " + dsrt.getNama_krt_prelist());
        }
        holder.tvNuRt.setText("No Urut Ruta: " + String.valueOf(currentFoto.getNu_rt()));
        holder.tvNks.setText("NKS: " + currentFoto.getNks());

        if (currentFoto.getFoto() != null) {
            try {
                byte[] imageBytes = currentFoto.getFoto();
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                holder.ivRumah.setImageBitmap(bitmap);
            } catch (Exception e) {
                Log.d("Failed Load Image", "Failed Load Image");
            }
        } else {
            holder.ivRumah.setImageResource(R.drawable.ic_home);
        }

        int status_foto = currentFoto.getStatus_foto();
        if (status_foto == 0) {
            holder.statusDsrt.setImageResource(R.drawable.ic_tag_notyet);
            holder.tvStatusFoto.setText("belum Upload");
            holder.tvStatusFoto.getBackground().setTint(ContextCompat.getColor(holder.tvStatusFoto.getContext(), R.color.red));
            holder.uploadBtn.setBackgroundColor(ContextCompat.getColor(holder.uploadBtn.getContext(), R.color.dark));
        }
        if (status_foto == 1) {
            holder.statusDsrt.setImageResource(R.drawable.ic_tag_saved);
            holder.tvStatusFoto.setText("Sudah Upload");
            holder.tvStatusFoto.getBackground().setTint(ContextCompat.getColor(holder.tvStatusFoto.getContext(), R.color.teal_200));
            holder.uploadBtn.setBackgroundColor(ContextCompat.getColor(holder.uploadBtn.getContext(), R.color.teal_200));
        }
        if (status_foto == 2) {
            holder.statusDsrt.setImageResource(R.drawable.ic_tag_saved);
            holder.tvStatusFoto.setText("Tersimpan di Server");
            holder.tvStatusFoto.getBackground().setTint(ContextCompat.getColor(holder.tvStatusFoto.getContext(), R.color.teal_200));
            holder.uploadBtn.setBackgroundColor(ContextCompat.getColor(holder.uploadBtn.getContext(), R.color.teal_200));
        }
    }

    @Override
    public int getItemCount() {
        return fotoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNks, tvNuRt, tvNamaKrt, tvStatusFoto;
        private ImageView statusDsrt, ivRumah;
        private ImageButton uploadBtn;
        Foto foto;
        private User user;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            tvNks = itemView.findViewById(R.id.nks);
            tvNuRt = itemView.findViewById(R.id.nuRt);
            tvNamaKrt = itemView.findViewById(R.id.namaKrt);
            statusDsrt = itemView.findViewById(R.id.tagStatusDsrt);
            tvStatusFoto = itemView.findViewById(R.id.tvStatusFoto);
            ivRumah = itemView.findViewById(R.id.ivRumah);
            uploadBtn = itemView.findViewById(R.id.uploadBtn);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(fotoList.get(position));
                    }
                }
            });
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    if (listener != null && position != RecyclerView.NO_POSITION) {
//                        listener.onItemClick(fotoList.get(position));
//                        Log.d("fotoDsrtAdapter", "onclick");
//                        getFotoDialog = new Dialog(itemView.getContext());
//                        getFotoDialog.setContentView(R.layout.getfoto_dialog);
//                        getFotoDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                        galleryBtn = getFotoDialog.findViewById(R.id.galleryBtn);
//                        cameraBtn = getFotoDialog.findViewById(R.id.cameraBtn);
//
//                        cameraBtn.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                getFotoDialog.dismiss();
//                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                if (takePictureIntent.resolveActivity(view.getContext().getPackageManager()) != null) {
//                                    try {
//                                        photoFile = createImageFile();
//                                    } catch (IOException ex) {
//                                        ex.printStackTrace();
//                                    }
//                                    if (photoFile != null) {
//                                        String authorities = view.getContext().getPackageName() + ".fileprovider";
//                                        imageUri = FileProvider.getUriForFile(view.getContext(), authorities, photoFile);
//                                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                                        // Menambahkan ID DSRT ke intent
//                                        takePictureIntent.putExtra(FotoDsrtActivity.EXTRA_ID, foto.getId());
////                                        Toast.makeText(itemView.getContext(), foto.getId(), Toast.LENGTH_SHORT);
//                                        fotoid = foto.getId();
//                                        mActivity.startActivityForResult(takePictureIntent, CAMERA);
//                                    }
//                                }
//                            }
//                        });
//
//                        galleryBtn.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                getFotoDialog.dismiss();
//                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                                intent.addFlags(FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
//                                intent.setType("image/*");
//                                String[] mimeTypes = {"image/jpeg", "image/png"};
//                                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
//                                // Menambahkan ID DSRT ke intent
//                                intent.putExtra(FotoDsrtActivity.EXTRA_ID, foto.getId());
////                                Toast.makeText(itemView.getContext(), foto.getId(), Toast.LENGTH_SHORT);
////                                startActivityForResult(intent, GALLERY_REQUEST_CODE);
//                                fotoid = foto.getId();
//                                mActivity.startActivityForResult(intent, GALLERY_REQUEST_CODE);
//                            }
//                        });
//
//                        getFotoDialog.show();
//                    }
//                }
//            });
            uploadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Objects.requireNonNull(foto.getFoto()).length == 0) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(itemView.getContext());
                        alertDialogBuilder.setTitle("SIEMAS 2024");
                        alertDialogBuilder.setMessage("Untuk dapat melakukan upload input terlebih dahulu foto!");
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }
                    else {
                        boolean status_net = getConnectivityStatusString(itemView.getContext());
                        if (!status_net) {
                            Toast.makeText(itemView.getContext(), "Koneksi terputus, periksa koneksi internet anda.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            ProgressDialog progressDialog = new ProgressDialog(itemView.getContext());
                            progressDialog.setMessage("Mengirim Data");
                            progressDialog.show();
                            user = viewModel.getUser().get(0);
                            InterfaceApi interfaceApi = RetrofitClientInstance.getClient().create(InterfaceApi.class);
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            byte[] imageBytes = foto.getFoto();
//                            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//                            bitmap.compress(Bitmap.CompressFormat.PNG, 90, bos);
                            try {
                                int maxSizeBytes = 1 * 1024 * 1024; // 5 MB
                                imageBytes = compressImageToMaxSize(imageBytes, maxSizeBytes, 500,500);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), imageBytes);
                            MultipartBody.Part body = MultipartBody.Part.createFormData("file_foto", "image.png", requestBody);
                            Call<ResponseBody> call = interfaceApi.upload_foto("Bearer " + user.getToken(), body, foto.getId() );
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
                                                Toast.makeText(itemView.getContext(), "Foto berhasil dikirim", Toast.LENGTH_SHORT).show();
                                                viewModel.updateStatusFoto(foto.getId(), 1);
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
                        }
                    }
                }
            });
        }
    }

    public interface onItemCLickListener {
        void onItemClick(Foto foto);
    }

    public void setOnItemClickListener(onItemCLickListener listener) {
        this.listener = listener;
    }

    public void setFotoList(List<Foto> fotoList) {
        this.fotoList = fotoList;
        notifyDataSetChanged();
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

    public byte[] compressImageToMaxSize(byte[] imageBytes, int maxSize) throws IOException {
        // Load the image into a Bitmap object
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        // Convert the image to JPEG format with 80% quality
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
        // Reduce the quality of the image until the size is less than or equal to the maximum size
        int quality = 80;
        while (outputStream.toByteArray().length > maxSize & quality != 0) {
            quality -= 10;
            outputStream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
        }
        // Return the compressed image as byte array
        return outputStream.toByteArray();
    }

    public byte[] compressImageToMaxSize(byte[] imageBytes, int maxSize, int maxWidth, int maxHeight) throws IOException {
        // Load the image into a Bitmap object
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        // Get the current dimensions of the bitmap
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // Calculate the new dimensions to fit within the specified maxWidth and maxHeight
        float aspectRatio = (float) width / height;
        if (width > maxWidth || height > maxHeight) {
            if (aspectRatio > 1) {
                width = maxWidth;
                height = (int) (width / aspectRatio);
            } else {
                height = maxHeight;
                width = (int) (height * aspectRatio);
            }
        }
        // Resize the bitmap to the new dimensions
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        // Convert the image to JPEG format with 80% quality
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
        // Reduce the quality of the image until the size is less than or equal to the maximum size
        int quality = 80;
        while (outputStream.toByteArray().length > maxSize && quality != 0) {
            quality -= 10;
            outputStream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
        }
        // Return the compressed image as byte array
        return outputStream.toByteArray();
    }
}
