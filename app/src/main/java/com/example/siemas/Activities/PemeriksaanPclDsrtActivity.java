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
import com.example.siemas.Adapter.DsrtPencacahanAdapter;
import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.Entities.Periode;
import com.example.siemas.RoomDatabase.ViewModel;

import java.util.List;

public class PemeriksaanPclDsrtActivity extends AppCompatActivity {
    public static final String EXTRA_ID_BS = "com.example.siemas.Activities.EXTRA_ID_BS";
    public static final String EXTRA_ID_DSRT = "com.example.siemas.Activities.EXTRA_ID_DSRT";
    public static final String EXTRA_KD_KAB = "com.example.siemas.Activities.EXTRA_KD_KAB";
    public static final String EXTRA_KD_KEC = "com.example.siemas.Activities.EXTRA_KD_KEC";
    public static final String EXTRA_KD_DESA = "com.example.siemas.Activities.EXTRA_KD_DESA";
    public static final String EXTRA_KD_BS = "com.example.siemas.Activities.EXTRA_KD_BS";
    private RecyclerView recyclerView;
    private ViewModel viewModel;
    private DsrtPemeriksaanPclAdapter dsrtPemeriksaanPclAdapter;
    private LinearLayoutCompat containerEmpty;
    private List<Periode> periodeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemeriksaan_pcl_dsrt);
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
        dsrtPemeriksaanPclAdapter = new DsrtPemeriksaanPclAdapter(viewModel);
        recyclerView.setAdapter(dsrtPemeriksaanPclAdapter);
//        List<Dsrt> dsrts = viewModel.getLiveDataDsrtByIdBs(idBs,periodeList.get(0).getTahun(), periodeList.get(0).getSemester());
//        if (dsrts.size() > 0) {
//            containerEmpty.setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);
//        } else {
//            containerEmpty.setVisibility(View.VISIBLE);
//            recyclerView.setVisibility(View.GONE);
//        }
//        dsrtPemeriksaanPclAdapter.setListDsrt(dsrts);
        viewModel.getLiveDataDsrtByIdBs(periodeList.get(0).getTahun(), periodeList.get(0).getSemester(), kd_kab, kd_kec, kd_desa, kd_bs).observe(this, new Observer<List<Dsrt>>() {
            @Override
            public void onChanged(List<Dsrt> dsrts) {
                if (dsrts.size()>0){
                    recyclerView.setVisibility(View.VISIBLE);
                    containerEmpty.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    containerEmpty.setVisibility(View.VISIBLE);
                }
                dsrtPemeriksaanPclAdapter.setListDsrt(dsrts);
            }
        });

        dsrtPemeriksaanPclAdapter.setOnItemClickListener(new DsrtPemeriksaanPclAdapter.onItemCLickListener() {
            @Override
            public void onItemClick(Dsrt dsrt) {
                if (dsrt.getStatus_pencacahan() < 1) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PemeriksaanPclDsrtActivity.this);
                    alertDialogBuilder.setTitle("SIEMAS 2022");
                    alertDialogBuilder.setMessage("Anda harus melakukan input pada menu pencacahan terlebih dahulu (menyelesaikan pencacahan) untuk Rumah Tangga ini sebelum melakukan input pemeriksaan.");
                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {

                    Intent intent = new Intent(PemeriksaanPclDsrtActivity.this, InputPemeriksaanPclActivity.class);
                    intent.putExtra(InputPemeriksaanPclActivity.EXTRA_KD_KAB, dsrt.getKd_kab());
                    intent.putExtra(InputPemeriksaanPclActivity.EXTRA_KD_KEC, dsrt.getKd_kec());
                    intent.putExtra(InputPemeriksaanPclActivity.EXTRA_KD_DESA, dsrt.getKd_desa());
                    intent.putExtra(InputPemeriksaanPclActivity.EXTRA_KD_BS, dsrt.getKd_bs());
                    intent.putExtra(InputPemeriksaanPclActivity.EXTRA_NAMA_KAB, dsrt.getNama_kab());
                    intent.putExtra(InputPemeriksaanPclActivity.EXTRA_NKS, dsrt.getNks());
                    intent.putExtra(InputPemeriksaanPclActivity.EXTRA_NAMA_KRT, dsrt.getNama_krt_prelist());
                    intent.putExtra(InputPemeriksaanPclActivity.EXTRA_ID_BS, "16"+ dsrt.getKd_kab()+dsrt.getKd_kec()+dsrt.getKd_desa()+dsrt.getKd_bs());
                    intent.putExtra(InputPemeriksaanPclActivity.EXTRA_NU_RT, String.valueOf(dsrt.getNu_rt()));
                    intent.putExtra(InputPemeriksaanPclActivity.EXTRA_ID_DSRT, String.valueOf(dsrt.getId()));
                    startActivityForResult(intent, 1);


                }
            }
        });

    }
}