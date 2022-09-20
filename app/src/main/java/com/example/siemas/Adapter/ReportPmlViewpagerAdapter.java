package com.example.siemas.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.siemas.Fragments.Tab1PmlFragment;
import com.example.siemas.Fragments.Tab2PmlFragment;
import com.example.siemas.Fragments.Tab3PmlFragment;
import com.example.siemas.Fragments.Table1Fragment;
import com.example.siemas.Fragments.Table2Fragment;
import com.example.siemas.Fragments.Table3Fragment;

public class ReportPmlViewpagerAdapter extends FragmentStateAdapter {

    public ReportPmlViewpagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new Tab1PmlFragment();
            case 1:
                return new Tab2PmlFragment();
            case 2:
                return new Tab3PmlFragment();
            default:
                return new Tab1PmlFragment();
        }

    }


    @Override
    public int getItemCount() {
        return 3;
    }
}
