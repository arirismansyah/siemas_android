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

import com.example.siemas.Adapter.DsbsPencacahanAdapter;
import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsbs;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.Entities.Periode;
import com.example.siemas.RoomDatabase.ViewModel;

import java.util.List;

public class PencacahanActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ViewModel viewModel;
    private DsbsPencacahanAdapter dsbsPencacahanAdapter;
    private List<Dsrt> dsrtListBelum, dsrtListSudah;
    private LinearLayoutCompat containerEmpty;

    private List<Periode> periode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // disallowed dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(com.example.siemas.R.layout.activity_pencacahan);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("MENU PENCACAHAN");
        setSupportActionBar(myToolbar);

        containerEmpty = findViewById(R.id.emptyPictContainer);
        recyclerView = findViewById(R.id.recyclerDsbsPencacahan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModel.class);
        periode = viewModel.getPeriode();

        dsbsPencacahanAdapter = new DsbsPencacahanAdapter(viewModel);
        recyclerView.setAdapter(dsbsPencacahanAdapter);
        viewModel.getLiveDataDsbs(periode.get(0).getTahun(), periode.get(0).getSemester()).observe(this, new Observer<List<Dsbs>>() {
            @Override
            public void onChanged(List<Dsbs> dsbs) {
                if (dsbs.size()>0){
                    containerEmpty.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }else {
                    containerEmpty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                dsbsPencacahanAdapter.setListDsbs(dsbs);
            }
        });

        dsbsPencacahanAdapter.setOnItemClickListener(new DsbsPencacahanAdapter.onItemCLickListener() {
            @Override
            public void onItemClick(Dsbs dsbs) {
                Intent intent = new Intent(PencacahanActivity.this, PencacahanDsrtActivity.class);
                intent.putExtra(PencacahanDsrtActivity.EXTRA_ID_BS, "16" + dsbs.getKd_kab() + dsbs.getKd_kec() + dsbs.getKd_desa() + dsbs.getKd_bs());
                intent.putExtra(PencacahanDsrtActivity.EXTRA_KD_KAB, dsbs.getKd_kab());
                intent.putExtra(PencacahanDsrtActivity.EXTRA_KD_KEC, dsbs.getKd_kec());
                intent.putExtra(PencacahanDsrtActivity.EXTRA_KD_DESA, dsbs.getKd_desa());
                intent.putExtra(PencacahanDsrtActivity.EXTRA_KD_BS, dsbs.getKd_bs());
                startActivityForResult(intent,1);
            }
        });

    }
}