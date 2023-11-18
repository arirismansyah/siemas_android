package com.example.siemas.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.siemas.Adapter.DsrtPencacahanAdapter;
import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.Entities.Periode;
import com.example.siemas.RoomDatabase.ViewModel;

import java.util.List;

public class PencacahanDsrtActivity extends AppCompatActivity {
    public static final String EXTRA_ID_BS = "com.example.siemas.Activities.EXTRA_ID_BS";
    public static final String EXTRA_KD_KAB = "com.example.siemas.Activities.EXTRA_KD_KAB";
    public static final String EXTRA_KD_KEC = "com.example.siemas.Activities.EXTRA_KD_KEC";
    public static final String EXTRA_KD_DESA = "com.example.siemas.Activities.EXTRA_KD_DESA";
    public static final String EXTRA_KD_BS = "com.example.siemas.Activities.EXTRA_KD_BS";
    private RecyclerView recyclerView;
    private ViewModel viewModel;
    private DsrtPencacahanAdapter dsrtPencacahanAdapter;
    private LinearLayoutCompat containerEmpty;
    private List<Periode> periodeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // disallowed dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_pencacahan_dsrt);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("MENU PENCACAHAN - DSRT");
        setSupportActionBar(myToolbar);

        containerEmpty = findViewById(R.id.emptyPictContainer);
        recyclerView = findViewById(R.id.recyclerDsrtPencacahan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModel.class);
        periodeList = viewModel.getPeriode();
        String idBs = this.getIntent().getStringExtra(EXTRA_ID_BS);
        dsrtPencacahanAdapter = new DsrtPencacahanAdapter(viewModel);
        recyclerView.setAdapter(dsrtPencacahanAdapter);
//        List<Dsrt> dsrts = viewModel.getLiveDataDsrtByIdBs(idBs,periodeList.get(0).getTahun(), periodeList.get(0).getSemester());
//        if (dsrts.size() > 0) {
//            containerEmpty.setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);
//        } else {
//            containerEmpty.setVisibility(View.VISIBLE);
//            recyclerView.setVisibility(View.GONE);
//        }
//        dsrtPencacahanAdapter.setListDsrt(dsrts);
        String kd_kab = this.getIntent().getStringExtra(EXTRA_KD_KAB);
        String kd_kec = this.getIntent().getStringExtra(EXTRA_KD_KEC);
        String kd_desa = this.getIntent().getStringExtra(EXTRA_KD_DESA);
        String kd_bs = this.getIntent().getStringExtra(EXTRA_KD_BS);

        viewModel.getLiveDataDsrtByIdBs(periodeList.get(0).getTahun(), periodeList.get(0).getSemester(), kd_kab, kd_kec, kd_desa, kd_bs).observe(this, new Observer<List<Dsrt>>() {
            @Override
            public void onChanged(List<Dsrt> dsrts) {
                if (dsrts.size()>0){
                    containerEmpty.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    containerEmpty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                dsrtPencacahanAdapter.setListDsrt(dsrts);
            }
        });

        dsrtPencacahanAdapter.setOnItemClickListener(new DsrtPencacahanAdapter.onItemCLickListener() {
            @Override
            public void onItemClick(Dsrt dsrt) {
                if (dsrt.getStatus_pencacahan()==0){
                    Intent intent = new Intent(PencacahanDsrtActivity.this, InputPencacahanActivity.class);
                    intent.putExtra(InputPencacahanActivity.EXTRA_ID_KAB, dsrt.getKd_kab());
                    intent.putExtra(InputPencacahanActivity.EXTRA_NAMA_KAB, dsrt.getNama_kab());
                    intent.putExtra(InputPencacahanActivity.EXTRA_NKS, dsrt.getNks());
                    intent.putExtra(InputPencacahanActivity.EXTRA_NAMA_KRT, dsrt.getNama_krt_cacah());
                    intent.putExtra(InputPencacahanActivity.EXTRA_ID_BS, dsrt.getId());
                    intent.putExtra(InputPencacahanActivity.EXTRA_NU_RT, String.valueOf(dsrt.getNu_rt()));
                    intent.putExtra(InputPencacahanActivity.EXTRA_ID_DSRT, String.valueOf(dsrt.getId()));
                    startActivityForResult(intent,1);
                } else {
                    Intent intent = new Intent(PencacahanDsrtActivity.this, EditPencacahanActivity.class);
                    intent.putExtra(InputPencacahanActivity.EXTRA_ID_KAB, dsrt.getKd_kab());
                    intent.putExtra(InputPencacahanActivity.EXTRA_NAMA_KAB, dsrt.getNama_kab());
                    intent.putExtra(InputPencacahanActivity.EXTRA_NKS, dsrt.getNks());
                    intent.putExtra(InputPencacahanActivity.EXTRA_NAMA_KRT, dsrt.getNama_krt_cacah());
                    intent.putExtra(InputPencacahanActivity.EXTRA_ID_BS, dsrt.getId());
                    intent.putExtra(InputPencacahanActivity.EXTRA_NU_RT, String.valueOf(dsrt.getNu_rt()));
                    intent.putExtra(InputPencacahanActivity.EXTRA_ID_DSRT, String.valueOf(dsrt.getId()));
                    startActivityForResult(intent,1);
                }
            }
        });

    }
}