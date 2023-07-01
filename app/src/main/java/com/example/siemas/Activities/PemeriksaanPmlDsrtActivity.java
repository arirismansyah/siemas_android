package com.example.siemas.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.siemas.Adapter.DsrtPemeriksaanPclAdapter;
import com.example.siemas.Adapter.DsrtPemeriksaanPmlAdapter;
import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.Entities.Periode;
import com.example.siemas.RoomDatabase.ViewModel;

import java.util.List;

public class PemeriksaanPmlDsrtActivity extends AppCompatActivity {
    public static final String EXTRA_ID_BS = "com.example.siemas.Activities.EXTRA_ID_BS";
    public static final String EXTRA_KD_KAB = "com.example.siemas.Activities.EXTRA_KD_KAB";
    public static final String EXTRA_KD_KEC = "com.example.siemas.Activities.EXTRA_KD_KEC";
    public static final String EXTRA_KD_DESA = "com.example.siemas.Activities.EXTRA_KD_DESA";
    public static final String EXTRA_KD_BS = "com.example.siemas.Activities.EXTRA_KD_BS";
    private RecyclerView recyclerView;
    private ViewModel viewModel;
    private DsrtPemeriksaanPmlAdapter dsrtPemeriksaanPmlAdapter;
    private LinearLayoutCompat containerEmpty;
    private List<Periode> periodeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemeriksaan_pml_dsrt);

        // disallowed dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("MENU PEMERIKSAAN - DSRT");
        setSupportActionBar(myToolbar);

        containerEmpty = findViewById(R.id.emptyPictContainer);
        recyclerView = findViewById(R.id.recyclerDsrtPemeriksaan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModel.class);
        periodeList = viewModel.getPeriode();
        String idBs = this.getIntent().getStringExtra(EXTRA_ID_BS);
        String kd_kab = this.getIntent().getStringExtra(EXTRA_KD_KAB);
        String kd_kec = this.getIntent().getStringExtra(EXTRA_KD_KEC);
        String kd_desa = this.getIntent().getStringExtra(EXTRA_KD_DESA);
        String kd_bs = this.getIntent().getStringExtra(EXTRA_KD_BS);
        dsrtPemeriksaanPmlAdapter = new DsrtPemeriksaanPmlAdapter(viewModel);
        recyclerView.setAdapter(dsrtPemeriksaanPmlAdapter);
//        List<Dsrt> dsrts = viewModel.getLiveDataDsrtByIdBs(idBs,periodeList.get(0).getTahun(), periodeList.get(0).getSemester());
//        if (dsrts.size() > 0) {
//            containerEmpty.setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);
//        } else {
//            containerEmpty.setVisibility(View.VISIBLE);
//            recyclerView.setVisibility(View.GONE);
//        }
//        dsrtPemeriksaanPmlAdapter.setListDsrt(dsrts);
        viewModel.getLiveDataDsrtByIdBs( periodeList.get(0).getTahun(), periodeList.get(0).getSemester() , kd_kab, kd_kec, kd_desa, kd_bs).observe(this, new Observer<List<Dsrt>>() {
            @Override
            public void onChanged(List<Dsrt> dsrts) {
                if (dsrts.size()>0){
                    recyclerView.setVisibility(View.VISIBLE);
                    containerEmpty.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    containerEmpty.setVisibility(View.VISIBLE);
                }
                dsrtPemeriksaanPmlAdapter.setListDsrt(dsrts);
            }
        });

        dsrtPemeriksaanPmlAdapter.setOnItemClickListener(new DsrtPemeriksaanPmlAdapter.onItemCLickListener() {
            @Override
            public void onItemClick(Dsrt dsrt) {
                if (dsrt.getStatus_pencacahan()<4){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PemeriksaanPmlDsrtActivity.this);
                    alertDialogBuilder.setTitle("SIEMAS 2022");
                    alertDialogBuilder.setMessage("PCL harus melakukan input pada menu pencacahan & pemeriksaan terlebih dahulu (menyelesaikan pencacahan & pemeriksaan) dan upload data untuk Rumah Tangga ini. Minta PCL untuk upload data kemudian lakukan Sync Data kembali sebelum melakukan input pemeriksaan.");
                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    Intent intent = new Intent(PemeriksaanPmlDsrtActivity.this, InputPemeriksaanPmlActivity.class);
                    intent.putExtra(InputPemeriksaanPmlActivity.EXTRA_KD_KAB,  dsrt.getKd_kab());
                    intent.putExtra(InputPemeriksaanPmlActivity.EXTRA_KD_KEC,  dsrt.getKd_kec() );
                    intent.putExtra(InputPemeriksaanPmlActivity.EXTRA_KD_DESA,  dsrt.getKd_desa() );
                    intent.putExtra(InputPemeriksaanPmlActivity.EXTRA_KD_BS,  dsrt.getKd_bs() );
                    intent.putExtra(InputPemeriksaanPmlActivity.EXTRA_ID_BS, "16"+ dsrt.getKd_kab() + dsrt.getKd_kec() + dsrt.getKd_desa() + dsrt.getKd_bs() );
                    intent.putExtra(InputPemeriksaanPmlActivity.EXTRA_ID_DSRT, String.valueOf(dsrt.getId()));
                    startActivityForResult(intent, 1);
                }
            }
        });
    }
}