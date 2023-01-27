package com.example.siemas.Activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siemas.Adapter.DsrtLaporanAdapter;
import com.example.siemas.Adapter.DsrtPemeriksaanPmlAdapter;
import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.Entities.Jadwal212;
import com.example.siemas.RoomDatabase.Entities.Laporan212;
import com.example.siemas.RoomDatabase.ViewModel;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Input212Activity extends AppCompatActivity {
    private CompactCalendarView compactCalendarView;
    private ViewModel viewModel;
    private List<Jadwal212> jadwal212List;
    private TextView tvMonth;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private AppCompatButton laporBtn;
    private RecyclerView recyclerView;
    private LinearLayoutCompat emptyContainer;
    private DsrtLaporanAdapter dsrtLaporanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.example.siemas.R.layout.activity_input212_main);

        // disallowed dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModel.class);
        jadwal212List = viewModel.getListJadwal();
        tvMonth = findViewById(R.id.month);
        laporBtn = findViewById(R.id.lapor_btn);

        // set recycler
        emptyContainer = findViewById(R.id.emptyPictContainer);
        recyclerView = findViewById(R.id.recyclerLaporan212);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        dsrtLaporanAdapter = new DsrtLaporanAdapter(viewModel);
        recyclerView.setAdapter(dsrtLaporanAdapter);

        viewModel.getLiveDataLaporan().observe(this, new Observer<List<Laporan212>>() {
            @Override
            public void onChanged(List<Laporan212> laporan212List) {
                if (laporan212List.size()>0){
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyContainer.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    emptyContainer.setVisibility(View.VISIBLE);
                }
                Log.d(TAG, "onChanged: laporan212List"+laporan212List);
                dsrtLaporanAdapter.setListLaporan(laporan212List);
            }
        });

        tvMonth.setText(dateFormatMonth.format(new Date()));

        for (int i = 0; i < jadwal212List.size(); i++) {
            Jadwal212 jadwal212 = jadwal212List.get(i);
            String string_date = jadwal212.getTanggal();
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date d = f.parse(string_date);
                Long milliseconds = d.getTime();
                Event ev1 = new Event(ContextCompat.getColor(this, R.color.orange_pastel), milliseconds, "Waktu Pelaporan 212");
                compactCalendarView.addEvent(ev1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                if (events.size()>0){
                    String s = events.get(0).getData().toString();
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                tvMonth.setText(dateFormatMonth.format(firstDayOfNewMonth));

            }
        });

        // lapor btn
        laporBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String stringDate = dateFormatDate.format(new Date());

                Jadwal212 jadwal212 = viewModel.getJadwalByTanggal(stringDate);
                Boolean b = !Objects.isNull(jadwal212);
//                b = true;

                if (b){
                    Intent intent = new Intent(Input212Activity.this, FormInput212Activity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Sekarang belum waktunya pelaporan 212", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }
}