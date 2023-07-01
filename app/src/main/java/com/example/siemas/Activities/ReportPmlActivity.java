package com.example.siemas.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.TextView;

import com.example.siemas.Adapter.ReportPmlViewpagerAdapter;
import com.example.siemas.Adapter.ReportViewPagerAdapter;
import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.User;
import com.example.siemas.RoomDatabase.ViewModel;
import com.google.android.material.tabs.TabLayout;

public class ReportPmlActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ReportPmlViewpagerAdapter reportPmlViewpagerAdapter;
    private ViewModel viewModel;
    private User user;
    TextView tvKdProv, tvKdKab, tvNamaProv, tvNamaKab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.siemas.R.layout.activity_report_pml);

        // disallowed dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("MENU REPORT - PML");
        setSupportActionBar(myToolbar);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModel.class);
        user = viewModel.getUser().get(0);

        tvKdProv = findViewById(R.id.tvKdProv);
        tvNamaProv = findViewById(R.id.tvNamaProv);
        tvKdKab = findViewById(R.id.tvKdKab);
        tvNamaKab = findViewById(R.id.tvNamakab);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);
        reportPmlViewpagerAdapter = new ReportPmlViewpagerAdapter(this);
        viewPager2.setAdapter(reportPmlViewpagerAdapter);

        tvKdProv.setText("[16]");
        tvNamaProv.setText("Sumatera Selatan");
        tvKdKab.setText("["+user.getKd_kab()+"]");
        tvNamaKab.setText(user.getNama_kab());


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
    }
}