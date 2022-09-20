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
import android.view.View;

import com.example.siemas.Adapter.DsrtPencacahanAdapter;
import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.ViewModel;

import java.util.List;

public class PencacahanDsrtActivity extends AppCompatActivity {
    public static final String EXTRA_ID_BS = "com.example.siemas.Activities.EXTRA_ID_BS";
    private RecyclerView recyclerView;
    private ViewModel viewModel;
    private DsrtPencacahanAdapter dsrtPencacahanAdapter;
    private LinearLayoutCompat containerEmpty;

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

        String idBs = this.getIntent().getStringExtra(EXTRA_ID_BS);
        dsrtPencacahanAdapter = new DsrtPencacahanAdapter();
        recyclerView.setAdapter(dsrtPencacahanAdapter);

        viewModel.getLiveDataDsrtByIdBs(idBs).observe(this, new Observer<List<Dsrt>>() {
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
                    intent.putExtra(InputPencacahanActivity.EXTRA_NAMA_KRT, dsrt.getNama_krt());
                    intent.putExtra(InputPencacahanActivity.EXTRA_ID_BS, dsrt.getId_bs());
                    intent.putExtra(InputPencacahanActivity.EXTRA_NU_RT, String.valueOf(dsrt.getNu_rt()));
                    intent.putExtra(InputPencacahanActivity.EXTRA_ID_DSRT, String.valueOf(dsrt.getId()));
                    startActivityForResult(intent,1);
                } else {
                    Intent intent = new Intent(PencacahanDsrtActivity.this, EditPencacahanActivity.class);
                    intent.putExtra(InputPencacahanActivity.EXTRA_ID_KAB, dsrt.getKd_kab());
                    intent.putExtra(InputPencacahanActivity.EXTRA_NAMA_KAB, dsrt.getNama_kab());
                    intent.putExtra(InputPencacahanActivity.EXTRA_NKS, dsrt.getNks());
                    intent.putExtra(InputPencacahanActivity.EXTRA_NAMA_KRT, dsrt.getNama_krt());
                    intent.putExtra(InputPencacahanActivity.EXTRA_ID_BS, dsrt.getId_bs());
                    intent.putExtra(InputPencacahanActivity.EXTRA_NU_RT, String.valueOf(dsrt.getNu_rt()));
                    intent.putExtra(InputPencacahanActivity.EXTRA_ID_DSRT, String.valueOf(dsrt.getId()));
                    startActivityForResult(intent,1);
                }
            }
        });

    }
}