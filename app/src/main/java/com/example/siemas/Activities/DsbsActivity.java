package com.example.siemas.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siemas.Adapter.TableDsbsAdapter;
import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsbs;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.Entities.Periode;
import com.example.siemas.RoomDatabase.Entities.User;
import com.example.siemas.RoomDatabase.ViewModel;

import java.util.List;

public class DsbsActivity extends AppCompatActivity {
    TextView tvKdProv, tvKdKab, tvNamaProv, tvNamaKab;
    ViewModel viewModel;
    Button syncAll, syncDsbs, syncDsrt, syncDsart;
    User user;
    RecyclerView recyclerView;
    TableDsbsAdapter adapter;
    private List<Periode> periodeList;
    private List<Dsrt> dsrtListBelumUpload;
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
        periodeList = viewModel.getPeriode();
        tvKdProv = findViewById(R.id.tvKdProv);
        tvNamaProv = findViewById(R.id.tvNamaProv);
        tvKdKab = findViewById(R.id.tvKdKab);
        tvNamaKab = findViewById(R.id.tvNamakab);
        syncAll = findViewById(R.id.syncAll);
        syncDsbs = findViewById(R.id.syncDsbs);
        syncDsrt = findViewById(R.id.syncDsrt);
        syncDsart = findViewById(R.id.syncDsart);
        recyclerView = findViewById(R.id.recyclerTable);


        tvKdProv.setText("[16]");
        tvNamaProv.setText("Sumatera Selatan");
        tvKdKab.setText("["+user.getKd_kab()+"]");
        tvNamaKab.setText(user.getNama_kab());

        recyclerView = findViewById(R.id.recyclerTable);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new TableDsbsAdapter();
        recyclerView.setAdapter(adapter);
        viewModel.getLiveDataDsbs(periodeList.get(0).getTahun(), periodeList.get(0).getSemester()).observe(this, new Observer<List<Dsbs>>() {
            @Override
            public void onChanged(List<Dsbs> dsbs) {
                adapter.setListDsbs(dsbs);
            }
        });

        syncAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dsrtListBelumUpload = viewModel.getListDsrtByStatus(1, 4, periodeList.get(0).getTahun(), periodeList.get(0).getSemester());
                if (dsrtListBelumUpload.size() > 0) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
                    alertDialogBuilder.setTitle("SIEMAS 2024");
                    alertDialogBuilder.setMessage("Mohon upload seluruh data lokal yang sudah diedit sebelum melakukan sinkronisasi!");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    viewModel.getDsbsPclFromApi(DsbsActivity.this, user.getEmail(), user.getToken());
                    viewModel.getDsrtPclFromApi(DsbsActivity.this, user.getEmail(), user.getToken());
                }
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

        syncDsart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getRole().equals("PENCACAH")){
                    viewModel.getDsartPclFromApi(DsbsActivity.this, user.getEmail(), user.getToken());
                }
                if (user.getRole().equals("PENGAWAS")){

                }
            }
        });
    }


}