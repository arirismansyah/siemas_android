package com.example.siemas.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.siemas.Adapter.InputPemeriksaanPCLAdapter;
import com.example.siemas.Adapter.InputPemeriksaanPMLAdapter;
import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.Entities.KegiatanUtama;
import com.example.siemas.RoomDatabase.Entities.Pendidikan;
import com.example.siemas.RoomDatabase.ViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InputPemeriksaanPmlActivity extends AppCompatActivity {
    public static final String EXTRA_ID_DSRT = "com.example.siemas.Activities.EXTRA_ID_DSRT";
    public static final String EXTRA_ID_BS = "com.example.siemas.Activities.EXTRA_ID_BS";
    public static final String EXTRA_KD_KAB = "com.example.siemas.Activities.EXTRA_KD_KAB";
    public static final String EXTRA_KD_KEC = "com.example.siemas.Activities.EXTRA_KD_KEC";
    public static final String EXTRA_KD_DESA = "com.example.siemas.Activities.EXTRA_KD_DESA";
    public static final String EXTRA_KD_BS = "com.example.siemas.Activities.EXTRA_KD_BS";

    private TextInputEditText tiKdKab, tinamaKab, tiNks, tiNuRt, tiNamaKrt, tiJmlArt, tijml_kom_makanan, tijml_kom_nonmakanan, tiRincian16_5, tiRincian8_3, tiRincian16_5_bypml, tiRincian8_3_bypml, tiRincian304, tiRincian305, tiJmlArtSekolah, tiJmlArtBpjs, tiDeskripsiUsaha, tiLuasLantai;
    private Spinner spinnerIjazahKrt, spinnerKegiatanKrt;
    private AppCompatButton batalBtn, simpanBtn;
    private ViewModel viewModel;
    private Dsrt dsrt;
    private RecyclerView recyclerView;
    private List<Pendidikan> pendidikanList;
    private List<KegiatanUtama> kegiatanUtamaList;
    private InputPemeriksaanPMLAdapter inputPemeriksaanPMLAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.siemas.R.layout.activity_input_pemeriksaan_pml);

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
        tijml_kom_makanan = findViewById(R.id.jml_komoditas_makanan);
        tijml_kom_nonmakanan = findViewById(R.id.jml_komoditas_nonmakanan);
        tiRincian16_5 = findViewById(R.id.rincian16_5);
        tiRincian8_3 = findViewById(R.id.rincian8_3);
        tiRincian16_5_bypml = findViewById(R.id.rincian16_5_bypml);
        tiRincian8_3_bypml = findViewById(R.id.rincian8_3_bypml);
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
        recyclerView = findViewById(R.id.dssartrecycler);
        // setting toolbar
        if (dsrt.getStatus_pencacahan()<5){
            Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
            myToolbar.setTitle("INPUT PEMERIKSAAN - PML");
            setSupportActionBar(myToolbar);
        } else {
            Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
            myToolbar.setTitle("EDIT PEMERIKSAAN - PML");
            setSupportActionBar(myToolbar);
        }

        // mount spinner pendidikan
        pendidikanList = viewModel.getAllPendidikan();
        List<String> namaPendidikan = new ArrayList<String>();

        for (int i = 0; i < pendidikanList.size(); i++) {
            namaPendidikan.add(pendidikanList.get(i).getPendidikan());
        }

        ArrayAdapter<String> spinnerIjazahAdapter = new ArrayAdapter<String>(this, R.layout.spinner_textview, namaPendidikan);
        spinnerIjazahKrt.setAdapter(spinnerIjazahAdapter);

        // mount spinner kegiatan
        kegiatanUtamaList = viewModel.getAllKegiatan();
        List<String> namaKegiatan = new ArrayList<>();
        for (int i = 0; i < kegiatanUtamaList.size(); i++) {
            namaKegiatan.add(kegiatanUtamaList.get(i).getKegiatan_utama());
        }
        ArrayAdapter<String> spinnerKegiatanAdapter = new ArrayAdapter<String>(this, R.layout.spinner_textview, namaKegiatan);
        spinnerKegiatanKrt.setAdapter(spinnerKegiatanAdapter);

        tiRincian16_5_bypml.addTextChangedListener(new TextWatcher() {
            private String setEditRupiah = tiRincian16_5_bypml.getText().toString().trim();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(setEditRupiah)) {
                    tiRincian16_5_bypml.removeTextChangedListener(this);
                    String replace = s.toString().replaceAll("[Rp. ]", "");
                    if (!replace.isEmpty()){
                        setEditRupiah = formatrupiah(Double.parseDouble(replace));
                    }else{
                        setEditRupiah = "";
                    }
                    tiRincian16_5_bypml.setText(setEditRupiah);
                    tiRincian16_5_bypml.setSelection(setEditRupiah.length());
                    tiRincian16_5_bypml.addTextChangedListener( this);
                }
            }
        });
        tiRincian8_3_bypml.addTextChangedListener(new TextWatcher() {
            private String setEditRupiah = tiRincian8_3_bypml.getText().toString().trim();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(setEditRupiah)) {
                    tiRincian8_3_bypml.removeTextChangedListener(this);
                    String replace = s.toString().replaceAll("[Rp. ]", "");
                    if (!replace.isEmpty()){
                        setEditRupiah = formatrupiah(Double.parseDouble(replace));
                    }else{
                        setEditRupiah = "";
                    }
                    tiRincian8_3_bypml.setText(setEditRupiah);
                    tiRincian8_3_bypml.setSelection(setEditRupiah.length());
                    tiRincian8_3_bypml.addTextChangedListener( this);
                }
            }
        });

        tiRincian304.addTextChangedListener(new TextWatcher() {
            private String setEditRupiah = tiRincian304.getText().toString().trim();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(setEditRupiah)) {
                    tiRincian304.removeTextChangedListener(this);
                    String replace = s.toString().replaceAll("[Rp. ]", "");
                    if (!replace.isEmpty()){
                        setEditRupiah = formatrupiah(Double.parseDouble(replace));
                    }else{
                        setEditRupiah = "";
                    }
                    tiRincian304.setText(setEditRupiah);
                    tiRincian304.setSelection(setEditRupiah.length());
                    tiRincian304.addTextChangedListener( this);
                }
            }
        });
        tiRincian305.addTextChangedListener(new TextWatcher() {
            private String setEditRupiah = tiRincian305.getText().toString().trim();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(setEditRupiah)) {
                    tiRincian305.removeTextChangedListener(this);
                    String replace = s.toString().replaceAll("[Rp. ]", "");
                    if (!replace.isEmpty()){
                        setEditRupiah = formatrupiah(Double.parseDouble(replace));
                    }else{
                        setEditRupiah = "";
                    }
                    tiRincian305.setText(setEditRupiah);
                    tiRincian305.setSelection(setEditRupiah.length());
                    tiRincian305.addTextChangedListener( this);
                }
            }
        });
        // set input value
        tiKdKab.setText(dsrt.getKd_kab());
        tinamaKab.setText(dsrt.getNama_kab());
        tiNks.setText(dsrt.getNks());
        tiNuRt.setText(String.valueOf(dsrt.getNu_rt()));

        if (!dsrt.getNama_krt_cacah().isEmpty() && !dsrt.getNama_krt_cacah().equals("null")) {
            tiNamaKrt.setText(dsrt.getNama_krt_cacah());
        } else {
            tiNamaKrt.setText(dsrt.getNama_krt_prelist());
        }

        // set jumlah art
        tiJmlArt.setText(String.valueOf(dsrt.getJml_art_cacah()));

        String jml_komMakananval = String.valueOf(dsrt.getJml_komoditas_makanan());
        if (!jml_komMakananval.isEmpty() && !jml_komMakananval.equals("null")){
            tijml_kom_makanan.setText(jml_komMakananval);
        }

        String jml_komNonMakananval = String.valueOf(dsrt.getJml_komoditas_nonmakanan());
        if (!jml_komNonMakananval.isEmpty() && !jml_komNonMakananval.equals("null") ){
            tijml_kom_nonmakanan.setText(jml_komNonMakananval);
        }
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
        String makanan_bypmlVal = dsrt.getMakanan_sebulan_bypml();
        if (!makanan_bypmlVal.isEmpty() && !makanan_bypmlVal.equals("null")){
            tiRincian16_5_bypml.setText(makanan_bypmlVal);
        }

        // set blok 8.3
        String nonMakanan_bypmlVal = dsrt.getNonmakanan_sebulan_bypml();
        if(!nonMakanan_bypmlVal.isEmpty() && !nonMakanan_bypmlVal.equals("null")){
            tiRincian8_3_bypml.setText(nonMakanan_bypmlVal);
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
        if (!artSekolahVal.isEmpty() && !artSekolahVal.equals("null") ){
            tiJmlArtSekolah.setText(artSekolahVal);
        }

        // set art bpjs
        String artBpjsVal = String.valueOf(dsrt.getArt_bpjs());
        if (!artBpjsVal.isEmpty() && !artBpjsVal.equals("null") ){
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

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        inputPemeriksaanPMLAdapter = new InputPemeriksaanPMLAdapter(viewModel, dsrt.getTahun(), dsrt.getSemester(), dsrt.getKd_kab(), dsrt.getKd_kec(), dsrt.getKd_desa(), dsrt.getKd_bs(), dsrt.getNu_rt());
        recyclerView.setAdapter(inputPemeriksaanPMLAdapter);

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
                if (TextUtils.isEmpty(tijml_kom_makanan.getText())) {
                    tijml_kom_makanan.setError("Tidak boleh kosong");
                }
                if (TextUtils.isEmpty(tijml_kom_nonmakanan.getText())) {
                    tijml_kom_nonmakanan.setError("Tidak boleh kosong");
                }
                if (TextUtils.isEmpty(tiRincian16_5.getText())) {
                    tiRincian16_5.setError("Tidak boleh kosong");
                }
                if (TextUtils.isEmpty(tiRincian8_3.getText())) {
                    tiRincian8_3.setError("Tidak boleh kosong");
                }
                if (TextUtils.isEmpty(tiRincian16_5_bypml.getText())) {
                    tiRincian16_5_bypml.setError("Tidak boleh kosong");
                }
                if (TextUtils.isEmpty(tiRincian8_3_bypml.getText())) {
                    tiRincian8_3_bypml.setError("Tidak boleh kosong");
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
//                                (!TextUtils.isEmpty(tijml_kom_makanan.getText())) &&
//                                (!TextUtils.isEmpty(tijml_kom_nonmakanan.getText())) &&
                                (!TextUtils.isEmpty(tiRincian16_5.getText())) &&
//                                (!TextUtils.isEmpty(tiRincian8_3.getText())) &&
                                (!TextUtils.isEmpty(tiRincian16_5_bypml.getText())) &&
                                (!TextUtils.isEmpty(tiRincian8_3_bypml.getText()))
//                                (!TextUtils.isEmpty(tiRincian304.getText())) &&
//                                (!TextUtils.isEmpty(tiRincian305.getText())) &&
//                                (!TextUtils.isEmpty(tiJmlArtSekolah.getText())) &&
//                                (!TextUtils.isEmpty(tiJmlArtBpjs.getText())) &&
//                                (!TextUtils.isEmpty(tiDeskripsiUsaha.getText())) &&
//                                (!TextUtils.isEmpty(tiLuasLantai.getText()))
                ) {

                    String ijazah = spinnerIjazahKrt.getSelectedItem().toString();
                    String kegiatan = spinnerKegiatanKrt.getSelectedItem().toString();
                    String trasnportasi = tiRincian304.getText().toString();
                    String peliharaan = tiRincian305.getText().toString();

                    try {

//                        viewModel.updatePemeriksaan(
//                                dsrt.getId(),
//                                tiNamaKrt.getText().toString(),
//                                Integer.parseInt(tiJmlArt.getText().toString()),
//                                Integer.parseInt(tijml_kom_makanan.getText().toString()),
//                                Integer.parseInt(tijml_kom_nonmakanan.getText().toString()),
//                                tiRincian16_5.getText().toString(),
//                                tiRincian8_3.getText().toString(),
//                                tiRincian16_5_bypml.getText().toString(),
//                                tiRincian8_3_bypml.getText().toString(),
//                                trasnportasi,
//                                peliharaan,
//                                Integer.parseInt(tiJmlArtSekolah.getText().toString()),
//                                Integer.parseInt(tiJmlArtBpjs.getText().toString()),
//                                ijazah,
//                                kegiatan,
//                                tiDeskripsiUsaha.getText().toString(),
//                                Integer.parseInt(tiLuasLantai.getText().toString()),
//                                5
//                        );
                        viewModel.updatePemeriksaan(
                                dsrt.getId(),
                                tiNamaKrt.getText().toString(),
                                Integer.parseInt(tiJmlArt.getText().toString()),
                                0,
                                0,
                                tiRincian16_5.getText().toString(),
                                tiRincian8_3.getText().toString(),
                                tiRincian16_5_bypml.getText().toString(),
                                tiRincian8_3_bypml.getText().toString(),
                                "",
                                "",
                                0,
                                0,
                                "",
                                "",
                                "",
                                0,
                                5
                        );

                        inputPemeriksaanPMLAdapter.saveadapter(viewModel);

                        finish();

                    } catch (Error e){
                        Toast toast = Toast.makeText(getApplicationContext(),
                                e.toString(),
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }


                }
            }
        });
    }

    private String formatrupiah(Double number){
        Locale locale = new Locale("IND", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        String formatrupiah = numberFormat.format(number);
        String[] split = formatrupiah.split(",");
        int length = split[0].length();
        return split[0].substring(0,2)+". "+split[0]. substring(2,length);
    }
}