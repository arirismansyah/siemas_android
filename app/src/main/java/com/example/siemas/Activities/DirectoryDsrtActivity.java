package com.example.siemas.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.siemas.Adapter.DsrtDirektoriPclAdapter;
import com.example.siemas.Adapter.DsrtPemeriksaanPclAdapter;
import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.Entities.Periode;
import com.example.siemas.RoomDatabase.ViewModel;

import java.util.List;

public class DirectoryDsrtActivity extends AppCompatActivity {
    public static final String EXTRA_ID_BS = "com.example.siemas.Activities.EXTRA_ID_BS";
    private RecyclerView recyclerView;
    private ViewModel viewModel;
    private DsrtDirektoriPclAdapter dsrtDirektoriPclAdapter;
    private LinearLayoutCompat containerEmpty;
    private List<Periode> periodeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory_dsrt);

        // disallowed dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("MENU DIREKTORI - DSRT");
        setSupportActionBar(myToolbar);

        containerEmpty = findViewById(R.id.emptyPictContainer);
        recyclerView = findViewById(R.id.recyclerDsrtDirektoriPcl);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModel.class);

        String idBs = this.getIntent().getStringExtra(EXTRA_ID_BS);
        dsrtDirektoriPclAdapter = new DsrtDirektoriPclAdapter(viewModel);
        recyclerView.setAdapter(dsrtDirektoriPclAdapter);
        periodeList = viewModel.getPeriode();
        viewModel.getLiveDataDsrtByIdBs(idBs, periodeList.get(0).getTahun(), periodeList.get(0).getSemester()).observe(this, new Observer<List<Dsrt>>() {
            @Override
            public void onChanged(List<Dsrt> dsrts) {
                if (dsrts.size()>0){
                    containerEmpty.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    containerEmpty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                dsrtDirektoriPclAdapter.setListDsrt(dsrts);
            }
        });
    }
}