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

import com.example.siemas.Adapter.DsbsPemeriksaanPclAdapter;
import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsbs;
import com.example.siemas.RoomDatabase.Entities.Periode;
import com.example.siemas.RoomDatabase.ViewModel;

import java.util.List;

public class PemeriksaanPclActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ViewModel viewModel;
    private DsbsPemeriksaanPclAdapter adapter;
    private LinearLayoutCompat containerEmpty;
    private List<Periode> periodeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemeriksaan_pcl);

        // disallowed dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("MENU UPLOAD");
        setSupportActionBar(myToolbar);

        containerEmpty = findViewById(R.id.emptyPictContainer);
        recyclerView = findViewById(R.id.recyclerFotoDsbs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModel.class);
        periodeList = viewModel.getPeriode();
        adapter = new DsbsPemeriksaanPclAdapter(viewModel);
        recyclerView.setAdapter(adapter);
        viewModel.getLiveDataDsbs(periodeList.get(0).getTahun(), periodeList.get(0).getSemester()).observe(this, new Observer<List<Dsbs>>() {
            @Override
            public void onChanged(List<Dsbs> dsbs) {
                if (dsbs.size()>0){
                    recyclerView.setVisibility(View.VISIBLE);
                    containerEmpty.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    containerEmpty.setVisibility(View.VISIBLE);
                }
                adapter.setListDsbs(dsbs);
            }
        });

        adapter.setOnItemClickListener(new DsbsPemeriksaanPclAdapter.onItemCLickListener() {
            @Override
            public void onItemClick(Dsbs dsbs) {
                Intent intent = new Intent(PemeriksaanPclActivity.this, PemeriksaanPclDsrtActivity.class);
                intent.putExtra(PemeriksaanPclDsrtActivity.EXTRA_KD_KAB,  dsbs.getKd_kab());
                intent.putExtra(PemeriksaanPclDsrtActivity.EXTRA_KD_KEC,  dsbs.getKd_kec() );
                intent.putExtra(PemeriksaanPclDsrtActivity.EXTRA_KD_DESA,  dsbs.getKd_desa() );
                intent.putExtra(PemeriksaanPclDsrtActivity.EXTRA_KD_BS,  dsbs.getKd_bs() );
                intent.putExtra(PemeriksaanPclDsrtActivity.EXTRA_ID_BS, "16"+ dsbs.getKd_kab() + dsbs.getKd_kec() + dsbs.getKd_desa() + dsbs.getKd_bs() );
                startActivityForResult(intent,1);
            }
        });
    }
}