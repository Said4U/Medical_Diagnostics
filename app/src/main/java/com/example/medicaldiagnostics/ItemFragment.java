package com.example.medicaldiagnostics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.medicaldiagnostics.sections.Bones;
import com.example.medicaldiagnostics.sections.Heart;
import com.example.medicaldiagnostics.sections.NervousSystem;
import com.example.medicaldiagnostics.sections.Oncology;
import com.example.medicaldiagnostics.sections.RespiratoryOrgans;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ItemFragment extends Fragment {


    ListView mainListView;
    ArrayAdapter mArrayAdapter;
    String selectedItem;
    String selectedWord;
    String[] names = {"Болезни органов дыхахния", "Заболевания нервной системы", "Болени костей", "Сердечно-сосудистые заболевания", "Онкологические заболевания"};
    ArrayList<String> itemLst = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mainListView = (ListView) getView().findViewById(R.id.main_listview);
        mArrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, names);
        mainListView.setAdapter(mArrayAdapter);
        registerForContextMenu(mainListView);

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                selectedItem =  (String)parent.getItemAtPosition(position);
                switch (selectedItem) {
                    case "Болезни органов дыхахния":
                        Intent intent1 = new Intent(getActivity(), RespiratoryOrgans.class);
                        startActivity(intent1);
                        break;
                    case "Заболевания нервной системы":
                        Intent intent2 = new Intent(getActivity(), NervousSystem.class);
                        startActivity(intent2);
                        break;
                    case "Болени костей":
                        Intent intent3 = new Intent(getActivity(), Bones.class);
                        startActivity(intent3);
                        break;
                    case "Сердечно-сосудистые заболевания":
                        Intent intent4 = new Intent(getActivity(), Heart.class);
                        startActivity(intent4);
                        break;
                    case "Онкологические заболевания":
                        Intent intent5 = new Intent(getActivity(), Oncology.class);
                        startActivity(intent5);
                        break;
                }
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) menuInfo;
        selectedWord = ((TextView) info.targetView).getText().toString();
        menu.add(0, 1, 0, "Добавить в избранное");
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorites, container, false);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Bundle result = new Bundle();
        if (selectedWord != null) {
            itemLst.add(selectedWord);
            result.putStringArrayList("1", itemLst);
            getParentFragmentManager().setFragmentResult("requestKey", result);
        }
        return super.onContextItemSelected(item);
    }


}