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

import com.example.siemas.Adapter.Table2Adapter;
import com.example.siemas.Adapter.Table3Adapter;
import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.Entities.Periode;
import com.example.siemas.RoomDatabase.ViewModel;

import java.util.List;

public class Table3Fragment extends Fragment {
    private ViewModel viewModel;
    private RecyclerView recyclerView;
    private Table3Adapter table3Adapter;
    private List<Periode> periodeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_table3, container, false);
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
        recyclerView = view.findViewById(R.id.recyclerTable3);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(ViewModel.class);
        periodeList = viewModel.getPeriode();

        table3Adapter = new Table3Adapter();
        recyclerView.setAdapter(table3Adapter);
//        List<Dsrt> dsrts = viewModel.getLiveDataDsrt(periodeList.get(0).getTahun(), periodeList.get(0).getSemester());
//        table3Adapter.setListDsrt(dsrts);
        viewModel.getLiveDataDsrt(periodeList.get(0).getTahun(), periodeList.get(0).getSemester()).observe(getActivity(), new Observer<List<Dsrt>>() {
            @Override
            public void onChanged(List<Dsrt> dsrtList) {
                table3Adapter.setListDsrt(dsrtList);
            }
        });


    }
}