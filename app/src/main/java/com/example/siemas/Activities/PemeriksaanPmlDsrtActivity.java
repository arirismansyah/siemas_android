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
import com.example.siemas.RoomDatabase.ViewModel;

import java.util.List;

public class PemeriksaanPmlDsrtActivity extends AppCompatActivity {
    public static final String EXTRA_ID_BS = "com.example.siemas.Activities.EXTRA_ID_BS";
    private RecyclerView recyclerView;
    private ViewModel viewModel;
    private DsrtPemeriksaanPmlAdapter dsrtPemeriksaanPmlAdapter;
    private LinearLayoutCompat containerEmpty;

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

        String idBs = this.getIntent().getStringExtra(EXTRA_ID_BS);
        dsrtPemeriksaanPmlAdapter = new DsrtPemeriksaanPmlAdapter(viewModel);
        recyclerView.setAdapter(dsrtPemeriksaanPmlAdapter);

        viewModel.getLiveDataDsrtByIdBs(idBs).observe(this, new Observer<List<Dsrt>>() {
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
                    intent.putExtra(InputPencacahanActivity.EXTRA_ID_KAB, dsrt.getKd_kab());
                    intent.putExtra(InputPencacahanActivity.EXTRA_NAMA_KAB, dsrt.getNama_kab());
                    intent.putExtra(InputPencacahanActivity.EXTRA_NKS, dsrt.getNks());
                    intent.putExtra(InputPencacahanActivity.EXTRA_NAMA_KRT, dsrt.getNama_krt());
                    intent.putExtra(InputPencacahanActivity.EXTRA_ID_BS, dsrt.getId_bs());
                    intent.putExtra(InputPencacahanActivity.EXTRA_NU_RT, String.valueOf(dsrt.getNu_rt()));
                    intent.putExtra(InputPencacahanActivity.EXTRA_ID_DSRT, String.valueOf(dsrt.getId()));
                    startActivityForResult(intent, 1);
                }
            }
        });
    }
}