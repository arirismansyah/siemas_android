package com.example.siemas.Activities;

import static android.content.Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.Entities.Foto;
import com.example.siemas.RoomDatabase.ViewModel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FotoInput extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.siemas.Activities.EXTRA_ID";
    public static final String EXTRA_ID_KAB = "com.example.siemas.Activities.EXTRA_ID_KAB";
    public static final String EXTRA_ID_BS = "com.example.siemas.Activities.EXTRA_ID_BS";
    public static final String EXTRA_NKS = "com.example.siemas.Activities.EXTRA_NKS";
    public static final String EXTRA_NU_RT = "com.example.siemas.Activities.EXTRA_NU_RT";
    public static final String EXTRA_URI_FOTO = "com.example.wilkerstatmonitoring.Activities.EXTRA_URI_FOTO";

    private boolean isReadPermissionGranted = false;
    private boolean isWritePermissionGranted = false;
    private boolean isCameraPermissionGranted = false;
    ActivityResultLauncher<String[]> mPermissionResultLauncher;
    ActivityResultLauncher<Intent> mGetImage;
    public Uri imageUri;
    public static final int CAMERA = 1;
    static final int GALLERY_REQUEST_CODE = 2;

    private Foto foto;
    private ViewModel viewModel;
    private ImageView mImageView;
    private Dialog getFotoDialog;
    private AppCompatButton galleryBtn, cameraBtn, kembaliBtn, getLocationBtn, getFotoBtn;
    private Context mContext;
    String currentPhotoPath;
    File photoFile = null;
    byte[] imageBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foto_input);
        // disallowed dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(com.example.siemas.RoomDatabase.ViewModel.class);
//        mContext = FotoInput.this;
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("INPUT FOTO");
        setSupportActionBar(myToolbar);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        kembaliBtn = findViewById(R.id.kembalibtn);
        getFotoBtn = findViewById(R.id.getFotoBtn);
        mImageView = findViewById(R.id.ivFotoRumah);
        getFotoDialog = new Dialog(FotoInput.this);
        getFotoDialog.setContentView(R.layout.getfoto_dialog);
        getFotoDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        galleryBtn = getFotoDialog.findViewById(R.id.galleryBtn);
        cameraBtn = getFotoDialog.findViewById(R.id.cameraBtn);

        foto = viewModel.getFotoById(Integer.parseInt(this.getIntent().getStringExtra(EXTRA_ID)));

        if (foto.getFoto() != null && !foto.getFoto().equals("null")) {
            try {
                imageBytes = foto.getFoto();
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                mImageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                Log.d("Failed Load Image", "Failed Load Image");
            }
        }


        kembaliBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getFotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFotoDialog.show();
            }
        });
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFotoDialog.dismiss();
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Buat file baru untuk menyimpan gambar hasil pengambilan foto
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    // Lanjutkan hanya jika file berhasil dibuat
                    if (photoFile != null) {
                        // Dapatkan URI file dengan menggunakan FileProvider
                        String authorities = getApplicationContext().getPackageName() + ".fileprovider";
                        imageUri = FileProvider.getUriForFile(getApplicationContext(), authorities, photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
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
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA && resultCode == RESULT_OK) {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File(currentPhotoPath);
            Uri contentUri = Uri.fromFile(f);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            try {
                InputStream inputStream = new FileInputStream(f);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[1024 * 8];
                int bytesRead;
                while ((bytesRead = inputStream.read(b)) != -1) {
                    bos.write(b, 0, bytesRead);
                }
                imageBytes = bos.toByteArray();
                imageBytes = compressImageToMaxSize(imageBytes, 30720);
                bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);
            String galleryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/Camera/";
            File galleryFile = new File(galleryPath, photoFile.getName());
            try {
                FileUtils.copyFile(new File(currentPhotoPath), galleryFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            MediaScannerConnection.scanFile(getApplicationContext(),
                    new String[]{galleryFile.getAbsolutePath()},
                    null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.d(TAG, "File " + path + " was scanned successfully");
                        }
                    });
            mImageView.setImageBitmap(bitmap);
            viewModel.updateFotoRumah(foto.getId(), imageBytes);
        }

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                imageBytes = IOUtils.toByteArray(inputStream);
                try {
                    imageBytes = compressImageToMaxSize(imageBytes, 30720 );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ContentValues values = new ContentValues();
                viewModel.updateFotoRumah(foto.getId(), imageBytes);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                mImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


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


    private File createImageFile() throws IOException {
        // Buat nama file gambar dengan timestamp yang unik
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Siemas_" + timeStamp + "_";
        // Buat file gambar di direktori penyimpanan external
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        currentPhotoPath = imageFile.getAbsolutePath();

        return imageFile;
    }

    public static byte[] compressImageToMaxSize(byte[] imageBytes, int maxSize) throws IOException {
        // Load the image into a Bitmap object
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        // Convert the image to JPEG format with 80% quality
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);

        // Reduce the quality of the image until the size is less than or equal to the maximum size
        int quality = 80;
        while (outputStream.toByteArray().length > maxSize && quality == 0) {

            outputStream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
        }

        // Return the compressed image as byte array
        return outputStream.toByteArray();
    }

    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(
                FotoInput.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_REQUEST_CODE);
            }
        } else {
            openGallery();
        }
    }
    private void openGallery() {
        getFotoDialog.dismiss();
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }
}
