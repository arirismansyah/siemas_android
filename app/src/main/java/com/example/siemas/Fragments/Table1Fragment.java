package com.example.siemas.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.siemas.Adapter.Table1PemeriksaanAdapter;
import com.example.siemas.Adapter.Table1PencacahanAdapter;
import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsbs;
import com.example.siemas.RoomDatabase.ViewModel;

import java.util.List;


public class Table1Fragment extends Fragment {
    private ViewModel viewModel;
    private RecyclerView recyclerView1, recyclerView2;
    private Table1PencacahanAdapter table1PencacahanAdapter;
    private Table1PemeriksaanAdapter table1PemeriksaanAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_table1, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(final View view){
        recyclerView1 = view.findViewById(R.id.recyclerTable1Pencacahan);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView1.setHasFixedSize(true);

        recyclerView2 = view.findViewById(R.id.recyclerTable1Pemeriksaan);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView2.setHasFixedSize(true);


        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(ViewModel.class);

        table1PencacahanAdapter = new Table1PencacahanAdapter(viewModel);
        table1PemeriksaanAdapter = new Table1PemeriksaanAdapter(viewModel);

        recyclerView1.setAdapter(table1PencacahanAdapter);
        recyclerView2.setAdapter(table1PemeriksaanAdapter);

        viewModel.getLiveDataDsbs().observe(getActivity(), new Observer<List<Dsbs>>() {
            @Override
            public void onChanged(List<Dsbs> dsbs) {
                table1PemeriksaanAdapter.setListDsbs(dsbs);
                table1PencacahanAdapter.setListDsbs(dsbs);
            }
        });
    }
}