package com.example.siemas.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siemas.Adapter.FotoDsbsAdapter;
import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsbs;
import com.example.siemas.RoomDatabase.Entities.Periode;
import com.example.siemas.RoomDatabase.ViewModel;

import java.util.List;

public class FotoDsbsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ViewModel viewModel;
    private FotoDsbsAdapter fotoDsbsAdapter;
    private LinearLayoutCompat containerEmpty;
    private List<Periode> periodeList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.foto_dsbs_activity);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("MENU FOTO - DSBS");
        setSupportActionBar(myToolbar);
        containerEmpty = findViewById(R.id.emptyPictContainer);
        recyclerView = findViewById(R.id.recyclerFotoDsbs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModel.class);
        periodeList = viewModel.getPeriode();
        fotoDsbsAdapter = new FotoDsbsAdapter(viewModel);
        recyclerView.setAdapter(fotoDsbsAdapter);
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
                fotoDsbsAdapter.setListDsbs(dsbs);
            }
        });

        fotoDsbsAdapter.setOnItemClickListener(new FotoDsbsAdapter.onItemCLickListener() {
            @Override
            public void onItemClick(Dsbs dsbs) {
                Intent intent = new Intent(FotoDsbsActivity.this, FotoDsrtActivity.class);
                intent.putExtra(FotoDsrtActivity.EXTRA_ID_BS, "16"+ dsbs.getKd_kab() + dsbs.getKd_kec() + dsbs.getKd_desa() + dsbs.getKd_bs());
                intent.putExtra(FotoDsrtActivity.EXTRA_KD_KAB, dsbs.getKd_kab());
                intent.putExtra(FotoDsrtActivity.EXTRA_KD_KEC, dsbs.getKd_kec());
                intent.putExtra(FotoDsrtActivity.EXTRA_KD_DESA, dsbs.getKd_desa());
                intent.putExtra(FotoDsrtActivity.EXTRA_KD_BS, dsbs.getKd_bs());
                startActivityForResult(intent,1);
            }
        });
    }
}
