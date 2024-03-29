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

import com.example.siemas.Adapter.DsbsDirektoriPclAdapter;
import com.example.siemas.Adapter.DsbsPemeriksaanPclAdapter;
import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsbs;
import com.example.siemas.RoomDatabase.Entities.Periode;
import com.example.siemas.RoomDatabase.ViewModel;

import java.util.List;

public class DirectoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ViewModel viewModel;
    private DsbsDirektoriPclAdapter adapter;
    private LinearLayoutCompat containerEmpty;

    private List<Periode> periodeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);
        // disallowed dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // set toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("MENU DIREKTORI");
        setSupportActionBar(myToolbar);

        containerEmpty = findViewById(R.id.emptyPictContainer);
        recyclerView = findViewById(R.id.recyclerDsbsDirektoriPcl);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModel.class);
        periodeList = viewModel.getPeriode();
        adapter = new DsbsDirektoriPclAdapter(viewModel);
        recyclerView.setAdapter(adapter);
        viewModel.getLiveDataDsbs(periodeList.get(0).getTahun(), periodeList.get(0).getSemester()).observe(this, new Observer<List<Dsbs>>() {
            @Override
            public void onChanged(List<Dsbs> dsbs) {
                if (dsbs.size()>0){
                    containerEmpty.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    containerEmpty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                adapter.setListDsbs(dsbs);
            }
        });

        adapter.setOnItemClickListener(new DsbsDirektoriPclAdapter.onItemCLickListener() {
            @Override
            public void onItemClick(Dsbs dsbs) {
                Intent intent = new Intent(DirectoryActivity.this, DirectoryDsrtActivity.class);
                intent.putExtra(DirectoryDsrtActivity.EXTRA_ID_BS, "16" + dsbs.getKd_kab() + dsbs.getKd_kec() + dsbs.getKd_desa() + dsbs.getKd_bs());
                intent.putExtra(DirectoryDsrtActivity.EXTRA_KD_KAB, dsbs.getKd_kab());
                intent.putExtra(DirectoryDsrtActivity.EXTRA_KD_KEC, dsbs.getKd_kec());
                intent.putExtra(DirectoryDsrtActivity.EXTRA_KD_DESA, dsbs.getKd_desa());
                intent.putExtra(DirectoryDsrtActivity.EXTRA_KD_BS, dsbs.getKd_bs());
                startActivityForResult(intent,1);
            }
        });
    }
}