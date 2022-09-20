package com.example.siemas.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.siemas.Adapter.TableDsbsAdapter;
import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsbs;
import com.example.siemas.RoomDatabase.Entities.User;
import com.example.siemas.RoomDatabase.ViewModel;

import java.util.List;

public class DsbsActivity extends AppCompatActivity {
    TextView tvKdProv, tvKdKab, tvNamaProv, tvNamaKab;
    ViewModel viewModel;
    Button syncDsbs, syncDsrt;
    User user;
    RecyclerView recyclerView;
    TableDsbsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // disallowed dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_dsbs);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("MENU DSBS");
        setSupportActionBar(myToolbar);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModel.class);
        user = viewModel.getUser().get(0);
        tvKdProv = findViewById(R.id.tvKdProv);
        tvNamaProv = findViewById(R.id.tvNamaProv);
        tvKdKab = findViewById(R.id.tvKdKab);
        tvNamaKab = findViewById(R.id.tvNamakab);
        syncDsbs = findViewById(R.id.syncDsbs);
        syncDsrt = findViewById(R.id.syncDsrt);
        recyclerView = findViewById(R.id.recyclerTable);


        tvKdProv.setText("[16]");
        tvNamaProv.setText("Sumatera Selatan");
        tvKdKab.setText("["+user.getKd_wilayah()+"]");
        tvNamaKab.setText(user.getNama_kab());

        recyclerView = findViewById(R.id.recyclerTable);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new TableDsbsAdapter();
        recyclerView.setAdapter(adapter);
        viewModel.getLiveDataDsbs().observe(this, new Observer<List<Dsbs>>() {
            @Override
            public void onChanged(List<Dsbs> dsbs) {
                adapter.setListDsbs(dsbs);
            }
        });

        syncDsbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getRole().equals("PENCACAH")){
                    viewModel.getDsbsPclFromApi(DsbsActivity.this, user.getEmail(), user.getToken());
                }

                if (user.getRole().equals("PENGAWAS")){

                }
            }
        });

        syncDsrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getRole().equals("PENCACAH")){
                    viewModel.getDsrtPclFromApi(DsbsActivity.this, user.getEmail(), user.getToken());
                }

                if (user.getRole().equals("PENGAWAS")){

                }
            }
        });
    }


}