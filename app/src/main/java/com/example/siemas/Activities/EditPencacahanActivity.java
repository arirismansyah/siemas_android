package com.example.siemas.Activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.Entities.StatusRumah;
import com.example.siemas.RoomDatabase.ViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EditPencacahanActivity extends AppCompatActivity {

    public static final String EXTRA_ID_DSRT = "com.example.siemas.Activities.EXTRA_ID_DSRT";
    public static final String EXTRA_ID_KAB = "com.example.siemas.Activities.EXTRA_ID_KAB";
    public static final String EXTRA_NAMA_KAB = "com.example.siemas.Activities.EXTRA_NAMA_KAB";
    public static final String EXTRA_NKS = "com.example.siemas.Activities.EXTRA_NKS";
    public static final String EXTRA_NAMA_KRT = "com.example.siemas.Activities.EXTRA_NAMA_KRT";
    public static final String EXTRA_ID_BS = "com.example.siemas.Activities.EXTRA_ID_BS";
    public static final String EXTRA_NU_RT = "com.example.siemas.Activities.EXTRA_NU_RT";

    public static final String EXTRA_URI_FOTO = "com.example.wilkerstatmonitoring.Activities.EXTRA_URI_FOTO";

    private boolean isReadPermissionGranted = false;
    private boolean isWritePermissionGranted = false;
    private boolean isCameraPermissionGranted = false;
    ActivityResultLauncher<String[]> mPermissionResultLauncher;
    ActivityResultLauncher<Intent> mGetImage;
    Uri imageUri;

    public static final int CAMERA = 1;
    static final int GALLERY_REQUEST_CODE = 2;

    private String pictureFilePath;
    private String deviceIdentifier;
    private ImageView mImageView;
    private boolean adafoto;

    private GpsTracker gpsTracker;
    private Context mContext;

    private String koordlatitude;
    private String koordlongitude;
    private LocationManager locationManager;
    private double currentLatitude;
    private double currentLongitude;
    private String lokasi = "";

    private TextInputEditText tiKdKab, tinamaKab, tiNks, tiNuRt, tiNamaKrt, tiJmlArt, tiMakananSebulan, tiNonMakananSebulan, tiKoordinat;
    private Spinner spinnerStatusRumah;
    private RadioButton rbGsmpYa, rbGsmpNo;
    private RadioGroup rgGsmp;
    private AppCompatButton batalBtn, simpanBtn, getLocationBtn, getFotoBtn;
    private ViewModel viewModel;
    TextView timerText;
    Handler handler;
    long tMilisec, tStart, tBuff, tUpdate = 0L;
    int sec, min, hour;

    int iddsrt;

    private Dsrt dsrt;
    private Dialog getFotoDialog;
    private AppCompatButton galleryBtn, cameraBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pencacahan);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        mContext = EditPencacahanActivity.this;

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("EDIT PENCACAHAN");
        setSupportActionBar(myToolbar);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(com.example.siemas.RoomDatabase.ViewModel.class);
        tiKdKab = findViewById(R.id.kdKab);
        tinamaKab = findViewById(R.id.namaKab);
        tiNks = findViewById(R.id.nks);
        tiNuRt = findViewById(R.id.nuRt);
        tiNamaKrt = findViewById(R.id.namaKrt);
        tiJmlArt = findViewById(R.id.jmlArt);
        tiMakananSebulan = findViewById(R.id.makananSebulan);
        tiNonMakananSebulan = findViewById(R.id.nonmakananSebulan);
        rgGsmp = findViewById(R.id.rgGsmp);
        rbGsmpYa = findViewById(R.id.radioBtnGsmpYa);
        rbGsmpNo = findViewById(R.id.radioBtnGsmpNo);
        simpanBtn = findViewById(R.id.simpanPencacahanDsrt);
        getLocationBtn = findViewById(R.id.getLocationBtn);
        tiKoordinat = findViewById(R.id.inputLocation);
        getFotoBtn = findViewById(R.id.getFotoBtn);
        batalBtn = findViewById(R.id.batalPencacahanDsrt);
        mImageView = findViewById(R.id.ivFotoRumah);

        iddsrt = Integer.parseInt(this.getIntent().getStringExtra(EXTRA_ID_DSRT));

        dsrt = viewModel.getDsrtById(Integer.parseInt(this.getIntent().getStringExtra(EXTRA_ID_DSRT)));
        tiKdKab.setText(dsrt.getKd_kab());
        tinamaKab.setText(dsrt.getNama_kab());
        tiNks.setText(dsrt.getNks());
        tiNuRt.setText(String.valueOf(dsrt.getNu_rt()));

        List<StatusRumah> statusRumahList = viewModel.getAllStatusRumah();
        spinnerStatusRumah = (Spinner) findViewById(R.id.spinnerStatusRumah);
        List<String> namaStatusRumah = new ArrayList<String>();

        for (int i = 0; i < statusRumahList.size(); i++) {
            namaStatusRumah.add(statusRumahList.get(i).getStatus_rumah());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, namaStatusRumah);
        spinnerStatusRumah.setAdapter(spinnerAdapter);

        // setting value
        // set nama krt
        if (!dsrt.getNama_krt2().isEmpty() && !dsrt.getNama_krt2().equals("null")){
            tiNamaKrt.setText(dsrt.getNama_krt2());
        } else {
            tiNamaKrt.setText(dsrt.getNama_krt());
        }

        // set jumlah art
        tiJmlArt.setText(String.valueOf(dsrt.getJml_art2()));

        // set status rumah
        String statusRumah = dsrt.getStatus_rumah();
        if (!statusRumah.isEmpty() && !statusRumah.equals("null")){
            spinnerStatusRumah.setSelection(spinnerAdapter.getPosition(statusRumah));
        }


        // set makanan sebulan
        tiMakananSebulan.setText(dsrt.getMakanan_sebulan());

        // set non makanan sebulan
        tiNonMakananSebulan.setText(dsrt.getNonmakanan_sebulan());

        // set gsmp
        int gsmpVal = dsrt.getGsmp();
        if (gsmpVal==0){
            rbGsmpNo.setChecked(true);
            rbGsmpYa.setChecked(false);
        } else {
            rbGsmpYa.setChecked(true);
            rbGsmpNo.setChecked(false);
        }

        // set koordinat
        currentLatitude = Double.parseDouble(dsrt.getLatitude());
        currentLongitude = Double.parseDouble(dsrt.getLongitude());
        lokasi = convert(currentLatitude, currentLongitude);
        tiKoordinat.setText(lokasi);

        // set foto
        if (!dsrt.getFoto().isEmpty() && !dsrt.getFoto().equals("null")) {
            mImageView.setImageURI(Uri.parse(dsrt.getFoto()));
        }

        // get foto dialog
        getFotoDialog = new Dialog(this);
        getFotoDialog.setContentView(R.layout.getfoto_dialog);
        getFotoDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        galleryBtn = getFotoDialog.findViewById(R.id.galleryBtn);
        cameraBtn = getFotoDialog.findViewById(R.id.cameraBtn);

        // get location
        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                lokasi = convert(currentLatitude, currentLongitude);
                tiKoordinat.setText(lokasi);
            }
        });

        // batal btn
        batalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // simpan btn
        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dsrt = viewModel.getDsrtById(iddsrt);


                // check is empty
                if (TextUtils.isEmpty(tiNamaKrt.getText())) {
                    tiNamaKrt.setError("Tidak boleh kosong");
                }
                if (TextUtils.isEmpty(tiJmlArt.getText())) {
                    tiJmlArt.setError("Tidak boleh kosong");
                }
                if (TextUtils.isEmpty(tiMakananSebulan.getText())) {
                    tiMakananSebulan.setError("Tidak boleh kosong");
                }
                if (TextUtils.isEmpty(tiNonMakananSebulan.getText())) {
                    tiNonMakananSebulan.setError("Tidak boleh kosong");
                }
                if (TextUtils.isEmpty(tiKoordinat.getText())) {
                    tiKoordinat.setError("Tidak boleh kosong");
                }
                if (rgGsmp.getCheckedRadioButtonId() == -1) {
                    rbGsmpNo.setError("Harus dipilih");
                }

                if (
                        (!TextUtils.isEmpty(tiNamaKrt.getText())) &&
                                (!TextUtils.isEmpty(tiJmlArt.getText())) &&
                                (!TextUtils.isEmpty(tiMakananSebulan.getText())) &&
                                (!TextUtils.isEmpty(tiNonMakananSebulan.getText())) &&
                                (!TextUtils.isEmpty(tiKoordinat.getText())) &&
                                (!(rgGsmp.getCheckedRadioButtonId() == -1))
                ) {


                    int gsmp = 0;
                    if (rbGsmpYa.isChecked()) {
                        gsmp = 1;
                    }

                    String statusRumah = spinnerStatusRumah.getSelectedItem().toString();

                    viewModel.updatePencacahan(
                            dsrt.getId(),
                            tiNamaKrt.getText().toString(),
                            Integer.parseInt(tiJmlArt.getText().toString()),
                            statusRumah,
                            tiMakananSebulan.getText().toString(),
                            tiNonMakananSebulan.getText().toString(),
                            gsmp,
                            String.valueOf(currentLatitude),
                            String.valueOf(currentLongitude),
                            dsrt.getDurasi_pencacahan(),
                            dsrt.getFoto(),
                            1
                    );

                    finish();

                }


            }
        });

        // get foto btn
        getFotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFotoDialog.show();
            }
        });

        // get foto by camera
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFotoDialog.dismiss();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String idDsrt = dsrt.getId_bs() + "_" + dsrt.getNks();
                String imageName = "ssn22_" + idDsrt + "_" + String.valueOf(System.currentTimeMillis());
                Uri imagePath = saveImageToExternalStorage(imageName);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imagePath);
                startActivityForResult(cameraIntent, CAMERA);
            }
        });

        // get foto by gallery
        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFotoDialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                mImageView.setImageURI(imageUri);
                Cursor returnCursor = getContentResolver().query(imageUri, null, null, null, null);
                int imageNameFileIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                returnCursor.moveToFirst();
                String imageFileName = returnCursor.getString(imageNameFileIndex);
                viewModel.updateFotoRumah(dsrt.getId(), imageUri.toString());
            }
        }

        if (requestCode == GALLERY_REQUEST_CODE) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImage);
                Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                pictureFilePath = getRealPathFromURI2(tempUri);
                imageUri = Uri.parse(new File(pictureFilePath).toString());
                Glide.with(this).load(pictureFilePath).into(mImageView);
                viewModel.updateFotoRumah(dsrt.getId(), selectedImage.toString());
            } catch (IOException e) {
                Log.i("TAG", "Some exception " + e);
            }
        }
    }

    private Uri saveImageToExternalStorage(String imageName) {
        Uri imageCollection = null;
        ContentResolver resolver = getContentResolver();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            imageCollection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        } else {
            imageCollection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }


        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, imageName + ".jpg");
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, imageName + ".jpg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        Uri finalImageUri = resolver.insert(imageCollection, contentValues);
        imageUri = finalImageUri;
        return finalImageUri;
    }

    public void getLocation() {
        gpsTracker = new GpsTracker(mContext);
        if (gpsTracker.canGetLocation()) {
            currentLatitude = gpsTracker.getLatitude();
            currentLongitude = gpsTracker.getLongitude();
        } else {
            gpsTracker.showSettingsAlert();
        }
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

        builder.append("??");
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
        builder.append("??");
        builder.append(longitudeSplit[1]);
        builder.append("'");
        builder.append(String.format("%.2f", Double.parseDouble(longitudeSplit[2].replace(",", "."))));
        builder.append("\"");

        return builder.toString();
    }


    protected synchronized String getInstallationIdentifier() {
        if (deviceIdentifier == null) {
            SharedPreferences sharedPrefs = this.getSharedPreferences(
                    "DEVICE_ID", Context.MODE_PRIVATE);
            deviceIdentifier = sharedPrefs.getString("DEVICE_ID", null);
            if (deviceIdentifier == null) {
                deviceIdentifier = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("DEVICE_ID", deviceIdentifier);
                editor.commit();
            }
        }
        return deviceIdentifier;
    }

    public String compressImage(String imageUri, String idresponden) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename(idresponden);
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename(String idresponden) {
        String FileFotoRumah = "SSN_" + idresponden;
        // make sure your target location folder exists!
        File storageDir = new File(Environment.getExternalStorageDirectory(), "SSN1600");
        File targetLocation = new File(storageDir + "/" + FileFotoRumah + ".jpg");
        String uriSting = (targetLocation.getAbsolutePath());
        return uriSting;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    private void hapus_foto_sementara() {
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File[] files = dir.listFiles();

        for (File file : files) {
            if (file.getName().startsWith("TEMP")) {
                // it's a match, call your function
                file.delete();
            }
        }
    }

    private void kompress_file_foto(String idresponden) {
        compressImage(pictureFilePath, idresponden);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI2(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
}