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
import com.example.siemas.Adapter.DsbsPemeriksaanPmlAdapter;
import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsbs;
import com.example.siemas.RoomDatabase.ViewModel;

import java.util.List;

public class PemeriksaanPmlActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ViewModel viewModel;
    private DsbsPemeriksaanPmlAdapter adapter;
    private LinearLayoutCompat containerEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemeriksaan_pml);

        // disallowed dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("MENU PEMERIKSAAN");
        setSupportActionBar(myToolbar);

        containerEmpty = findViewById(R.id.emptyPictContainer);
        recyclerView = findViewById(R.id.recyclerDsbsPemeriksaanPml);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModel.class);

        adapter = new DsbsPemeriksaanPmlAdapter(viewModel);
        recyclerView.setAdapter(adapter);

        viewModel.getLiveDataDsbs().observe(this, new Observer<List<Dsbs>>() {
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

        adapter.setOnItemClickListener(new DsbsPemeriksaanPmlAdapter.onItemCLickListener() {
            @Override
            public void onItemClick(Dsbs dsbs) {
                Intent intent = new Intent(PemeriksaanPmlActivity.this, PemeriksaanPmlDsrtActivity.class);
                intent.putExtra(PencacahanDsrtActivity.EXTRA_ID_BS, dsbs.getId_bs());
                startActivityForResult(intent,1);
            }
        });
    }
}