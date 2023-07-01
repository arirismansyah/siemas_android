package com.example.siemas.Activities;

import static android.content.Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siemas.Adapter.DsrtPemeriksaanPclAdapter;
import com.example.siemas.Adapter.FotoDsrtAdapter;
import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.Entities.Foto;
import com.example.siemas.RoomDatabase.Entities.Periode;
import com.example.siemas.RoomDatabase.ViewModel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FotoDsrtActivity extends AppCompatActivity {
    public static final String EXTRA_ID_BS = "com.example.siemas.Activities.EXTRA_ID_BS";
    private RecyclerView recyclerView;
    private ViewModel viewModel;
    private FotoDsrtAdapter fotoDsrtAdapter;
    private LinearLayoutCompat containerEmpty;
    private List<Periode> periodeList;
    private Dialog getFotoDialog;
    private AppCompatButton galleryBtn, cameraBtn;

    String currentPhotoPath;
    public static final int CAMERA = 1;
    static final int GALLERY_REQUEST_CODE = 2;
    File photoFile = null;
    public Uri imageUri;
    byte[] imageBytes;

    private Foto foto;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foto_dsrt_activity);
        // disallowed dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("MENU FOTO - DSRT");
        setSupportActionBar(myToolbar);

        containerEmpty = findViewById(R.id.emptyPictContainer);
        recyclerView = findViewById(R.id.recyclerFotoDsrt);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModel.class);
        periodeList = viewModel.getPeriode();
        String idBs = this.getIntent().getStringExtra(EXTRA_ID_BS);
        fotoDsrtAdapter = new FotoDsrtAdapter(viewModel);
        recyclerView.setAdapter(fotoDsrtAdapter);

        viewModel.getLiveDatafotobyIdbs(idBs,periodeList.get(0).getTahun(), periodeList.get(0).getSemester()).observe(this, new Observer<List<Foto>>() {
            @Override
            public void onChanged(List<Foto> fotos) {
                if (fotos.size()>0){
                    recyclerView.setVisibility(View.VISIBLE);
                    containerEmpty.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    containerEmpty.setVisibility(View.VISIBLE);
                }
                fotoDsrtAdapter.setFotoList(fotos);
            }
        });
        fotoDsrtAdapter.setOnItemClickListener(new FotoDsrtAdapter.onItemCLickListener() {
            @Override
            public void onItemClick(Foto foto) {
                getFotoDialog = new Dialog(FotoDsrtActivity.this);
                getFotoDialog.setContentView(R.layout.getfoto_dialog);
                getFotoDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                galleryBtn = getFotoDialog.findViewById(R.id.galleryBtn);
                cameraBtn = getFotoDialog.findViewById(R.id.cameraBtn);

                cameraBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getFotoDialog.dismiss();
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            try {
                                photoFile = createImageFile();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            if (photoFile != null) {
                                String authorities = getApplicationContext().getPackageName() + ".fileprovider";
                                imageUri = FileProvider.getUriForFile(getApplicationContext(), authorities, photoFile);
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                takePictureIntent.putExtra("iddsrt", foto.getId());
                                startActivityForResult(takePictureIntent, CAMERA);
                            }
                        }
                    }
                });

                galleryBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getFotoDialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.addFlags(FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                        intent.setType("image/*");
                        String[] mimeTypes = {"image/jpeg", "image/png"};
                        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                        intent.putExtra("iddsrt", foto.getId());
                        startActivityForResult(intent, GALLERY_REQUEST_CODE);
                    }
                });
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA && resultCode == RESULT_OK) {

            Toast.makeText(getApplicationContext(), data.getExtras().get("iddsrt").toString(), Toast.LENGTH_SHORT);
//            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//            File f = new File(currentPhotoPath);
//            Uri contentUri = Uri.fromFile(f);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//            try {
//                InputStream inputStream = new FileInputStream(f);
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                byte[] b = new byte[1024 * 8];
//                int bytesRead;
//                while ((bytesRead = inputStream.read(b)) != -1) {
//                    bos.write(b, 0, bytesRead);
//                }
//                imageBytes = bos.toByteArray();
//                imageBytes = compressImageToMaxSize(imageBytes, 30720);
//                bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//
//            mediaScanIntent.setData(contentUri);
//            this.sendBroadcast(mediaScanIntent);
//            String galleryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/Camera/";
//            File galleryFile = new File(galleryPath, photoFile.getName());
//            try {
//                FileUtils.copyFile(new File(currentPhotoPath), galleryFile);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            MediaScannerConnection.scanFile(getApplicationContext(),
//                    new String[]{galleryFile.getAbsolutePath()},
//                    null,
//                    new MediaScannerConnection.OnScanCompletedListener() {
//                        @Override
//                        public void onScanCompleted(String path, Uri uri) {
//                            Log.d(TAG, "File " + path + " was scanned successfully");
//                        }
//                    });
//            mImageView.setImageBitmap(bitmap);
//            viewModel.updateFotoRumah(foto.getId(), imageBytes);
        }

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            Toast.makeText(getApplicationContext(), data.getExtras().get("iddsrt").toString(), Toast.LENGTH_SHORT);
//            imageUri = data.getData();
//            try {
//                InputStream inputStream = getContentResolver().openInputStream(imageUri);
//                imageBytes = IOUtils.toByteArray(inputStream);
//                try {
//                    imageBytes = compressImageToMaxSize(imageBytes, 30720 );
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                ContentValues values = new ContentValues();
//                viewModel.updateFotoRumah(foto.getId(), imageBytes);
//                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//                mImageView.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


//            String[] projection = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getContentResolver().query(imageUri, projection, null, null, null);
//            if (cursor != null && cursor.moveToFirst()) {
//                @SuppressLint({"InlinedApi", "Range"}) String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//                cursor.close();
//                // Gunakan File Provider untuk memperoleh URI gambar
//                String authorities = getApplicationContext().getPackageName() + ".fileprovider";
//                imageUri = FileProvider.getUriForFile(this, authorities, new File(imagePath));
//                // Gunakan imageUri untuk memuat gambar
//                viewModel.updateFotoRumah(dsrt.getId(), imageUri.toString());
//                Glide.with(this)
//                        .load(imageUri)
//                        .into(mImageView);
//            } else {
//                Toast.makeText(getApplicationContext(), "gagal Mengambil gambar", Toast.LENGTH_SHORT);
//            }
        }
    }
    public static byte[] compressImageToMaxSize(byte[] imageBytes, int maxSize) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
        int quality = 80;
        while (outputStream.toByteArray().length > maxSize && quality == 0) {

            outputStream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
        }
        return outputStream.toByteArray();
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Siemas_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );
        currentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

}
