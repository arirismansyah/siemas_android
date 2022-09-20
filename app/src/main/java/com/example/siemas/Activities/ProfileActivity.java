package com.example.siemas.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.User;
import com.example.siemas.RoomDatabase.ViewModel;

public class ProfileActivity extends AppCompatActivity {
    private ViewModel viewModel;
    private User user;
    TextView tvNama, tvRole, tvKdProv, tvKdKab, tvNamaProv, tvNamaKab, tvJmlDsbs, tvJmlDsrt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // disallowed dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModel.class);
        user = viewModel.getUser().get(0);
        tvNama = findViewById(R.id.textNamaPetugas);
        tvRole = findViewById(R.id.textRole);
        tvKdProv = findViewById(R.id.tvKdProv);
        tvNamaProv = findViewById(R.id.tvNamaProv);
        tvKdKab = findViewById(R.id.tvKdKab);
        tvNamaKab = findViewById(R.id.tvNamakab);
        tvJmlDsbs = findViewById(R.id.tvJmlDsbs);
        tvJmlDsrt = findViewById(R.id.tvJmlDsrt);

        tvNama.setText(user.getName());
        tvRole.setText(user.getRole());
        tvKdProv.setText("[16]");
        tvNamaProv.setText("Sumatera Selatan");
        tvKdKab.setText("["+user.getKd_wilayah()+"]");
        tvNamaKab.setText(user.getNama_kab());
        tvJmlDsbs.setText(String.valueOf(viewModel.getDsbs().size()));
        tvJmlDsrt.setText(String.valueOf(viewModel.getDsrt().size()));


    }
}