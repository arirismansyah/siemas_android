package com.example.siemas.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.Entities.KegiatanUtama;
import com.example.siemas.RoomDatabase.Entities.Pendidikan;
import com.example.siemas.RoomDatabase.Entities.StatusRumah;
import com.example.siemas.RoomDatabase.ViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class InputPemeriksaanPclActivity extends AppCompatActivity {
    public static final String EXTRA_ID_DSRT = "com.example.siemas.Activities.EXTRA_ID_DSRT";
    public static final String EXTRA_ID_KAB = "com.example.siemas.Activities.EXTRA_ID_KAB";
    public static final String EXTRA_NAMA_KAB = "com.example.siemas.Activities.EXTRA_NAMA_KAB";
    public static final String EXTRA_NKS = "com.example.siemas.Activities.EXTRA_NKS";
    public static final String EXTRA_NAMA_KRT = "com.example.siemas.Activities.EXTRA_NAMA_KRT";
    public static final String EXTRA_ID_BS = "com.example.siemas.Activities.EXTRA_ID_BS";
    public static final String EXTRA_NU_RT = "com.example.siemas.Activities.EXTRA_NU_RT";

    private TextInputEditText tiKdKab, tinamaKab, tiNks, tiNuRt, tiNamaKrt, tiJmlArt, tiRincian16_5, tiRincian8_3, tiRincian304, tiRincian305, tiJmlArtSekolah, tiJmlArtBpjs, tiDeskripsiUsaha, tiLuasLantai;
    private Spinner spinnerIjazahKrt, spinnerKegiatanKrt;
    private AppCompatButton batalBtn, simpanBtn;
    private ViewModel viewModel;
    private Dsrt dsrt;

    private List<Pendidikan> pendidikanList;
    private List<KegiatanUtama> kegiatanUtamaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pemeriksaan_pcl);

        // disallowed dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        // setting viewmodel
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(com.example.siemas.RoomDatabase.ViewModel.class);

        // find elements
        tiKdKab = findViewById(R.id.kdKab);
        tinamaKab = findViewById(R.id.namaKab);
        tiNks = findViewById(R.id.nks);
        tiNuRt = findViewById(R.id.nuRt);
        tiNamaKrt = findViewById(R.id.namaKrt);
        tiJmlArt = findViewById(R.id.jmlArt);
        tiRincian16_5 = findViewById(R.id.rincian16_5);
        tiRincian8_3 = findViewById(R.id.rincian8_3);
        tiRincian304 = findViewById(R.id.rincian304);
        tiRincian305 = findViewById(R.id.rincian305);
        tiJmlArtSekolah = findViewById(R.id.jmlArtSekolah);
        tiJmlArtBpjs = findViewById(R.id.jmlArtBpjs);
        tiDeskripsiUsaha = findViewById(R.id.deskripsiKegiatanKrt);
        tiLuasLantai = findViewById(R.id.luasLantai);

        batalBtn = findViewById(R.id.batalPemeriksaanPclDsrt);
        simpanBtn = findViewById(R.id.simpanPemeriksaanPclDsrt);

        spinnerIjazahKrt = (Spinner) findViewById(R.id.spinnerIjazahKrt);
        spinnerKegiatanKrt = (Spinner) findViewById(R.id.spinnerKegiatanKrt);

        // define dsrt
        dsrt = viewModel.getDsrtById(Integer.parseInt(this.getIntent().getStringExtra(EXTRA_ID_DSRT)));

        // setting toolbar
        if (dsrt.getStatus_pencacahan()<3){
            Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
            myToolbar.setTitle("INPUT PEMERIKSAAN - PCL");
            setSupportActionBar(myToolbar);
        } else {
            Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
            myToolbar.setTitle("EDIT PEMERIKSAAN - PCL");
            setSupportActionBar(myToolbar);
        }


        // mount spinner pendidikan
        pendidikanList = viewModel.getAllPendidikan();
        List<String> namaPendidikan = new ArrayList<String>();

        for (int i = 0; i < pendidikanList.size(); i++) {
            namaPendidikan.add(pendidikanList.get(i).getPendidikan());
        }

        ArrayAdapter<String> spinnerIjazahAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, namaPendidikan);
        spinnerIjazahKrt.setAdapter(spinnerIjazahAdapter);

        // mount spinner kegiatan
        kegiatanUtamaList = viewModel.getAllKegiatan();
        List<String> namaKegiatan = new ArrayList<>();
        for (int i = 0; i < kegiatanUtamaList.size(); i++) {
            namaKegiatan.add(kegiatanUtamaList.get(i).getKegiatan_utama());
        }
        ArrayAdapter<String> spinnerKegiatanAdapter = new ArrayAdapter<String>(this, R.layout.spinner_textview, namaKegiatan);
        spinnerKegiatanKrt.setAdapter(spinnerKegiatanAdapter);

        // set input value
        tiKdKab.setText(dsrt.getKd_kab());
        tinamaKab.setText(dsrt.getNama_kab());
        tiNks.setText(dsrt.getNks());
        tiNuRt.setText(String.valueOf(dsrt.getNu_rt()));

        if (!dsrt.getNama_krt2().isEmpty() && !dsrt.getNama_krt2().equals("null")) {
            tiNamaKrt.setText(dsrt.getNama_krt2());
        } else {
            tiNamaKrt.setText(dsrt.getNama_krt());
        }

        // set jumlah art
        tiJmlArt.setText(String.valueOf(dsrt.getJml_art2()));

        // set blok 16.5
        String makananVal = dsrt.getMakanan_sebulan();
        if (!makananVal.isEmpty() && !makananVal.equals("null")){
            tiRincian16_5.setText(makananVal);
        }

        // set blok 8.3
        String nonMakananVal = dsrt.getNonmakanan_sebulan();
        if(!nonMakananVal.isEmpty() && !nonMakananVal.equals("null")){
            tiRincian8_3.setText(nonMakananVal);
        }

        // set rincian 304
        String transportasiVal = dsrt.getTransportasi();
        if (!transportasiVal.isEmpty() && !transportasiVal.equals("null")){
            tiRincian304.setText(transportasiVal);
        }

        // set rincian 305
        String peliharaanVal = dsrt.getPeliharaan();
        if (!peliharaanVal.isEmpty() && !peliharaanVal.equals("null")){
            tiRincian305.setText(peliharaanVal);
        }

        // set art sekolah
        String artSekolahVal = String.valueOf(dsrt.getArt_sekolah());
        if (!artSekolahVal.isEmpty() && !artSekolahVal.equals("null") && (dsrt.getArt_sekolah()>0)){
            tiJmlArtSekolah.setText(artSekolahVal);
        }

        // set art bpjs
        String artBpjsVal = String.valueOf(dsrt.getArt_bpjs());
        if (!artBpjsVal.isEmpty() && !artBpjsVal.equals("null") && (dsrt.getArt_bpjs()>0)){
            tiJmlArtBpjs.setText(artBpjsVal);
        }

        // set pendidikan
        String pendidikanVal = dsrt.getIjazah_krt();
        if (!pendidikanVal.isEmpty() && !pendidikanVal.equals("null")){
            spinnerIjazahKrt.setSelection(spinnerIjazahAdapter.getPosition(pendidikanVal));
        }

        // set kegiatan
        String kegiatanVal = dsrt.getKegiatan_seminggu();
        if (!kegiatanVal.isEmpty() && !kegiatanVal.equals("null")){
            spinnerKegiatanKrt.setSelection(spinnerKegiatanAdapter.getPosition(kegiatanVal));
        }

        // set deskripsi
        String deskripsiVal = dsrt.getDeskripsi_kegiatan();
        if (!deskripsiVal.isEmpty() && !deskripsiVal.equals("null")){
            tiDeskripsiUsaha.setText(deskripsiVal);
        }

        // set luas lantai
        String luasLantaiVal = String.valueOf(dsrt.getLuas_lantai());
        if (!luasLantaiVal.isEmpty() && !luasLantaiVal.equals("null") && (dsrt.getLuas_lantai()>0)){
            tiLuasLantai.setText(luasLantaiVal);
        }

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
                // check empty
                if (TextUtils.isEmpty(tiNamaKrt.getText())) {
                    tiNamaKrt.setError("Tidak boleh kosong");
                }
                if (TextUtils.isEmpty(tiJmlArt.getText())) {
                    tiJmlArt.setError("Tidak boleh kosong");
                }
                if (TextUtils.isEmpty(tiRincian16_5.getText())) {
                    tiRincian16_5.setError("Tidak boleh kosong");
                }
                if (TextUtils.isEmpty(tiRincian8_3.getText())) {
                    tiRincian8_3.setError("Tidak boleh kosong");
                }
                if (TextUtils.isEmpty(tiRincian304.getText())) {
                    tiRincian304.setError("Tidak boleh kosong");
                }
                if (TextUtils.isEmpty(tiRincian305.getText())) {
                    tiRincian305.setError("Tidak boleh kosong");
                }
                if (TextUtils.isEmpty(tiJmlArtSekolah.getText())) {
                    tiJmlArtSekolah.setError("Tidak boleh kosong");
                }
                if (TextUtils.isEmpty(tiJmlArtBpjs.getText())) {
                    tiJmlArtBpjs.setError("Tidak boleh kosong");
                }
                if (TextUtils.isEmpty(tiDeskripsiUsaha.getText())) {
                    tiDeskripsiUsaha.setError("Tidak boleh kosong");
                }
                if (TextUtils.isEmpty(tiLuasLantai.getText())) {
                    tiLuasLantai.setError("Tidak boleh kosong");
                }

                if (
                        (!TextUtils.isEmpty(tiNamaKrt.getText())) &&
                                (!TextUtils.isEmpty(tiJmlArt.getText())) &&
                                (!TextUtils.isEmpty(tiRincian16_5.getText())) &&
                                (!TextUtils.isEmpty(tiRincian8_3.getText())) &&
                                (!TextUtils.isEmpty(tiRincian304.getText())) &&
                                (!TextUtils.isEmpty(tiRincian305.getText())) &&
                                (!TextUtils.isEmpty(tiJmlArtSekolah.getText())) &&
                                (!TextUtils.isEmpty(tiJmlArtBpjs.getText())) &&
                                (!TextUtils.isEmpty(tiDeskripsiUsaha.getText())) &&
                                (!TextUtils.isEmpty(tiLuasLantai.getText())) &&
                                (spinnerIjazahKrt != null) && (spinnerKegiatanKrt != null) &&
                                (spinnerIjazahKrt.getSelectedItem() != null) && (spinnerKegiatanKrt.getSelectedItem()!=null)
                ) {

                    String ijazah = spinnerIjazahKrt.getSelectedItem().toString();
                    String kegiatan = spinnerKegiatanKrt.getSelectedItem().toString();
                    String trasnportasi = tiRincian304.getText().toString();
                    String peliharaan = tiRincian305.getText().toString();

                    try {

                        viewModel.updatePemeriksaan(
                                dsrt.getId(),
                                tiNamaKrt.getText().toString(),
                                Integer.parseInt(tiJmlArt.getText().toString()),
                                tiRincian16_5.getText().toString(),
                                tiRincian8_3.getText().toString(),
                                trasnportasi,
                                peliharaan,
                                Integer.parseInt(tiJmlArtSekolah.getText().toString()),
                                Integer.parseInt(tiJmlArtBpjs.getText().toString()),
                                ijazah,
                                kegiatan,
                                tiDeskripsiUsaha.getText().toString(),
                                Integer.parseInt(tiLuasLantai.getText().toString()),
                                3
                        );

                        finish();

                    } catch (Error e){
                        Toast toast = Toast.makeText(getApplicationContext(),
                                e.toString(),
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }


                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InputPemeriksaanPclActivity.this);
                    alertDialogBuilder.setTitle("SIEMAS 2022");
                    alertDialogBuilder.setMessage("Ada isian yang belum terisi. Pastikan semua isian terisi!");
                    alertDialogBuilder.setCancelable(false);

                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });


    }
}