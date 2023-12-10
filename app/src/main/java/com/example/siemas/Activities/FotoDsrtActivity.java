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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
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
    public static final String EXTRA_ID = "com.example.siemas.Activities.EXTRA_ID";
    public static final String EXTRA_ID_BS = "com.example.siemas.Activities.EXTRA_ID_BS";
    public static final String EXTRA_KD_KAB = "com.example.siemas.Activities.EXTRA_KD_KAB";
    public static final String EXTRA_KD_KEC = "com.example.siemas.Activities.EXTRA_KD_KEC";
    public static final String EXTRA_KD_DESA = "com.example.siemas.Activities.EXTRA_KD_DESA";
    public static final String EXTRA_KD_BS = "com.example.siemas.Activities.EXTRA_KD_BS";
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

        fotoDsrtAdapter = new FotoDsrtAdapter(viewModel, this);
        recyclerView.setAdapter(fotoDsrtAdapter);

        String kd_kab = this.getIntent().getStringExtra(EXTRA_KD_KAB);
        String kd_kec = this.getIntent().getStringExtra(EXTRA_KD_KEC);
        String kd_desa = this.getIntent().getStringExtra(EXTRA_KD_DESA);
        String kd_bs = this.getIntent().getStringExtra(EXTRA_KD_BS);

        viewModel.getLiveDatafotobyIdbs(periodeList.get(0).getTahun(), periodeList.get(0).getSemester(), kd_kab, kd_kec, kd_desa, kd_bs).observe(this, new Observer<List<Foto>>() {
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
                Intent intent = new Intent(FotoDsrtActivity.this, FotoInput.class);
                intent.putExtra(FotoInput.EXTRA_ID, String.valueOf(foto.getId()));
                intent.putExtra(FotoInput.EXTRA_ID_KAB, foto.getKd_kab());
                intent.putExtra(FotoInput.EXTRA_NKS, foto.getNks());
                intent.putExtra(FotoInput.EXTRA_ID_BS, foto.getId());
                intent.putExtra(FotoInput.EXTRA_NU_RT, String.valueOf(foto.getNu_rt()));
                startActivityForResult(intent, 1);
            }
        });
    }
}
