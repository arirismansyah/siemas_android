package com.example.siemas.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.siemas.Fragments.Table1Fragment;
import com.example.siemas.Fragments.Table2Fragment;
import com.example.siemas.Fragments.Table3Fragment;

public class ReportViewPagerAdapter extends FragmentStateAdapter {

    public ReportViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new Table1Fragment();
            case 1:
                return new Table2Fragment();
            case 2:
                return new Table3Fragment();
            default:
                return new Table1Fragment();
        }

    }


    @Override
    public int getItemCount() {
        return 3;
    }
}
