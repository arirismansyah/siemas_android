package com.example.siemas.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsbs;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.Entities.Laporan212;
import com.example.siemas.RoomDatabase.Entities.Periode;
import com.example.siemas.RoomDatabase.Entities.User;
import com.example.siemas.RoomDatabase.ViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FormInput212Activity extends AppCompatActivity {
    private TextInputEditText tiKdKab, tiNamaKab;
    private TextInputEditText tiNamaKrt;
    private Spinner spinnerBs, spinnerNuRt;
    private ViewModel viewModel;
    private AppCompatButton batalBtn, simpanBtn;
    private List<Dsbs> dsbsList;
    private List<Dsrt> dsrtList;
    private Dsrt dsrtSelected;
    private User user;
    private SimpleDateFormat dateFormatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private List<Periode> periodeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_input212);

        // disallowed dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        // setting viewmodel
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(com.example.siemas.RoomDatabase.ViewModel.class);
        user = viewModel.getUser().get(0);
        periodeList = viewModel.getPeriode();
        tiKdKab = findViewById(R.id.kdKab);
        tiNamaKab = findViewById(R.id.namaKab);
        tiNamaKrt = findViewById(R.id.namaKrt);
        spinnerBs = (Spinner) findViewById(R.id.spinnerBs);
        spinnerNuRt = (Spinner) findViewById(R.id.spinnerNuRT);
        batalBtn = findViewById(R.id.batalLaporan);
        simpanBtn = findViewById(R.id.simpanLaporan);

        // set ti kab
        tiKdKab.setText(user.getKd_kab());
        tiNamaKab.setText(user.getNama_kab());

        // mount spinner dsbs
        dsbsList = viewModel.getDsbs(periodeList.get(0).getTahun(), periodeList.get(0).getSemester());

        List<String> idBsList = new ArrayList<String>();

        for (int i = 0; i < dsbsList.size(); i++) {
            idBsList.add(dsbsList.get(i).getKd_kab() + dsbsList.get(i).getKd_kec()+ dsbsList.get(i).getKd_desa()+dsbsList.get(i).getKd_bs());
        }

        ArrayAdapter<String> spinnerBsAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, idBsList);
        spinnerBs.setAdapter(spinnerBsAdapter);

        // set spinner bs on click
        spinnerBs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String idBs = spinnerBs.getSelectedItem().toString();
                dsrtList = viewModel.getListDsrtByIdBs(periodeList.get(0).getTahun(), periodeList.get(0).getSemester(), idBs.substring(0,2), idBs.substring(2,5), idBs.substring(5,8), idBs.substring(8,12));
                // mount spinner dsrt
                List<String> nuRtList = new ArrayList<String>();
                for (int j = 0; j < dsrtList.size(); j++) {
                    nuRtList.add("["+String.valueOf(dsrtList.get(j).getNu_rt())+"]-"+dsrtList.get(j).getNama_krt_cacah());
                }
                ArrayAdapter<String> spinnerNuRtAdapter = new ArrayAdapter<String>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, nuRtList);
                spinnerNuRt.setAdapter(spinnerNuRtAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerNuRt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String idBs = spinnerBs.getSelectedItem().toString();
                String selectedNurt = spinnerNuRt.getSelectedItem().toString();
                String[] arrayStr = selectedNurt.split("-");
                String nuRtString = arrayStr[0].replace("[","");
                nuRtString = nuRtString.replace("]","");
                int nuRT = Integer.parseInt(nuRtString);
                tiNamaKrt.setText(arrayStr[1]);
                dsrtSelected = viewModel.getDsrtByIdBsNuRt(periodeList.get(0).getTahun(), periodeList.get(0).getSemester(), idBs.substring(0,2), idBs.substring(2,5), idBs.substring(5,8), idBs.substring(8,12),  nuRT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // batal btn
        batalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // simpan btn
        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringDate = dateFormatDate.format(new Date());
                Laporan212 laporan212 = new Laporan212(
                        dsrtSelected.getTahun(),
                        dsrtSelected.getSemester(),
                        dsrtSelected.getKd_kab(),
                        dsrtSelected.getKd_kec(),
                        dsrtSelected.getKd_desa(),
                        dsrtSelected.getKd_bs(),
                        dsrtSelected.getNu_rt(),
                        dsrtSelected.getNks(),
                        dsrtSelected.getNama_krt_cacah(),
                        user.getEmail(),
                        stringDate,
                        1
                );

                viewModel.insertLaporan(laporan212);
                Toast.makeText(FormInput212Activity.this, "Laporan disimpan", Toast.LENGTH_SHORT).show();
                finish();

            }
        });



    }
}