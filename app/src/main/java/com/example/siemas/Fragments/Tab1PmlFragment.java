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

import com.example.siemas.Adapter.Table1PmlAdapter;
import com.example.siemas.R;
import com.example.siemas.RoomDatabase.Entities.Dsbs;
import com.example.siemas.RoomDatabase.Entities.Periode;
import com.example.siemas.RoomDatabase.ViewModel;

import java.util.List;


public class Tab1PmlFragment extends Fragment {
    private ViewModel viewModel;
    private RecyclerView recyclerView;
    private Table1PmlAdapter table1PmlAdapter;
    private List<Periode> periodeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab1_pml, container, false);
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
        recyclerView = view.findViewById(R.id.recyclerTable1Pml);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(ViewModel.class);
        periodeList = viewModel.getPeriode();
        table1PmlAdapter = new Table1PmlAdapter(viewModel);

        recyclerView.setAdapter(table1PmlAdapter);

        viewModel.getLiveDataDsbs(periodeList.get(0).getTahun(), periodeList.get(0).getSemester()).observe(getActivity(), new Observer<List<Dsbs>>() {
            @Override
            public void onChanged(List<Dsbs> dsbs) {
                table1PmlAdapter.setListDsbs(dsbs);
            }
        });
    }
}